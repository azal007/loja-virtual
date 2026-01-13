package br.com.lojavirtual.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequest {
    private Long pedidoId;
    private Long produtoId;
    private Integer quantidade;
    private Double precoUnitario;
}
