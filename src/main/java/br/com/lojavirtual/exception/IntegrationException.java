package br.com.lojavirtual.exception;

public class IntegrationException extends RuntimeException {
    public IntegrationException() {
        super("Ocorreu um erro interno da aplicação.");
    }
}
