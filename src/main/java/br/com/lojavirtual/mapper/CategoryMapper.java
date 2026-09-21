package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.category.CategoryPatchRequest;
import br.com.lojavirtual.dto.category.CategoryRequest;
import br.com.lojavirtual.dto.category.CategoryResponse;
import br.com.lojavirtual.dto.category.CategoryUpdateRequest;
import br.com.lojavirtual.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getParentCategoryId(),
            category.getActive(),
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }

    public Category toEntity(CategoryRequest request) {
        return new Category(
            null,
            request.getName(),
            request.getParentCategoryId(),
            Boolean.TRUE,
            null,
            null
        );
    }
    public Category toEntityUpdate(CategoryUpdateRequest request) {
        return new Category(
                null,
                request.getName(),
                request.getParentCategoryId(),
                Boolean.TRUE,
                null,
                null
        );
    }

    public Category toEntityPatch(CategoryPatchRequest request) {
        return new Category(
                null,
                request.getName(),
                request.getParentCategoryId(),
                Boolean.TRUE,
                null,
                null
        );
    }

}
