package br.com.lojavirtual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String urlImagem;
    private Double preco;
    private Boolean ativo;
    private Date criadoEm;
    private Date atualizadoEm;
    private Long categoriaId;
}
