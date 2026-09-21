package br.com.lojavirtual.service;

import br.com.lojavirtual.constants.OrderStatus;
import br.com.lojavirtual.dto.order.OrderItemRequest;
import br.com.lojavirtual.dto.order.OrderResponse;
import br.com.lojavirtual.dto.product.ProductResponse;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.model.OrderItem;
import br.com.lojavirtual.model.Order;
import br.com.lojavirtual.model.Product;
import br.com.lojavirtual.repository.OrderDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderDAO orderDAO;
    private final ProductService productService;

    public OrderService(OrderDAO orderDAO, ProductService productService) {
        this.orderDAO = orderDAO;
        this.productService = productService;
    }

    @Transactional
    public OrderResponse insertOrder(List<OrderItemRequest> items, Long userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(0.0);
        order = orderDAO.insertOrder(order);

        for (OrderItemRequest item : items) {
            OrderItem orderItem = new OrderItem();

            Long productId = item.getProductId();
            ProductResponse product = productService.findById(productId);

            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(product.getPrice());

            orderDAO.insertOrderItems(orderItem);
        }

        order = orderDAO.findById(order.getId());
        order.calculateTotal();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setUserId(order.getUserId());
        orderResponse.setIssuedAt(order.getIssuedAt());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setTotal(order.getTotal());
        orderResponse.setItems(order.getItems());
        return orderResponse;
    }

    @Transactional
    public OrderResponse cancelOrder(Long id, Long userId) {
        Order order = orderDAO.findById(id);

        if (order == null) {
            throw new BusinessException("Order not found");
        }

        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("Order does not belong to the user");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessException("Only orders with CREATED status can be cancelled");
        }

        orderDAO.updateStatus(id, OrderStatus.CANCELLED);
        order = orderDAO.findById(id);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setUserId(order.getUserId());
        orderResponse.setIssuedAt(order.getIssuedAt());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setTotal(order.getTotal());
        orderResponse.setItems(order.getItems());
        return orderResponse;
    }

    public List<OrderResponse> listOrdersByUserId(Long userId) {
        List<Order> orders = orderDAO.listByUserId(userId);

        return orders.stream().map(order -> {
            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setUserId(order.getUserId());
            response.setIssuedAt(order.getIssuedAt());
            response.setStatus(order.getStatus());
            response.setTotal(order.getTotal());
            response.setItems(order.getItems());
            return response;
        }).toList();
    }
}
