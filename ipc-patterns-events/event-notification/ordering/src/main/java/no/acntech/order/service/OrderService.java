package no.acntech.order.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import no.acntech.order.exception.ItemAlreadyExistsException;
import no.acntech.order.exception.ItemNotFoundException;
import no.acntech.order.exception.OrderNotFoundException;
import no.acntech.order.model.CreateItemDto;
import no.acntech.order.model.CreateOrderDto;
import no.acntech.order.model.Item;
import no.acntech.order.model.ItemStatus;
import no.acntech.order.model.Order;
import no.acntech.order.model.OrderQuery;
import no.acntech.order.model.OrderStatus;
import no.acntech.order.model.UpdateItemDto;
import no.acntech.order.model.UpdateOrderDto;
import no.acntech.order.producer.OrderEventProducer;
import no.acntech.order.repository.ItemRepository;
import no.acntech.order.repository.OrderRepository;
import no.acntech.reservation.consumer.ReservationRestConsumer;
import no.acntech.reservation.model.CreateReservationDto;
import no.acntech.reservation.model.ReservationStatus;
import no.acntech.reservation.model.UpdateReservationDto;

@SuppressWarnings("Duplicates")
@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private static final Sort SORT_BY_ID = Sort.by("id");

    private final ConversionService conversionService;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderEventProducer orderEventProducer;
    private final ReservationRestConsumer reservationRestConsumer;

    public OrderService(final ConversionService conversionService,
                        final OrderRepository orderRepository,
                        final ItemRepository itemRepository,
                        final OrderEventProducer orderEventProducer,
                        final ReservationRestConsumer reservationRestConsumer) {
        this.conversionService = conversionService;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderEventProducer = orderEventProducer;
        this.reservationRestConsumer = reservationRestConsumer;
    }

    public List<Order> findOrders(@NotNull final OrderQuery orderQuery) {
        UUID customerId = orderQuery.getCustomerId();
        OrderStatus status = orderQuery.getStatus();
        if (customerId != null && status != null) {
            return orderRepository.findAllByCustomerIdAndStatus(customerId, status);
        } else if (customerId != null) {
            return orderRepository.findAllByCustomerId(customerId);
        } else if (status != null) {
            return orderRepository.findAllByStatus(status);
        } else {
            return orderRepository.findAll(SORT_BY_ID);
        }
    }

    public Order getOrder(@NotNull final UUID orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional
    public Order createOrder(@Valid final CreateOrderDto createOrder) {
        Order order = conversionService.convert(createOrder, Order.class);
        Order createdOrder = orderRepository.save(order);
        LOGGER.debug("Created order with order-id {}", createdOrder.getOrderId());
        orderEventProducer.publish(createdOrder.getOrderId());
        return createdOrder;
    }

    @Transactional
    public Order updateOrder(@NotNull final UUID orderId, @Valid final UpdateOrderDto updateOrder) {
        OrderStatus orderStatus = updateOrder.getStatus();
        Order order = getOrder(orderId);

        ReservationStatus reservationStatus = ReservationStatus.valueOf(orderStatus.name());

        UpdateReservationDto updateReservation = UpdateReservationDto.builder()
                .orderId(orderId)
                .status(reservationStatus)
                .build();
        reservationRestConsumer.update(updateReservation);

        LOGGER.debug("Updated order with order-id {}", orderId);
        orderEventProducer.publish(orderId);
        return order;
    }

    @Transactional
    public Order createItem(@NotNull final UUID orderId, @Valid final CreateItemDto createItem) {
        UUID productId = createItem.getProductId();
        Long quantity = createItem.getQuantity();

        Order order = getOrder(orderId);
        Optional<Item> exitingItem = itemRepository.findByOrderIdAndProductId(order.getId(), productId);

        if (!exitingItem.isPresent()) {
            Item item = Item.builder()
                    .orderId(order.getId())
                    .productId(productId)
                    .quantity(quantity)
                    .build();

            itemRepository.save(item);

            CreateReservationDto createReservation = CreateReservationDto.builder()
                    .orderId(orderId)
                    .productId(productId)
                    .quantity(quantity)
                    .build();
            reservationRestConsumer.create(createReservation);

            LOGGER.debug("Created order item with product-id {} for order-id {}", orderId, productId);
            orderEventProducer.publish(orderId);
            return order;
        } else {
            throw new ItemAlreadyExistsException(orderId, productId);
        }
    }

    @Transactional
    public Order updateItem(@NotNull final UUID orderId, @Valid final UpdateItemDto updateItem) {
        UUID productId = updateItem.getProductId();

        Order order = getOrder(orderId);
        Optional<Item> exitingItem = itemRepository.findByOrderIdAndProductId(order.getId(), productId);

        if (exitingItem.isPresent()) {

            if (updateItem.isValidUpdateQuantity()) {
                Long quantity = updateItem.getQuantity();

                UpdateReservationDto updateReservation = UpdateReservationDto.builder()
                        .orderId(orderId)
                        .productId(productId)
                        .quantity(quantity)
                        .build();
                reservationRestConsumer.update(updateReservation);
            } else {
                ItemStatus itemStatus = updateItem.getStatus();
                ReservationStatus reservationStatus = ReservationStatus.valueOf(itemStatus.name());

                UpdateReservationDto updateReservation = UpdateReservationDto.builder()
                        .orderId(orderId)
                        .productId(productId)
                        .status(reservationStatus)
                        .build();
                reservationRestConsumer.update(updateReservation);
            }

            LOGGER.debug("Updated order item with product-id {} for order-id {}", orderId, productId);
            orderEventProducer.publish(orderId);
            return order;
        } else {
            throw new ItemNotFoundException(orderId, productId);
        }
    }
}
