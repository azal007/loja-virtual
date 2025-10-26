package br.com.lojavirtual.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.lojavirtual.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LojaVirtualExceptionHandler {

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(String.format("Campo %s %s", fieldName, errorMessage));
        });
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroValidacaoResponse(errors));
    }
}

