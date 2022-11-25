package no.acntech.customer.converter;

import no.acntech.customer.model.CreateCustomerDto;
import no.acntech.customer.model.CustomerEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomerDtoToCustomerEntityConverter implements Converter<CreateCustomerDto, CustomerEntity> {

    @NonNull
    @Override
    public CustomerEntity convert(@NonNull final CreateCustomerDto source) {
        return CustomerEntity.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .address(source.getAddress())
                .build();
    }
}