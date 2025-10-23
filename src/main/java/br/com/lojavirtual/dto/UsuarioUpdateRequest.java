package br.com.lojavirtual.dto;

import br.com.lojavirtual.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateRequest {
    private String nome;
    private String apelido;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private boolean habilitarNotificacoesPromocoes;

    HashMap<String, String> errors = new HashMap<>();

    public void validate() {
        if (nome == null || nome.isBlank()) {
            errors.put("nome", "O campo nome é obrigatório.");
        }

        if (apelido == null || apelido.isBlank()) {
            errors.put("apelido", "O campo apelido é obrigatório.");
        }

        if (cpf == null || cpf.isBlank()) {
            errors.put("cpf", "O campo cpf é obrigatório.");
        }

        if (dataNascimento == null) {
            errors.put("dataNascimento", "O campo dataNascimento é obrigatório.");
        }

        if (email == null || email.isBlank()) {
            errors.put("email", "O campo email é obrigatório.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
