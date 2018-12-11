package no.acntech.product.resource;

import no.acntech.product.model.CreateProduct;
import no.acntech.product.model.Product;
import no.acntech.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping(path = "products")
@RestController
public class ProductResource {

    private final ProductService productService;

    public ProductResource(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> get() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @GetMapping(path = "{productId}")
    public ResponseEntity<Product> get(@Valid @NotNull @PathVariable("productId") final UUID productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody final CreateProduct createProduct) {
        Product product = productService.createProduct(createProduct);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{productId}")
                .buildAndExpand(product.getProductId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
