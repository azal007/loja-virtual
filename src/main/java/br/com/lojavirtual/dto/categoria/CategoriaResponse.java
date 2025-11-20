package br.com.lojavirtual.dto.categoria;

import br.com.lojavirtual.model.Page;
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
    private Page page;

    public CategoriaResponse(Long id, String nome, Long idCategoriaPai, Boolean ativo, Date criadoEm, Date atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.idCategoriaPai = idCategoriaPai;
        this.ativo = ativo;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}