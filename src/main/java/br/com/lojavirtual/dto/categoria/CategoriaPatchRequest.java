package br.com.lojavirtual.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaPatchRequest {
    private Long id;
    private String nome;
    private Long idCategoriaPai;
}
