package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.product.ProductPatchRequest;
import br.com.lojavirtual.dto.product.ProductRequest;
import br.com.lojavirtual.dto.product.ProductUpdateRequest;
import br.com.lojavirtual.model.Product;
import br.com.lojavirtual.dto.product.ProductResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ProductMapper {
    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice().setScale(2, RoundingMode.HALF_UP),
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCategoryId()
        );
    }

    public Product toEntity(ProductRequest request) {
        return new Product(
                null,
                request.getName(),
                request.getDescription(),
                request.getImageUrl(),
                request.getPrice(),
                Boolean.TRUE,
                null,
                null,
                request.getCategoryId()
        );
    }

    public Product toEntityUpdate(ProductUpdateRequest request) {
        return new Product(
                null,
                request.getName(),
                request.getDescription(),
                request.getImageUrl(),
                request.getPrice(),
                Boolean.TRUE,
                null,
                null,
                request.getCategoryId()
        );
    }

    public Product toEntityPatch(ProductPatchRequest request) {
        return new Product(
                null,
                request.getName(),
                request.getDescription(),
                request.getImageUrl(),
                request.getPrice(),
                Boolean.TRUE,
                null,
                null,
                request.getCategoryId()
        );
    }
}
