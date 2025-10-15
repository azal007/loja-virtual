package br.com.lojavirtual.dto;

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
}
