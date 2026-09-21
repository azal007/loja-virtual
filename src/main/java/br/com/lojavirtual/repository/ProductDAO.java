package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.Product;
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
public class ProductDAO extends BaseDAO {
    private final JdbcTemplate jdbcTemplate;
    @Getter
    private final List<Object> pageParameters;

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.pageParameters = new ArrayList<>();
    }

    public Product findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM product p WHERE p.id = ?", new BeanPropertyRowMapper<>(Product.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while finding product with id {}", id, e);
            throw new IntegrationException();
        }
    }

    public String buildFromWhereListing(List<Object> parameters, String name, Long categoryId, Double priceMin, Double priceMax, Boolean active) {
        String sqlFromWhere = "FROM product p WHERE 1=1";

        if (!Objects.isNull(name)) {
            sqlFromWhere += " AND p.name LIKE ?";
            parameters.add("%" + name + "%");
        }

        if (!Objects.isNull(categoryId)) {
            sqlFromWhere += " AND p.category_id = ?";
            parameters.add(categoryId);
        }

        if (!Objects.isNull(priceMin) && Objects.isNull(priceMax)) {
            sqlFromWhere += " AND p.price >= ?";
            parameters.add(priceMin);
        }

        if (!Objects.isNull(priceMax) && Objects.isNull(priceMin)) {
            sqlFromWhere += " AND p.price <= ?";
            parameters.add(priceMax);
        }

        if (!Objects.isNull(priceMin) && !Objects.isNull(priceMax)) {
            sqlFromWhere += " AND p.price BETWEEN ? AND ?";
            parameters.add(priceMin);
            parameters.add(priceMax);
        }

        if (!Objects.isNull(active)) {
            sqlFromWhere += " AND p.active = ?";
            parameters.add(active);
        }

        return sqlFromWhere;
    }

    public List<Product> list(String name, Long categoryId, Double priceMin, Double priceMax, Boolean active, Integer pageNumber, Integer pageSize) {
        try {
            String sql = "SELECT * ";
            List<Object> parameters = new ArrayList<>();

            sql += buildFromWhereListing(parameters, name, categoryId, priceMin, priceMax, active);

            if (!Objects.isNull(pageSize)) {
                pageNumber = (pageNumber - 1) * pageSize;
                sql += " LIMIT ? OFFSET ?";
                parameters.add(pageSize);
                parameters.add(pageNumber);
            }

            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), parameters.toArray());
        } catch (Exception e) {
            log.error("Error occurred while listing products.", e);
            throw new IntegrationException();
        }
    }

    public Integer countList(String name, Long categoryId, Double priceMin, Double priceMax, Boolean active) {
        try {
            String sql = "SELECT count(*) ";
            List<Object> parameters = new ArrayList<>();

            sql += buildFromWhereListing(parameters, name, categoryId, priceMin, priceMax, active);

            return jdbcTemplate.queryForObject(sql, Integer.class, parameters.toArray());
        } catch (Exception e) {
            log.error("Error occurred while listing products.", e);
            throw new IntegrationException();
        }
    }

    public Product insert(Product product) {
        try {
            jdbcTemplate.update("INSERT INTO product (name, description, image_url, price, category_id) VALUES(?, ?, ?, ?, ?)", product.getName(), product.getDescription(), product.getImageUrl(), product.getPrice(), product.getCategoryId());
            Long id = jdbcTemplate.queryForObject("SELECT p.id FROM product p WHERE id = LAST_INSERT_ID()", Long.class);
            return findById(id);
        } catch (Exception e) {
            log.error("Error occurred while creating product.", e);
            throw new IntegrationException();
        }
    }

    public Product update(Long id, Product product) {
        try {
            jdbcTemplate.update("UPDATE product p SET p.name = ?, p.description = ?, p.image_url = ?, p.price = ?, p.category_id = ? WHERE (id) = ?", product.getName(), product.getDescription(), product.getImageUrl(), product.getPrice(), product.getCategoryId(), id);
            return findById(id);
        } catch (Exception e) {
            log.error("Error occurred while updating product with id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public void delete(Long id) {
        try {
            jdbcTemplate.update("UPDATE product p SET p.active = FALSE WHERE p.id = ?", id);
        } catch (Exception e) {
            log.error("Error occurred while deleting product with id {}.", id, e);
            throw new IntegrationException();
        }
    }
}
