package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.pedido.ItemPedidoRequest;
import br.com.lojavirtual.dto.pedido.PedidoResponse;
import br.com.lojavirtual.dto.produto.ProdutoResponse;
import br.com.lojavirtual.model.ItemPedido;
import br.com.lojavirtual.model.Pedido;
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.repository.PedidoDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoDAO pedidoDAO;
    private final ProdutoService produtoService;
    private static final Long USUARIO = 1L;

    public PedidoService(PedidoDAO pedidoDAO, ProdutoService produtoService) {
        this.pedidoDAO = pedidoDAO;
        this.produtoService = produtoService;
    }

    @Transactional
    public PedidoResponse incluirPedido(List<ItemPedidoRequest> itens) {
        // Criando uma instância de Pedido com usuário fixo e total inicial zero
        Pedido pedido = new Pedido();
        pedido.setUserId(USUARIO);
        pedido.setTotal(0.0);
        pedido = pedidoDAO.incluirPedido(pedido);

        // salvando os itens do pedido
        for (ItemPedidoRequest item : itens) {
            // Criando uma instância de ItemPedido para cada item na lista
            ItemPedido itemPedido = new ItemPedido();

            // Buscando o produto para obter o preço unitário
            Long produtoId = item.getProdutoId();
            ProdutoResponse produto = produtoService.buscarPorId(produtoId);

            // Salvando as informações do item do pedido
            itemPedido.setPedidoId(pedido.getId());
            itemPedido.setProdutoId(item.getProdutoId());
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());

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
