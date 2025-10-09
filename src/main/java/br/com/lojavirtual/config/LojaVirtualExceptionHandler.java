package br.com.lojavirtual.config;

import java.time.LocalDateTime;

import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.CustomDataIntegrityViolationException;
import br.com.lojavirtual.exception.CustomEmptyResultDataAccessException;
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

    @ExceptionHandler(CustomEmptyResultDataAccessException.class)
    public ResponseEntity<?> handleCustomEmptyResultDataAccessException(CustomEmptyResultDataAccessException e){
        String mensagem = "Entidade: " + e.getNome() + ", ID: " + e.getId() + " - " + e.getMessage();
        ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), mensagem);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(erroResponse);
    }
    @ExceptionHandler(CustomDataIntegrityViolationException.class)
    public ResponseEntity<?> handleCustomDataIntegrityViolationException(CustomDataIntegrityViolationException e){
        ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erroResponse);
    }
}

