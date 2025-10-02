package br.com.lojavirtual.repository;

import br.com.lojavirtual.model.Produto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoDAO {
    private final JdbcTemplate jdbcTemplate;

    public ProdutoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Produto buscarPorId(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Produtos p WHERE p.id = ?", new BeanPropertyRowMapper<>(Produto.class), id);
    }

    public List<Produto> listar(String nome, Long categoriaId, Double precoMin, Double precoMax) {
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
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Produto.class), parametros.toArray());
    }

    public Produto incluir(Produto produto) {
        jdbcTemplate.update("INSERT INTO Produtos (nome, descricao, url_imagem, preco, categoria_id) VALUES(?, ?, ?, ?, ?)", produto.getNome(), produto.getDescricao(), produto.getUrlImagem(), produto.getPreco(), produto.getCategoriaId());
        Long id = jdbcTemplate.queryForObject("SELECT p.id FROM Produtos p WHERE id = LAST_INSERT_ID()", Long.class);
        return buscarPorId(id);
    }

    public Produto atualizar(Long id, Produto produto) {
        jdbcTemplate.update("UPDATE Produtos p SET p.nome = ?, p.descricao = ?, p.url_imagem = ?, p.preco = ?, p.categoria_id = ? WHERE (id) = ?", produto.getNome(), produto.getDescricao(), produto.getUrlImagem(), produto.getPreco(), produto.getCategoriaId(), id);
        return buscarPorId(id);
    }

    public void excluir(Long id) {
        jdbcTemplate.update("UPDATE Produtos p SET p.ativo = FALSE WHERE p.id = ?", id);
    }

    public Boolean possuiMesmoNome(String nome){
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM Produtos p WHERE p.nome = ?)", Boolean.class, nome);
    }
}
