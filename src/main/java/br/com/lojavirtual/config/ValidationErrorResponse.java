package br.com.lojavirtual.config;

import br.com.lojavirtual.constants.Constants;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse {

    List<String> validationErrors;

    public ValidationErrorResponse(List<String> validationErrors) {
        this.setDataHora(LocalDateTime.now());
        this.setMensagem(Constants.MSG_ERRO_VALIDACAO_CAMPOS);
        this.validationErrors = validationErrors;
    }
}
