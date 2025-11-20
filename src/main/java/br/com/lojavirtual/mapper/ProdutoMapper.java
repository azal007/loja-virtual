package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.produto.ProdutoPatchRequest;
import br.com.lojavirtual.dto.produto.ProdutoRequest;
import br.com.lojavirtual.dto.produto.ProdutoUpdateRequest;
import br.com.lojavirtual.model.Page;
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.dto.produto.ProdutoResponse;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
public class ProdutoMapper {
    public ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getUrlImagem(),
                produto.getPreco().setScale(2, RoundingMode.HALF_UP),
                produto.getAtivo(),
                produto.getCriadoEm(),
                produto.getAtualizadoEm(),
                produto.getCategoriaId()
        );
    }

    public ProdutoResponse toResponse(Produto produto, Page page) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getUrlImagem(),
                produto.getPreco().setScale(2, RoundingMode.HALF_UP),
                produto.getAtivo(),
                produto.getCriadoEm(),
                produto.getAtualizadoEm(),
                produto.getCategoriaId(),
                new Page(
                        page.getNumeroPagina(),
                        page.getTamanhoPagina(),
                        page.getTotalElementos(),
                        page.getTotalPaginas()
                )
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

    public Produto toEntityUpdate(ProdutoUpdateRequest request) {
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

    public Produto toEntityPatch(ProdutoPatchRequest request) {
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