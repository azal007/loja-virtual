package br.com.lojavirtual.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPatchRequest {
    private Long id;
    private String name;
    private Long parentCategoryId;
}
