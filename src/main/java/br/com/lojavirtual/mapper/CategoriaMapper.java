package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.categoria.CategoriaPatchRequest;
import br.com.lojavirtual.dto.categoria.CategoriaRequest;
import br.com.lojavirtual.dto.categoria.CategoriaResponse;
import br.com.lojavirtual.dto.categoria.CategoriaUpdateRequest;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.model.Page;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getIdCategoriaPai(),
            categoria.getAtivo(),
            categoria.getCriadoEm(),
            categoria.getAtualizadoEm()
        );
    }

    public Categoria toEntity(CategoriaRequest request) {
        return new Categoria(
            null,
            request.getNome(),
            request.getIdCategoriaPai(),
            Boolean.TRUE,
            null,
            null
        );
    }
    public Categoria toEntityUpdate(CategoriaUpdateRequest request) {
        return new Categoria(
                null,
                request.getNome(),
                request.getIdCategoriaPai(),
                Boolean.TRUE,
                null,
                null
        );
    }

    public Categoria toEntityPatch(CategoriaPatchRequest request) {
        return new Categoria(
                null,
                request.getNome(),
                request.getIdCategoriaPai(),
                Boolean.TRUE,
                null,
                null
        );
    }

}
