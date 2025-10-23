package br.com.lojavirtual.dto;

import br.com.lojavirtual.exception.ValidationException;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    private String email;
    private String senha;
    private String confirmarSenha;

    HashMap<String, String> errors = new HashMap<>();

    public void validate() {
        if (email == null) {
            errors.put("email", "O campo email é obrigatório.");
        }

        if (senha == null) {
            errors.put("email", "O campo senha é obrigatório.");
        }

        if (confirmarSenha == null) {
            errors.put("confirmarSenha", "O campo confirmarSenha é obrigatório.");
        }

        if (senha != null && !senha.equals(confirmarSenha)) {
            errors.put("message", "As senhas não coincidem.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
