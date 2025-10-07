package br.com.lojavirtual.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
        private Long id;
        private String nome;
        private Long idCategoriaPai;
        private Boolean ativo;
}
