package br.com.lojavirtual.mapper;

import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.dto.ProdutoDTO;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    public ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
            produto.getId(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getUrlImagem(),
            produto.getPreco(),
            produto.getAtivo(),
            produto.getCriadoEm(),
            produto.getAtualizadoEm(),
            produto.getCategoriaId()
        );
    }

    public Produto toEntity(ProdutoDTO produtoDTO) {
        return new Produto(
            produtoDTO.getId(),
            produtoDTO.getNome(),
            produtoDTO.getDescricao(),
            produtoDTO.getUrlImagem(),
            produtoDTO.getPreco(),
            produtoDTO.getAtivo(),
            produtoDTO.getCriadoEm(),
            produtoDTO.getAtualizadoEm(),
            produtoDTO.getCategoriaId()
        );
    }
}