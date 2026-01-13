package br.com.lojavirtual.dto.pedido;

import br.com.lojavirtual.model.ItemPedido;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {
    @NotEmpty
    private Long userId;
    @NotEmpty
    private List<ItemPedido> itens;
}
