package br.com.lojavirtual.service;

import br.com.lojavirtual.constantes.PedidoStatus;
import br.com.lojavirtual.dto.pedido.ItemPedidoRequest;
import br.com.lojavirtual.dto.pedido.PedidoResponse;
import br.com.lojavirtual.dto.produto.ProdutoResponse;
import br.com.lojavirtual.exception.BusinessException;
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

    public PedidoService(PedidoDAO pedidoDAO, ProdutoService produtoService) {
        this.pedidoDAO = pedidoDAO;
        this.produtoService = produtoService;
    }

    @Transactional
    public PedidoResponse incluirPedido(List<ItemPedidoRequest> itens, Long userId) {
        Pedido pedido = new Pedido();
        pedido.setUserId(userId);
        pedido.setTotal(0.0);
        pedido = pedidoDAO.incluirPedido(pedido);

        for (ItemPedidoRequest item : itens) {
            ItemPedido itemPedido = new ItemPedido();

            Long produtoId = item.getProdutoId();
            ProdutoResponse produto = produtoService.buscarPorId(produtoId);

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

    @Transactional
    public PedidoResponse cancelarPedido(Long id, Long userId) {
        Pedido pedido = pedidoDAO.buscarPorId(id);

        if (pedido == null) {
            throw new BusinessException("Pedido não encontrado");
        }

        if (!pedido.getUserId().equals(userId)) {
            throw new BusinessException("Pedido não pertence ao usuário");
        }

        if (pedido.getStatus() != PedidoStatus.CRIADO) {
            throw new BusinessException("Somente pedidos com status CRIADO podem ser cancelados");
        }

        pedidoDAO.atualizarStatus(id, PedidoStatus.CANCELADO);
        pedido = pedidoDAO.buscarPorId(id);

        PedidoResponse pedidoResponse = new PedidoResponse();
        pedidoResponse.setId(pedido.getId());
        pedidoResponse.setUserId(pedido.getUserId());
        pedidoResponse.setDataEmissao(pedido.getDataEmissao());
        pedidoResponse.setStatus(pedido.getStatus());
        pedidoResponse.setTotal(pedido.getTotal());
        pedidoResponse.setItens(pedido.getItens());
        return pedidoResponse;
    }

    public List<PedidoResponse> listarPedidosPorUsuario(Long userId) {
        List<Pedido> pedidos = pedidoDAO.listarPorUsuario(userId);

        return pedidos.stream().map(pedido -> {
            PedidoResponse response = new PedidoResponse();
            response.setId(pedido.getId());
            response.setUserId(pedido.getUserId());
            response.setDataEmissao(pedido.getDataEmissao());
            response.setStatus(pedido.getStatus());
            response.setTotal(pedido.getTotal());
            response.setItens(pedido.getItens());
            return response;
        }).toList();
    }
}
