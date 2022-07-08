package no.acntech.order.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Valid
public class CreateOrderDto {

    @NotNull
    private UUID customerId;
    @NotNull
    private String name;
    private String description;

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
