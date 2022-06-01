package no.acntech.order.resource;

import no.acntech.order.model.OrderDto;
import no.acntech.order.model.OrderItemDto;
import no.acntech.order.model.UpdateOrderItemDto;
import no.acntech.order.service.OrderOrchestrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = "/api/items")
@RestController
public class OrderItemsResource {

    private final OrderOrchestrationService orderOrchestrationService;

    public OrderItemsResource(final OrderOrchestrationService orderOrchestrationService) {
        this.orderOrchestrationService = orderOrchestrationService;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<OrderItemDto> get(@PathVariable("id") final UUID itemId) {
        final var orderItemDto = orderOrchestrationService.getOrderItem(itemId);
        return ResponseEntity.ok(orderItemDto);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<OrderDto> put(@PathVariable("id") final UUID itemId,
                                        @RequestBody final UpdateOrderItemDto updateOrderItemDto) {
        orderOrchestrationService.updateOrderItem(itemId, updateOrderItemDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final UUID itemId) {
        orderOrchestrationService.deleteOrderItem(itemId);
        return ResponseEntity.ok().build();
    }
}
