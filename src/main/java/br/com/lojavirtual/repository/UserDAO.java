package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import br.com.lojavirtual.model.User;
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
public class UserDAO extends BaseDAO {
    private final JdbcTemplate jdbcTemplate;
    @Getter
    private final List<Object> pageParameters;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.pageParameters = new ArrayList<>();
    }

    public User findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM `user` u WHERE u.id = ?", new BeanPropertyRowMapper<>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
           throw e;
        }  catch (Exception e) {
            log.error("Error occurred while finding user with id {}.", id, e);
            throw new IntegrationException();
        }
    }

    public String buildFromWhereListing(List<Object> parameters, String name, String cpf, String email, Boolean active) {
        String sqlFromWhere = "FROM `user` u WHERE 1=1";

        if (!Objects.isNull(name)) {
            sqlFromWhere += " AND u.name LIKE ?";
            parameters.add("%" + name + "%");
        }

        if (!Objects.isNull(cpf)) {
            sqlFromWhere += " AND cpf LIKE ?";
            parameters.add("%" + cpf + "%");
        }

        if (!Objects.isNull(email)) {
            sqlFromWhere += " AND email LIKE ?";
            parameters.add("%" + email + "%");
        }

        if (!Objects.isNull(active)) {
            sqlFromWhere += " AND active = ?";
            parameters.add(active);
        }


        return sqlFromWhere;
    }

    public List<User> list(String name, String cpf, String email, Boolean active, Integer pageNumber, Integer pageSize) {
        try {
            String sql = "SELECT * ";
            List<Object> parameters = new ArrayList<>();

            sql += buildFromWhereListing(parameters, name, cpf, email, active);

            if (!Objects.isNull(pageSize)) {
                pageNumber = (pageNumber - 1) * pageSize;
                sql += " LIMIT ? OFFSET ?";
                parameters.add(pageSize);
                parameters.add(pageNumber);
            }
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), parameters.toArray());
        } catch (Exception e) {
            log.error("Error occurred while listing users.", e);
            throw new IntegrationException();
        }
    }

    public Integer countList(String name, String cpf, String email, Boolean active) {
        try {
            String sql = "SELECT count(*) ";
            List<Object> parameters = new ArrayList<>();

            sql += buildFromWhereListing(parameters, name, cpf, email, active);

            return jdbcTemplate.queryForObject(sql, Integer.class, parameters.toArray());
        } catch (Exception e) {
            log.error("Error occurred while listing users.", e);
            throw new IntegrationException();
        }
    }


    public User insert(User user) {
        try {
            jdbcTemplate.update("INSERT INTO `user` (email, password, admin) VALUES (?, ?, FALSE)", user.getEmail(), user.getPassword());
            Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() AS id", Long.class);
            return findById(id);
        } catch (Exception e) {
            log.error("Error occurred while creating user.", e);
            throw new IntegrationException();
        }
    }

    public User update(Long id, User user) {
        try {
            jdbcTemplate.update("UPDATE `user` u SET u.name = ?, u.nickname = ?, u.cpf = ?, u.birth_date = ?, u.email = ?, u.enable_promotional_notifications = ?, u.admin = ? WHERE u.id = ?", user.getName(), user.getNickname(), user.getCpf(), user.getBirthDate(), user.getEmail(), user.getEnablePromotionalNotifications(), user.getAdmin(), id);
            return findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating user.", e);
            throw new IntegrationException();
        }
    }

    public void delete(Long id) {
        try {
            jdbcTemplate.update("UPDATE `user` u SET u.active = FALSE WHERE u.id = ?", id);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating user.", e);
            throw new IntegrationException();
        }
    }

    public Boolean hasSameEmail(String email, Long id) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT 1 FROM `user` u WHERE u.email = ? AND u.id <> ? AND u.email <> '')", Boolean.class, email, id);
    };

    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM `user` u WHERE u.email = ?", new BeanPropertyRowMapper<>(User.class), email);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        }  catch (Exception e) {
            log.error("Error occurred while finding user with email {}.", email, e);
            throw new IntegrationException();
        }
    }
}
