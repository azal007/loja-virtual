package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.CategoriaRequest;
import br.com.lojavirtual.dto.CategoriaResponse;
import br.com.lojavirtual.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getIdCategoriaPai(),
            categoria.getAtivo()
        );
    }

    public Categoria toEntity(CategoriaRequest request) {
        return new Categoria(
            null,
            request.getNome(),
            request.getIdCategoriaPai(),
            Boolean.TRUE
        );
    }
}
