package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.product.ProductPatchRequest;
import br.com.lojavirtual.dto.product.ProductRequest;
import br.com.lojavirtual.dto.product.ProductResponse;
import br.com.lojavirtual.dto.product.ProductUpdateRequest;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.PageMapper;
import br.com.lojavirtual.mapper.ProductMapper;
import br.com.lojavirtual.model.Category;
import br.com.lojavirtual.model.PageInfo;
import br.com.lojavirtual.model.Product;
import br.com.lojavirtual.repository.ProductDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends BaseService<ProductDAO> {
    private final ProductDAO productDAO;
    private final ProductMapper productMapper;
    private final PageMapper<ProductResponse> productResponsePageMapper;
    private final CategoryService categoryService;

    public ProductService(ProductDAO productDAO, ProductMapper productMapper, PageMapper<ProductResponse> productResponsePageMapper, CategoryService categoryService) {
        super(productDAO);
        this.productDAO = productDAO;
        this.productMapper = productMapper;
        this.productResponsePageMapper = productResponsePageMapper;
        this.categoryService = categoryService;
    }

    public ProductResponse findById(Long id) {
        return productMapper.toResponse(validateFindById(id));
    }

    public PageResponse<ProductResponse> list(String name, Long categoryId, Double priceMin, Double priceMax, Boolean active, Integer pageNumber, Integer pageSize) {
        List<Product> products = productDAO.list(name, categoryId, priceMin, priceMax, active, pageNumber, pageSize);

        int totalElements = productDAO.countList(name, categoryId, priceMin, priceMax, active);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PageInfo pageInfo = new PageInfo(pageNumber, pageSize, totalElements, totalPages);

        return productResponsePageMapper.toResponse(
                pageInfo,
                products.stream().map(productMapper::toResponse).toList()
        );
    }

    public ProductResponse insert(ProductRequest request) {
        Long id = request.getId();
        String name = request.getName();
        Long categoryId = request.getCategoryId();
        Product product = productMapper.toEntity(request);

        validateCategoryExists(categoryId);
        validateHasChildren(categoryId);
        validateEntityHasSameName(name, id);

        return productMapper.toResponse(productDAO.insert(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        String name = request.getName();
        Long categoryId = request.getCategoryId();

        validateFindById(id);
        validateCategoryExists(categoryId);
        validateEntityHasSameName(name, id);

        Product product = productMapper.toEntityUpdate(request);
        return productMapper.toResponse(productDAO.update(id, product));
    }

    @Transactional
    public ProductResponse updatePartial(Long id, ProductPatchRequest request) {
        String name = request.getName();
        Long categoryId = request.getCategoryId();

        validateFindById(id);
        validateCategoryExists(categoryId);
        validateEntityHasSameName(name, id);

        Product product = productMapper.toEntityPatch(request);
        return productMapper.toResponse(productDAO.update(id, product));
    }

    @Transactional
    public void delete(Long id) {
        validateFindById(id);
        productDAO.delete(id);
    }

    private Product validateFindById(Long id) {
        try {
            return productDAO.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Product.class.getSimpleName(), id);
        }
    }

    private void validateHasChildren(Long categoryId) {
        Category parentCategory = categoryService.validateFindById(categoryId);
        Boolean hasChildren = categoryService.hasChildrenInCategory(parentCategory.getId());
        if (hasChildren) {
            throw new BusinessException("The informed category is invalid because it has subcategories.");
        }
    }
}
