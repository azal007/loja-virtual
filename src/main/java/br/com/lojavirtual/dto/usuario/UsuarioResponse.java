package br.com.lojavirtual.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String apelido;
    private String cpf;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    private String email;
    private boolean habilitarNotificacoesPromocoes;
    private boolean ativo;
    private Date criadoEm;
    private Date atualizadoEm;
}
