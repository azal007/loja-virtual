package br.com.lojavirtual.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Long id;
    private String nome;
    private String apelido;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String senha;
    private boolean habilitarNotificacoesPromocoes;
    private boolean ativo;
    private Date criadoEm;
    private Date atualizadoEm;
}
