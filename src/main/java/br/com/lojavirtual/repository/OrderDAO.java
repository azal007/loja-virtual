package br.com.lojavirtual.repository;

import br.com.lojavirtual.constants.OrderStatus;
import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.OrderItem;
import br.com.lojavirtual.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class OrderDAO {
    private final JdbcTemplate jdbcTemplate;

    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Order findById(Long id) {
        try {
            Order order = jdbcTemplate.queryForObject("SELECT * FROM `order` o WHERE o.id = ?", new BeanPropertyRowMapper<>(Order.class), id);
            List<OrderItem> items = jdbcTemplate.query("SELECT * FROM order_item WHERE order_id = ?", new BeanPropertyRowMapper<>(OrderItem.class), id);

            if (!Objects.isNull(order)) {
                order.setItems(items);
            }

            return order;
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while finding order with id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public List<Order> listByUserId(Long userId) {
        try {
            List<Order> orders = jdbcTemplate.query(
                "SELECT * FROM `order` o WHERE o.user_id = ? ORDER BY o.issued_at DESC",
                new BeanPropertyRowMapper<>(Order.class), userId
            );

            for (Order order : orders) {
                List<OrderItem> items = jdbcTemplate.query(
                    "SELECT * FROM order_item WHERE order_id = ?",
                    new BeanPropertyRowMapper<>(OrderItem.class), order.getId()
                );
                order.setItems(items);
            }

            return orders;
        } catch (Exception e) {
            log.error("Error occurred while listing orders for user {}.", userId, e);
            throw new IntegrationException();
        }
    }

    public Order insertOrder(Order order) {
        try {
            jdbcTemplate.update("INSERT INTO `order` (user_id, status, total) VALUES (?, ?, ?)", order.getUserId(), OrderStatus.CREATED.name(), order.getTotal());
            Long id = jdbcTemplate.queryForObject("SELECT o.id FROM `order` o WHERE id = LAST_INSERT_ID()", Long.class);
            return findById(id);
        } catch (Exception e) {
            log.error("Error occurred while inserting order.", e);
            throw new IntegrationException();
        }
    }

    public OrderItem insertOrderItems(OrderItem orderItem) {
        try {
            String sql = "INSERT INTO order_item (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getUnitPrice());
            return orderItem;
        } catch (Exception e) {
            log.error("Error occurred while inserting order item.", e);
            throw new IntegrationException();
        }
    }

    public void updateStatus(Long id, OrderStatus status) {
        try {
            jdbcTemplate.update("UPDATE `order` o SET o.status = ? WHERE o.id = ?", status.name(), id);
        } catch (Exception e) {
            log.error("Error occurred while updating order status {}.", id, e);
            throw new IntegrationException();
        }
    }
}
