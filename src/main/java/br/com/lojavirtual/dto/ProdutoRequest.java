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
public class ProdutoRequest {
    private String nome;
    private String descricao;
    private String urlImagem;
    private Double preco;
    private Long categoriaId;

    HashMap<String, String> errors = new HashMap<>();

    public void validate() {
        if (nome == null || nome.isBlank()) {
            errors.put("nome", "O campo nome é obrigatório.");
        }

        if (urlImagem == null || urlImagem.isBlank()) {
            errors.put("urlImagem", "O campo urlImagem é obrigatório.");
        }

        if (preco == null) {
            errors.put("preco", "O campo preco é obrigatório.");
        }

        if (categoriaId == null) {
            errors.put("categoriaId", "O campo categoriaId é obrigatório.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}