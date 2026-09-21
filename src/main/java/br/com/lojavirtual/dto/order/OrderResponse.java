package br.com.lojavirtual.dto.order;

import br.com.lojavirtual.constants.OrderStatus;
import br.com.lojavirtual.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class OrderResponse {
    private Long id;
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date issuedAt;
    private OrderStatus status;
    private Double total;
    private List<OrderItem> items;
}
