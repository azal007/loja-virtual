package br.com.lojavirtual.config;

import java.time.LocalDateTime;

import br.com.lojavirtual.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LojaVirtualExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erroResponse);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<?> handleIntegrationException(IntegrationException e) {
        ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erroResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(erroResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        ValidationResponse response = new ValidationResponse(LocalDateTime.now(), e.getErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}

