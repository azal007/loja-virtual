package br.com.lojavirtual.exception;

import lombok.Getter;
import org.springframework.dao.EmptyResultDataAccessException;

@Getter
public class CustomEmptyResultDataAccessException extends EmptyResultDataAccessException {
    private final String nome;
    private final Long id;

    public CustomEmptyResultDataAccessException(String nome, Long id) {
        super(1);
        this.nome = nome;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return message();
    }

    public String message() {
        return "Não foi possível encontrar a entidade " + getNome() + " com o ID " + getId();
    }
}
