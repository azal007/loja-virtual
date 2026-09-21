package br.com.lojavirtual.service;

import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.repository.BaseDAO;
import br.com.lojavirtual.repository.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseService<T extends BaseDAO> {
    @Autowired
    private CategoryDAO categoryDAO;
    private final T entityDAO;

    public BaseService(T entityDAO) {
        this.entityDAO = entityDAO;
    }

    public void validateEntityHasSameName(String name, Long id) {
        Boolean hasSameName = entityDAO.hasSameName(name, id);
        if (hasSameName) {
            throw new BusinessException("The informed name already exists.");
        }
    }

    public void validateCategoryExists(Long categoryId) {
        Boolean exists = categoryDAO.existsCategory(categoryId);
        if (!exists) {
            throw new BusinessException("The informed category does not exist.");
        }
    }
}
