package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UsuarioDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Usuario buscarPorId(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Usuarios u WHERE u.id = ?", new BeanPropertyRowMapper<>(Usuario.class), id);
        } catch (EmptyResultDataAccessException e) {
           throw e;
        }  catch (Exception e) {
            log.error("Ocorreu um erro ao buscar o usuário com id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public List<Usuario> listar(String nome, String cpf, String email, Boolean ativo) {
        try {
            String sql = "SELECT * FROM Usuarios u WHERE 1=1";
            List<Object> parametros = new ArrayList<>();

            if (nome != null) {
                sql += " AND u.nome LIKE ?";
                parametros.add("%" + nome + "%");
            }
            if (cpf != null) {
                sql += " AND cpf LIKE ?";
                parametros.add("%" + cpf + "%");
            }

            if (email != null) {
                sql += " AND email LIKE ?";
                parametros.add("%" + email + "%");
            }

            if (ativo != null) {
                sql += " AND ativo = ?";
                parametros.add(ativo);
            }
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Usuario.class), parametros.toArray());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao listar os usuários.", e);
            throw new IntegrationException();
        }
    }

    public Usuario incluir(Usuario usuario) {
        try {
            jdbcTemplate.update("INSERT INTO Usuarios (email, senha) VALUES (?, ?)", usuario.getEmail(), usuario.getSenha());
            Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() AS id", Long.class);
            return buscarPorId(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao criar o usuário.", e);
            throw new IntegrationException();
        }
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        try {
            log.info(usuario.getNome());
            jdbcTemplate.update("UPDATE Usuarios u SET u.nome = ?, u.apelido = ?, u.cpf = ?, u.dataNascimento = ?, u.email = ?, u.habilitarNotificacoesPromocoes = ? WHERE u.id = ?", usuario.getNome(), usuario.getApelido(), usuario.getCpf(), usuario.getDataNascimento(), usuario.getEmail(), usuario.isHabilitarNotificacoesPromocoes(), id);
            return buscarPorId(id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao atualizar o usuário.", e);
            throw new IntegrationException();
        }
    }
}
