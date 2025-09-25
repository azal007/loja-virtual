package br.com.lojavirtual.repository;

import br.com.lojavirtual.model.Categoria;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class CategoriaDAO {
    private final JdbcTemplate jdbcTemplate;

    public CategoriaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Categoria buscarPorId(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Categoria c WHERE c.id = ? AND ativo = TRUE", new BeanPropertyRowMapper<>(Categoria.class), id);
    }

    public List<Categoria> listar(){
        return jdbcTemplate.query("SELECT * FROM Categoria c WHERE c.ativo = TRUE", new BeanPropertyRowMapper<>(Categoria.class));
    }

    public Categoria incluir(Categoria categoria) {
        jdbcTemplate.update("INSERT INTO Categoria (nome, id_categoria_pai) VALUES (?, ?)", categoria.getNome(), categoria.getIdCategoriaPai());
        // Obtendo o último Id inserido no banco de dados
        Long id = jdbcTemplate.queryForObject("SELECT c.id  FROM Categoria c WHERE c.id = LAST_INSERT_ID()", Long.class);
        // retornando a categoria inserida com o Id
        return jdbcTemplate.queryForObject("SELECT * FROM Categoria c WHERE c.id = ?", new BeanPropertyRowMapper<>(Categoria.class), id);
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        jdbcTemplate.update("UPDATE Categoria c SET c.nome = ?, c.ativo = ? WHERE  (id) = ?", categoria.getNome(), categoria.getAtivo(), id);
        return jdbcTemplate.queryForObject("SELECT c.id FROM Categoria c WHERE c.id = ?", new BeanPropertyRowMapper<>(Categoria.class), id);
    }

    public void excluir(Long id) {
        jdbcTemplate.update("UPDATE Categoria c SET c.ativo = FALSE  WHERE id = ?", id);
    }

    public boolean ehNulo(Long id_categoria_pai) {
        // Seleciona o campo c.id da tabela Categoria e faz somatória de quantos "c.ids"
        // correspondem ao id_categoria_pai passado como parâmetro da consulta, se for igual a 0
        // retorna true (1), caso contrário retorna false (0).
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT COUNT(c.id) = 0 FROM Categoria c WHERE id_categoria_pai = ?", Boolean.class, id_categoria_pai));
    }
}
