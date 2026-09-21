package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.category.CategoryPatchRequest;
import br.com.lojavirtual.dto.category.CategoryRequest;
import br.com.lojavirtual.dto.category.CategoryResponse;
import br.com.lojavirtual.dto.category.CategoryUpdateRequest;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.CategoryMapper;
import br.com.lojavirtual.mapper.PageMapper;
import br.com.lojavirtual.model.Category;
import br.com.lojavirtual.model.PageInfo;
import br.com.lojavirtual.repository.CategoryDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService extends BaseService<CategoryDAO> {
    private final CategoryDAO categoryDAO;
    private final CategoryMapper categoryMapper;
    private final PageMapper<CategoryResponse> categoryResponsePageMapper;

    public CategoryService(CategoryDAO categoryDAO, CategoryMapper categoryMapper, PageMapper<CategoryResponse> categoryResponsePageMapper) {
        super(categoryDAO);
        this.categoryDAO = categoryDAO;
        this.categoryMapper = categoryMapper;
        this.categoryResponsePageMapper = categoryResponsePageMapper;
    }

    public CategoryResponse findById(Long id) {
        return categoryMapper.toResponse(validateFindById(id));
    }

    public PageResponse<CategoryResponse> list(Boolean active, Integer pageNumber, Integer pageSize) {
        List<Category> categories = categoryDAO.list(active, pageNumber, pageSize);

        int totalElements = categoryDAO.countList(active);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PageInfo pageInfo = new PageInfo(pageNumber, pageSize, totalElements, totalPages);

        return categoryResponsePageMapper.toResponse(
                pageInfo,
                categories.stream().map(categoryMapper::toResponse).toList()
        );
    }

    public CategoryResponse insert(CategoryRequest request) {
        String name = request.getName();
        Long id = request.getId();
        Long parentCategoryId = request.getParentCategoryId();
        Category category = categoryMapper.toEntity(request);

        if (request.getParentCategoryId() != null) {
            validateCategoryExists(parentCategoryId);
        }
        validateEntityHasSameName(name, id);

        return categoryMapper.toResponse(categoryDAO.insert(category));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        String name = request.getName();
        Long parentCategoryId = request.getParentCategoryId();

        if (Objects.nonNull(validateFindById(id).getParentCategoryId())) {
            validateCategoryExists(parentCategoryId);
        }
        validateEntityHasSameName(name, id);

        Category category = categoryMapper.toEntityUpdate(request);
        return categoryMapper.toResponse(categoryDAO.update(id, category));
    }

    @Transactional
    public CategoryResponse updatePartial(Long id, CategoryPatchRequest request) {
        String name = request.getName();
        Long parentCategoryId = request.getParentCategoryId();

        if (Objects.nonNull(validateFindById(id).getParentCategoryId())) {
            validateCategoryExists(parentCategoryId);
        }
        validateEntityHasSameName(name, id);

        Category category = categoryMapper.toEntityPatch(request);
        return categoryMapper.toResponse(categoryDAO.update(id, category));
    }

    @Transactional
    public void delete(Long id) {
        validateFindById(id);
        categoryDAO.delete(id);
    }

    public Category validateFindById(Long id) {
        try {
            return categoryDAO.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Category.class.getSimpleName(), id);
        }
    }

    public Boolean hasChildrenInCategory(Long parentCategoryId) {
        return categoryDAO.hasChildrenInCategory(parentCategoryId);
    }
}
