package br.com.lojavirtual.dto.pedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequest {
    @NotNull
    private Long produtoId;
    @NotNull
    @Min(1)
    private Integer quantidade;
}
