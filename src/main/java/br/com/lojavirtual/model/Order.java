package br.com.lojavirtual.model;

import br.com.lojavirtual.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private Date issuedAt;
    private OrderStatus status;
    private Double total;
    private List<OrderItem> items;

    public void calculateTotal() {
        if (items != null && !items.isEmpty()) {
            this.total = items.stream()
                .mapToDouble(item -> item.getUnitPrice().doubleValue() * item.getQuantity())
                .sum();
        }
    }
}
