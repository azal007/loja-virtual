package br.com.lojavirtual.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoPatchRequest {
    private String nome;
    private String descricao;
    private String urlImagem;
    private BigDecimal preco;
    private Long categoriaId;
}
