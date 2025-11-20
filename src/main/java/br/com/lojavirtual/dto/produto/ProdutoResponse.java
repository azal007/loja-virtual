package br.com.lojavirtual.dto.produto;

import br.com.lojavirtual.model.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private String urlImagem;
    private BigDecimal preco;
    private Boolean ativo;
    private Date criadoEm;
    private Date atualizadoEm;
    private Long categoriaId;
    private Page page;

    public ProdutoResponse(Long id, String nome, String descricao, String urlImagem, BigDecimal preco, Boolean ativo, Date criadoEm, Date atualizadoEm, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
        this.preco = preco;
        this.ativo = ativo;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.categoriaId = categoriaId;
    }
}
