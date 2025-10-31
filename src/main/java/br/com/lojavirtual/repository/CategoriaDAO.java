package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.Categoria;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Slf4j
@Repository
public class CategoriaDAO extends BaseDAO {
    private final JdbcTemplate jdbcTemplate;

    public CategoriaDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    public Categoria buscarPorId(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Categorias c WHERE c.id = ? AND ativo = TRUE", new BeanPropertyRowMapper<>(Categoria.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao buscar a categoria com id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public List<Categoria> listar(){
        try {
            return jdbcTemplate.query("SELECT * FROM Categorias c WHERE c.ativo = TRUE", new BeanPropertyRowMapper<>(Categoria.class));
        } catch (Exception e) {
            log.error("Ocorreu um erro ao listar as categorias.", e);
            throw new IntegrationException();
        }
    }

    public Categoria incluir(Categoria categoria) {
        try {
            jdbcTemplate.update("INSERT INTO Categorias (nome, id_categoria_pai) VALUES (?, ?)", categoria.getNome(), categoria.getIdCategoriaPai());
            // Recupera o último ID inserido no banco de dados
            Long id = jdbcTemplate.queryForObject("SELECT c.id  FROM Categorias c WHERE c.id = LAST_INSERT_ID()", Long.class);
            // Retorna a categoria recém-inserida com o ID gerado
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar a categoria.", e);
            throw new IntegrationException();
        }
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        try {
            jdbcTemplate.update("UPDATE Categorias c SET c.nome = ?, c.ativo = ? WHERE  (id) = ?", categoria.getNome(), categoria.getAtivo(), id);
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao atualizar a categoria {}.", id, e);
            throw new IntegrationException();
        }
    }

    public void excluir(Long id) {
        try {
            jdbcTemplate.update("UPDATE Categorias c SET c.ativo = FALSE  WHERE id = ?", id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao excluir a categoria com id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public Boolean existeCategoria(Long idCategoriaPai) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM Categorias c WHERE c.id = ? AND c.ativo = TRUE)", Boolean.class, idCategoriaPai);
    }

    public Boolean existeFilhosNaCategoria(Long idCategoriaPai){
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM Categorias c WHERE c.id_categoria_pai = ? AND c.ativo = TRUE)", Boolean.class, idCategoriaPai);
    }
}
