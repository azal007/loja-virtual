package br.com.lojavirtual.dto;

import br.com.lojavirtual.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {
    private String nome;
    private Long idCategoriaPai;

    public void validate() {
        HashMap<String, String> errors = new HashMap<>();

        if (nome == null || nome.isBlank()) {
            errors.put("nome", "O campo nome é obrigatório.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}