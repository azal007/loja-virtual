package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.category.CategoryPatchRequest;
import br.com.lojavirtual.dto.category.CategoryRequest;
import br.com.lojavirtual.dto.category.CategoryResponse;
import br.com.lojavirtual.dto.category.CategoryUpdateRequest;
import br.com.lojavirtual.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> findById(@NotNull @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> list(
            @RequestParam(value = "active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "8") Integer pageSize
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.list(active, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> insert(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.insert(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(id, request));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> updatePartial(@PathVariable Long id, @Valid @RequestBody CategoryPatchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updatePartial(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
