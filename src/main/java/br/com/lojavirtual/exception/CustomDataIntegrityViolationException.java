package br.com.lojavirtual.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class CustomDataIntegrityViolationException extends DataIntegrityViolationException {
    public CustomDataIntegrityViolationException(String message) {
        super(message);
    }
}
