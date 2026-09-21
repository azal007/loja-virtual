package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.order.OrderItemRequest;
import br.com.lojavirtual.dto.order.OrderResponse;
import br.com.lojavirtual.security.service.JwtService;
import br.com.lojavirtual.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;
    private final JwtService jwtService;

    public OrderController(OrderService orderService, JwtService jwtService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> insert(@Valid @RequestBody List<OrderItemRequest> request,
                                                   HttpServletRequest httpRequest) {
        Long userId = extractUserId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.insertOrder(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listByUserId(HttpServletRequest httpRequest) {
        Long userId = extractUserId(httpRequest);
        return ResponseEntity.ok(orderService.listOrdersByUserId(userId));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancel(@PathVariable Long id,
                                                    HttpServletRequest httpRequest) {
        Long userId = extractUserId(httpRequest);
        return ResponseEntity.ok(orderService.cancelOrder(id, userId));
    }

    private Long extractUserId(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            return jwtService.getUserIdFromToken(token);
        }
        throw new RuntimeException("Token not provided");
    }
}
