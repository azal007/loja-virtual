package br.com.lojavirtual.dto.usuario;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String senha;
    @NotEmpty
    private String confirmarSenha;
}
