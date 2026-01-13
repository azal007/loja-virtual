package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.pedido.ItemPedidoRequest;
import br.com.lojavirtual.dto.pedido.PedidoResponse;
import br.com.lojavirtual.model.ItemPedido;
import br.com.lojavirtual.model.Pedido;
import br.com.lojavirtual.repository.PedidoDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoDAO pedidoDAO;
    private static final Long USUARIO = 1L;

    public PedidoService(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    @Transactional
    public PedidoResponse incluirPedido(List<ItemPedidoRequest> itens) {
        Pedido pedido = new Pedido();
        pedido.setUserId(USUARIO);
        pedido.setTotal(0.0);
        pedido = pedidoDAO.incluirPedido(pedido);

        for (ItemPedidoRequest item : itens) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedidoId(pedido.getId());
            itemPedido.setProdutoId(item.getProdutoId());
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setPrecoUnitario(item.getPrecoUnitario());
            pedidoDAO.incluirItensPedido(itemPedido);
        }

        pedido = pedidoDAO.buscarPorId(pedido.getId());
        pedido.calcularTotal();

        PedidoResponse pedidoResponse = new PedidoResponse();
        pedidoResponse.setId(pedido.getId());
        pedidoResponse.setUserId(pedido.getUserId());
        pedidoResponse.setDataEmissao(pedido.getDataEmissao());
        pedidoResponse.setStatus(pedido.getStatus());
        pedidoResponse.setTotal(pedido.getTotal());
        pedidoResponse.setItens(pedido.getItens());
        return pedidoResponse;
    }

    // TODO: Implementar a lógica de cancelamento de pedido
    public Object cancelarPedido(Long id) {
        return null;
    }

    // TODO: Implementar a lógica de listagem de pedidos por usuário
    public Object listarPedidosPorUsuario() {
        return null;
    }
}
