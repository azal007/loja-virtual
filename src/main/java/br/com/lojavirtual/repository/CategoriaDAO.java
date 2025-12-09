package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.Categoria;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class CategoriaDAO extends BaseDAO {
    private final JdbcTemplate jdbcTemplate;
    @Getter
    private final List<Object> pageParametros;

    public CategoriaDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.pageParametros = new ArrayList<>();
    }

    public Categoria buscarPorId(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM categoria c WHERE c.id = ? AND ativo = TRUE", new BeanPropertyRowMapper<>(Categoria.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao buscar a categoria com id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public List<Categoria> listar(Boolean ativo, Integer numeroPagina, Integer tamanhoPagina){
        try {
            String sql = "SELECT * FROM categoria c WHERE 1=1";
            ArrayList<Object> parametros = new ArrayList<>();

            if (!Objects.isNull(ativo)) {
                sql += " AND c.ativo = ?";
                parametros.add(ativo);
            }

            if (!Objects.isNull(tamanhoPagina)) {
                numeroPagina = (numeroPagina - 1) * tamanhoPagina;
                sql += " LIMIT ? OFFSET ?";
                parametros.add(tamanhoPagina);
                parametros.add(numeroPagina);
            }
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Categoria.class), parametros.toArray());

        } catch (Exception e) {
            log.error("Ocorreu um erro ao listar as categorias.", e);
            throw new IntegrationException();
        }
    }

    public Categoria incluir(Categoria categoria) {
        try {
            jdbcTemplate.update("INSERT INTO categoria (nome, id_categoria_pai) VALUES (?, ?)", categoria.getNome(), categoria.getIdCategoriaPai());
            // Recupera o último ID inserido no banco de dados
            Long id = jdbcTemplate.queryForObject("SELECT c.id  FROM categoria c WHERE c.id = LAST_INSERT_ID()", Long.class);
            // Retorna a categoria recém-inserida com o ID gerado
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar a categoria.", e);
            throw new IntegrationException();
        }
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        try {
            jdbcTemplate.update("UPDATE categoria c SET c.nome = ?, c.ativo = ? WHERE  (id) = ?", categoria.getNome(), categoria.getAtivo(), id);
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao atualizar a categoria {}.", id, e);
            throw new IntegrationException();
        }
    }

    public void excluir(Long id) {
        try {
            jdbcTemplate.update("UPDATE categoria c SET c.ativo = FALSE  WHERE id = ?", id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao excluir a categoria com id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public Boolean existeCategoria(Long idCategoriaPai) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM categoria c WHERE c.id = ? AND c.ativo = TRUE)", Boolean.class, idCategoriaPai);
    }

    public Boolean existeFilhosNaCategoria(Long idCategoriaPai){
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM categoria c WHERE c.id_categoria_pai = ? AND c.ativo = TRUE)", Boolean.class, idCategoriaPai);
    }

    public String obterParametros(Boolean ativo) {
        String sqlFromWhere = "";
        pageParametros.clear();

        if (!Objects.isNull(ativo)) {
            sqlFromWhere += " AND ativo = ?";
            pageParametros.add(ativo);
        }

        return sqlFromWhere;
    }
}
