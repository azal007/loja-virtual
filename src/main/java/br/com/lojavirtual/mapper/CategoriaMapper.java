package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.CategoriaDTO;
import br.com.lojavirtual.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    private Long id;
    private String nome;
    private Long idCategoriaPai;
    private Boolean ativo;

    public CategoriaDTO toDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.idCategoriaPai = categoria.getIdCategoriaPai();
        this.ativo = categoria.getAtivo();
        return new CategoriaDTO(this.id, this.nome, this.idCategoriaPai, this.ativo);
    }
    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        this.id = categoriaDTO.getId();
        this.nome = categoriaDTO.getNome();
        this.idCategoriaPai = categoriaDTO.getIdCategoriaPai();
        this.ativo = categoriaDTO.getAtivo();
        return new Categoria(this.id, this.nome, this.idCategoriaPai, this.ativo);
    }
}

//entidade -> dao -> service -> controller