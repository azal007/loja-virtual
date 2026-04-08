package br.com.lojavirtual.repository;

import br.com.lojavirtual.constantes.PedidoStatus;
import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.ItemPedido;
import br.com.lojavirtual.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class PedidoDAO {
    private final JdbcTemplate jdbcTemplate;

    public PedidoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Pedido buscarPorId(Long id) {
        try {
            Pedido pedido = jdbcTemplate.queryForObject("SELECT * FROM pedido p WHERE p.id = ?", new BeanPropertyRowMapper<>(Pedido.class), id);
            List<ItemPedido> itens = jdbcTemplate.query("SELECT * FROM item_pedido WHERE pedido_id = ?", new BeanPropertyRowMapper<>(ItemPedido.class), id);

            if (!Objects.isNull(pedido)) {
                pedido.setItens(itens);
            }

            return pedido;
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao buscar um pedido com id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public Pedido incluirPedido(Pedido pedido) {
        try {
            jdbcTemplate.update("INSERT INTO pedido (user_id, status, total) VALUES (?, ?, ?)", pedido.getUserId(), PedidoStatus.CRIADO.name(), pedido.getTotal());
            Long id = jdbcTemplate.queryForObject("SELECT p.id FROM pedido p WHERE id = LAST_INSERT_ID()", Long.class);
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao incluir o pedido.", e);
            throw new IntegrationException();
        }
    }

    public ItemPedido incluirItensPedido(ItemPedido itemPedido) {
        try {
            String sql = "INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, itemPedido.getPedidoId(), itemPedido.getProdutoId(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario());
            return itemPedido;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao incluir o item do pedido.", e);
            throw new IntegrationException();
        }
    }
}
