package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.Usuario;
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
public class UsuarioDAO extends BaseDAO {
    private final JdbcTemplate jdbcTemplate;
    @Getter
    private final List<Object> pageParametros;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.pageParametros = new ArrayList<>();
    }

    public Usuario buscarPorId(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM usuario u WHERE u.id = ?", new BeanPropertyRowMapper<>(Usuario.class), id);
        } catch (EmptyResultDataAccessException e) {
           throw e;
        }  catch (Exception e) {
            log.error("Ocorreu um erro ao buscar o usuário com id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public List<Usuario> listar(String nome, String cpf, String email, Boolean ativo, Integer numeroPagina, Integer tamanhoPagina) {
        try {
            String sql = "SELECT * FROM usuario u WHERE 1=1";
            List<Object> parametros = new ArrayList<>();

            if (!Objects.isNull(nome)) {
                sql += " AND u.nome LIKE ?";
                parametros.add("%" + nome + "%");
            }

            if (!Objects.isNull(cpf)) {
                sql += " AND cpf LIKE ?";
                parametros.add("%" + cpf + "%");
            }

            if (!Objects.isNull(email)) {
                sql += " AND email LIKE ?";
                parametros.add("%" + email + "%");
            }

            if (!Objects.isNull(ativo)) {
                sql += " AND ativo = ?";
                parametros.add(ativo);
            }

            if (!Objects.isNull(tamanhoPagina)) {
                numeroPagina = (numeroPagina - 1) * tamanhoPagina;
                sql += " LIMIT ? OFFSET ?";
                parametros.add(tamanhoPagina);
                parametros.add(numeroPagina);
            }
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Usuario.class), parametros.toArray());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao listar os usuários.", e);
            throw new IntegrationException();
        }
    }

    public Usuario incluir(Usuario usuario) {
        try {
            jdbcTemplate.update("INSERT INTO usuario (email, senha) VALUES (?, ?)", usuario.getEmail(), usuario.getSenha());
            Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() AS id", Long.class);
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o usuário.", e);
            throw new IntegrationException();
        }
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        try {
            jdbcTemplate.update("UPDATE usuario u SET u.nome = ?, u.apelido = ?, u.cpf = ?, u.dataNascimento = ?, u.email = ?, u.habilitarNotificacoesPromocoes = ? WHERE u.id = ?", usuario.getNome(), usuario.getApelido(), usuario.getCpf(), usuario.getDataNascimento(), usuario.getEmail(), usuario.isHabilitarNotificacoesPromocoes(), id);
            return buscarPorId(id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao atualizar o usuário.", e);
            throw new IntegrationException();
        }
    }

    public void excluir(Long id) {
        try {
            jdbcTemplate.update("UPDATE usuario u SET u.ativo = FALSE WHERE u.id = ?", id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao atualizar o usuário.", e);
            throw new IntegrationException();
        }
    }

    public Boolean validaPossuiMesmoEmail(String email, Long id) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT 1 FROM usuario u WHERE u.email = ? AND u.id <> ? AND u.email <> '')", Boolean.class, email, id);
    };

    public Usuario buscarPorEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM usuario u WHERE u.email = ? AND u.ativo = TRUE",
                new BeanPropertyRowMapper<>(Usuario.class),
                email
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao buscar o usuario pelo email {}.", email, e);
            throw new IntegrationException();
        }
    }

    public String obterParametros(String nome, String cpf, String email, Boolean ativo) {
        String sqlFromWhere = "";
        pageParametros.clear();

        if (!Objects.isNull(nome)) {
            sqlFromWhere += " AND nome LIKE ?";
            pageParametros.add("%" + nome + "%");
        }

        if (!Objects.isNull(cpf)) {
            sqlFromWhere += " AND cpf LIKE ?";
            pageParametros.add("%" + cpf + "%");
        }

        if (!Objects.isNull(email)) {
            sqlFromWhere += " AND email LIKE ?";
            pageParametros.add("%" + email + "%");
        }

        if (!Objects.isNull(ativo)) {
            sqlFromWhere += " AND ativo = ?";
            pageParametros.add(ativo);
        }

        return sqlFromWhere;
    }
}
