package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.CategoriaDTO;
import br.com.lojavirtual.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(
            categoria.getId(),
            categoria.getNome(),
            categoria.getIdCategoriaPai(),
            categoria.getAtivo()
        );
    }

    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        return new Categoria(
            categoriaDTO.getId(),
            categoriaDTO.getNome(),
            categoriaDTO.getIdCategoriaPai(),
            categoriaDTO.getAtivo()
        );
    }
}
