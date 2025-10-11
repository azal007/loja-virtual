package br.com.lojavirtual.exception;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class ValidationException extends RuntimeException {
    private final HashMap<String, String> errors;

    public ValidationException(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
