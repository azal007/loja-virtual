package br.com.lojavirtual.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPatchRequest {
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Long categoryId;
}
