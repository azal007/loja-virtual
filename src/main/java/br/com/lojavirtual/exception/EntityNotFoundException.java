package br.com.lojavirtual.exception;

import lombok.Getter;
import org.springframework.dao.EmptyResultDataAccessException;

@Getter
public class EntityNotFoundException extends EmptyResultDataAccessException {
    private final String nome;
    private final Long id;

    public EntityNotFoundException(String nome, Long id) {
        super(1);
        this.nome = nome;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return message();
    }

    public String message() {
        return "Não foi possível encontrar a " + getNome().toLowerCase() + " com id " + getId() + ".";
    }
}
