package br.com.lojavirtual.config;

import java.time.LocalDateTime;

import br.com.lojavirtual.exception.BusinessException;
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
}

