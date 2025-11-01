package br.com.lojavirtual.dto.usuario;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String senha;
}
