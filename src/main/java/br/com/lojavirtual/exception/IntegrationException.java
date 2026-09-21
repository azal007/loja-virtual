package br.com.lojavirtual.exception;

public class IntegrationException extends RuntimeException {
    public IntegrationException() {
        super("An internal application error occurred.");
    }
}
