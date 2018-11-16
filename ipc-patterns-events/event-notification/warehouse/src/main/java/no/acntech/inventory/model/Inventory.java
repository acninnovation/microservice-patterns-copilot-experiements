package no.acntech.inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.time.ZonedDateTime;

import no.acntech.product.model.Product;

@Table(name = "INVENTORIES")
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @NotNull
    private Long quantity;
    private ZonedDateTime modified;

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public ZonedDateTime getModified() {
        return modified;
    }

    @PrePersist
    public void prePersist() {
        modified = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modified = ZonedDateTime.now();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Product product;
        private Long quantity;

        private Builder() {
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public Inventory build() {
            Inventory inventory = new Inventory();
            inventory.product = this.product;
            inventory.quantity = this.quantity;
            return inventory;
        }
    }
}
