package br.com.lojavirtual.model;

import br.com.lojavirtual.constantes.PedidoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    private Long id;
    private Long userId;
    private Date dataEmissao;
    private PedidoStatus status;
    private Double total;
    private List<ItemPedido> itens;

    public void calcularTotal() {
        this.total = itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario().doubleValue() * item.getQuantidade())
                .sum();
    }
}
