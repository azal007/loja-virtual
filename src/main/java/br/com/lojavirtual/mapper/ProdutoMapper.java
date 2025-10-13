package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.ProdutoRequest;
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.dto.ProdutoResponse;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    public ProdutoResponse toDTO(Produto produto) {
        return new ProdutoResponse(
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

    public Produto toEntity(ProdutoRequest request) {
        return new Produto(
            null,
            request.getNome(),
            request.getDescricao(),
            request.getUrlImagem(),
            request.getPreco(),
            Boolean.TRUE,
            null,
            null,
            request.getCategoriaId()
        );
    }
}