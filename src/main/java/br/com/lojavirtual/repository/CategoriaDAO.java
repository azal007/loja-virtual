package br.com.lojavirtual.repository;

import br.com.lojavirtual.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class CategoriaDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Categoria buscarPorId(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Categoria WHERE id = ? AND ativo = TRUE", new BeanPropertyRowMapper<>(Categoria.class), id);
    }

    public List<Categoria> listar(){
        return jdbcTemplate.query("SELECT * FROM Categoria WHERE ativo = TRUE", new BeanPropertyRowMapper<>(Categoria.class));
    }

    public void incluir(Categoria categoria) {
        jdbcTemplate.update("INSERT INTO Categoria (nome, id_categoria_pai) VALUES (?, ?)", categoria.getNome(), categoria.getIdCategoriaPai());
    }

    public void atualizar(Long id, Categoria categoria) {
        jdbcTemplate.update("UPDATE Categoria SET nome = ?, ativo = ? WHERE  (id) = ?", categoria.getNome(), categoria.getAtivo(), id);
    }

    public void excluir(Long id) {
        jdbcTemplate.update("UPDATE Categoria SET ativo = FALSE  WHERE id = ?", id);
    }

    public boolean existe(Long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT COUNT(id) = 1 FROM Categoria WHERE id_categoria_pai = ? AND ativo = TRUE", Boolean.class, id));
    }
}
