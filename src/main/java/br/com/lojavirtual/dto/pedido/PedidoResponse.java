package br.com.lojavirtual.dto.pedido;

import br.com.lojavirtual.constantes.PedidoStatus;
import br.com.lojavirtual.model.ItemPedido;
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
public class PedidoResponse {
    private Long id;
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataEmissao;
    private PedidoStatus status;
    private Double total;
    private List<ItemPedido> itens;
}
