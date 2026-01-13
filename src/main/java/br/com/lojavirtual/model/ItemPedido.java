package br.com.lojavirtual.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {
    private Long id;
    private Long pedidoId;
    private Long produtoId;
    private Integer quantidade;
    private Double precoUnitario;
}