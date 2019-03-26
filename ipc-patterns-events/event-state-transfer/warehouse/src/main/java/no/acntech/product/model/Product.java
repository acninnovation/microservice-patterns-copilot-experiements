package no.acntech.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Table(name = "PRODUCTS")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private UUID productId;
    @NotBlank
    private String name;
    private String description;
    private ZonedDateTime created;
    private ZonedDateTime modified;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getModified() {
        return modified;
    }

    @PrePersist
    public void prePersist() {
        productId = UUID.randomUUID();
        created = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modified = ZonedDateTime.now();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String name;
        private String description;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.description = this.description;
            product.name = this.name;
            return product;
        }
    }
}