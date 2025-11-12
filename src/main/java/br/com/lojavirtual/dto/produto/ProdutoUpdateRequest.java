package br.com.lojavirtual.dto.produto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoUpdateRequest {
    @NotEmpty
    private String nome;
    @NotEmpty
    private String descricao;
    @NotEmpty
    private String urlImagem;
    @NotNull
    private BigDecimal preco;
    @NotNull
    private Long categoriaId;
}
