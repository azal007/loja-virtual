package br.com.lojavirtual.dto.usuario;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPatchRequest {
    private String nome;
    private String apelido;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private Boolean habilitarNotificacoesPromocoes;
}
