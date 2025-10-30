package br.com.lojavirtual.dto.usuario;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateRequest {
    @NotEmpty
    private String nome;
    @NotEmpty
    private String apelido;
    @NotEmpty
    private String cpf;
    @NotEmpty
    private Date dataNascimento;
    @NotEmpty
    private String email;
    private boolean habilitarNotificacoesPromocoes;
}
