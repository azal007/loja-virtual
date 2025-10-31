package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.Produto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ProdutoDAO extends BaseDAO {
    private final JdbcTemplate jdbcTemplate;

    public ProdutoDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    public Produto buscarPorId(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Produtos p WHERE p.id = ?", new BeanPropertyRowMapper<>(Produto.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao buscar o produto com id {}", id, e);
            throw new IntegrationException();
        }
    }

    public List<Produto> listar(String nome, Long categoriaId, Double precoMin, Double precoMax, Boolean ativo) {
        try {
            String sql = "SELECT * FROM Produtos p WHERE 1=1";
            List<Object> parametros = new ArrayList<>();

            if (nome != null) {
                sql += " AND p.nome LIKE ?";
                parametros.add("%" + nome + "%");
            }

            if (categoriaId != null) {
                sql += " AND p.categoria_id = ?";
                parametros.add(categoriaId);
            }

            if (precoMin != null && precoMax == null) {
                sql += " AND p.preco >= ?";
                parametros.add(precoMin);
            }

            if (precoMax != null && precoMin == null) {
                sql += " AND p.preco <= ?";
                parametros.add(precoMax);
            }

            if (precoMin != null && precoMax != null) {
                sql += " AND p.preco BETWEEN ? AND ?";
                parametros.add(precoMin);
                parametros.add(precoMax);
            }

            if (ativo != null ) {
                sql += " AND p.ativo = ?";
                parametros.add(ativo);
            }

            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Produto.class), parametros.toArray());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao listar os produtos.", e);
            throw new IntegrationException();
        }
    }

    public Produto incluir(Produto produto) {
        try {
            jdbcTemplate.update("INSERT INTO Produtos (nome, descricao, url_imagem, preco, categoria_id) VALUES(?, ?, ?, ?, ?)", produto.getNome(), produto.getDescricao(), produto.getUrlImagem(), produto.getPreco(), produto.getCategoriaId());
            Long id = jdbcTemplate.queryForObject("SELECT p.id FROM Produtos p WHERE id = LAST_INSERT_ID()", Long.class);
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o produto.", e);
            throw new IntegrationException();
        }
    }

    public Produto atualizar(Long id, Produto produto) {
        try {
            jdbcTemplate.update("UPDATE Produtos p SET p.nome = ?, p.descricao = ?, p.url_imagem = ?, p.preco = ?, p.categoria_id = ? WHERE (id) = ?", produto.getNome(), produto.getDescricao(), produto.getUrlImagem(), produto.getPreco(), produto.getCategoriaId(), id);
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao atualizar o produto com id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public void excluir(Long id) {
        try {
            jdbcTemplate.update("UPDATE Produtos p SET p.ativo = FALSE WHERE p.id = ?", id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao excluir o produto com id {}.", id, e);
            throw new IntegrationException();
        }
    }
}
