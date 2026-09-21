package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.product.ProductPatchRequest;
import br.com.lojavirtual.dto.product.ProductRequest;
import br.com.lojavirtual.dto.product.ProductResponse;
import br.com.lojavirtual.dto.product.ProductUpdateRequest;
import br.com.lojavirtual.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> list(
            @RequestParam(value = "name", required=false) String name,
            @RequestParam(value = "id", required = false) Long categoryId,
            @RequestParam(value = "min", required = false) Double priceMin,
            @RequestParam(value = "max", required = false) Double priceMax,
            @RequestParam(value = "active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "8") Integer pageSize
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.list(name, categoryId, priceMin, priceMax, active, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> insert(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.insert(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, request));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> updatePartial(@PathVariable Long id, @Valid @RequestBody ProductPatchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updatePartial(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
