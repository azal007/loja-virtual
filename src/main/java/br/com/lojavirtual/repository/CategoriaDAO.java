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
        // Recupera o último ID inserido no banco de dados
        Long id = jdbcTemplate.queryForObject("SELECT c.id  FROM Categoria c WHERE c.id = LAST_INSERT_ID()", Long.class);
        // Retorna a categoria recém-inserida com o ID gerado
        return buscarPorId(id);
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        jdbcTemplate.update("UPDATE Categoria c SET c.nome = ?, c.ativo = ? WHERE  (id) = ?", categoria.getNome(), categoria.getAtivo(), id);
        return buscarPorId(id);
    }

    public void excluir(Long id) {
        jdbcTemplate.update("UPDATE Categoria c SET c.ativo = FALSE  WHERE id = ?", id);
    }

    public Boolean existeCategoriaPai(Long idCategoriaPai) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM Categoria c WHERE c.id = ? AND c.ativo = TRUE)", Boolean.class, idCategoriaPai);
    }

    public Boolean existeFilhosNaCategoriaPai(Long idCategoriaPai){
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM Categoria c WHERE c.id_categoria_pai = ? AND c.ativo = TRUE)", Boolean.class, idCategoriaPai);
    }

    public Boolean possuiMesmoNome(String nome) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) > 0 FROM Categoria c WHERE c.nome = ?", Boolean.class, nome);
    }
}
