package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.Category;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class CategoryDAO extends BaseDAO {
    private final JdbcTemplate jdbcTemplate;
    @Getter
    private final List<Object> pageParameters;

    public CategoryDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.pageParameters = new ArrayList<>();
    }

    public Category findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM category c WHERE c.id = ? AND active = TRUE", new BeanPropertyRowMapper<>(Category.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while finding category with id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public String buildFromWhereListing(List<Object> parameters, Boolean active) {
        String sqlFromWhere = "FROM category c WHERE 1=1";

        if (!Objects.isNull(active)) {
            sqlFromWhere += " AND c.active = ?";
            parameters.add(active);
        }

        return sqlFromWhere;
    }


    public List<Category> list(Boolean active, Integer pageNumber, Integer pageSize){
        try {
            String sql = "SELECT * ";
            ArrayList<Object> parameters = new ArrayList<>();

            sql += buildFromWhereListing(parameters, active);

            if (!Objects.isNull(pageSize)) {
                pageNumber = (pageNumber - 1) * pageSize;
                sql += " LIMIT ? OFFSET ?";
                parameters.add(pageSize);
                parameters.add(pageNumber);
            }
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), parameters.toArray());

        } catch (Exception e) {
            log.error("Error occurred while listing categories.", e);
            throw new IntegrationException();
        }
    }

    public Integer countList(Boolean active) {
        try {
            String sql = "SELECT count(*) ";
            List<Object> parameters = new ArrayList<>();

            sql += buildFromWhereListing(parameters, active);

            return jdbcTemplate.queryForObject(sql, Integer.class, parameters.toArray());
        } catch (Exception e) {
            log.error("Error occurred while listing products.", e);
            throw new IntegrationException();
        }
    }


    public Category insert(Category category) {
        try {
            jdbcTemplate.update("INSERT INTO category (name, parent_category_id) VALUES (?, ?)", category.getName(), category.getParentCategoryId());
            Long id = jdbcTemplate.queryForObject("SELECT c.id  FROM category c WHERE c.id = LAST_INSERT_ID()", Long.class);
            return findById(id);
        } catch (Exception e) {
            log.error("Error occurred while creating category.", e);
            throw new IntegrationException();
        }
    }

    public Category update(Long id, Category category) {
        try {
            jdbcTemplate.update("UPDATE category c SET c.name = ?, c.active = ? WHERE  (id) = ?", category.getName(), category.getActive(), id);
            return findById(id);
        } catch (Exception e) {
            log.error("Error occurred while updating category {}.", id, e);
            throw new IntegrationException();
        }
    }

    public void delete(Long id) {
        try {
            jdbcTemplate.update("UPDATE category c SET c.active = FALSE  WHERE id = ?", id);
        } catch (Exception e) {
            log.error("Error occurred while deleting category with id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public Boolean existsCategory(Long parentCategoryId) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM category c WHERE c.id = ? AND c.active = TRUE)", Boolean.class, parentCategoryId);
    }

    public Boolean hasChildrenInCategory(Long parentCategoryId){
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM category c WHERE c.parent_category_id = ? AND c.active = TRUE)", Boolean.class, parentCategoryId);
    }
}
