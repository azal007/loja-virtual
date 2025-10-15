package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UsuarioDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Usuario buscarPorId(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Usuarios u WHERE u.id = ?", Usuario.class, id);
        } catch (EmptyResultDataAccessException e) {
           throw e;
        }  catch (Exception e) {
            log.error("Ocorreu um erro ao buscar o usuário com id {}.", id, e);
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
}
