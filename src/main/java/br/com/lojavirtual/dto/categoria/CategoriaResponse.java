package br.com.lojavirtual.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponse {
    private Long id;
    private String nome;
    private Long idCategoriaPai;
    private Boolean ativo;
    private Date criadoEm;
    private Date atualizadoEm;
}