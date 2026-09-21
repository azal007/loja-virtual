package br.com.lojavirtual.exception;

import lombok.Getter;
import org.springframework.dao.EmptyResultDataAccessException;

@Getter
public class EntityNotFoundException extends EmptyResultDataAccessException {
    private final String name;
    private final Long id;

    public EntityNotFoundException(String name, Long id) {
        super(1);
        this.name = name;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return message();
    }

    public String message() {
        return "Could not find the " + getName().toLowerCase() + " with id " + getId() + ".";
    }
}
