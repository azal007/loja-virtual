package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class BaseDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String table;

    public BaseDAO() {
        this.table = getTableName();
    }

    private String getTableName() {
        String entityName = getClass().getSimpleName();
        int length = entityName.length();

        if (entityName.endsWith("DAO")) {
            entityName = entityName.substring(0, length - 3);
        }
        String tableName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);

        // Add backticks for MySQL reserved words
        if (tableName.equals("user") || tableName.equals("order")) {
            return "`" + tableName + "`";
        }
        return tableName;
    }

    public Boolean hasSameName(String name, Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM " + this.table + " WHERE name = ? AND id <> ?)", Boolean.class, name, id);
        } catch (Exception e) {
            log.error("Error occurred while checking if there is a record in {} with the same name for id ({}).", this.table, id, e);
            throw new IntegrationException();
        }
    }
}
