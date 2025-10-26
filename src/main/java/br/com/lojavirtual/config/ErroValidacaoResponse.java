package br.com.lojavirtual.config;

import br.com.lojavirtual.constantes.Constantes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErroValidacaoResponse extends ErroResponse {

    List<String> errosValidacao;

    public ErroValidacaoResponse(List<String> errosValidacao) {
        this.setDataHora(LocalDateTime.now());
        this.setMensagem(Constantes.MSG_ERRO_VALIDACAO_CAMPOS);
        this.errosValidacao = errosValidacao;
    }
}
