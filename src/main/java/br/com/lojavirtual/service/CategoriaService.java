package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.CategoriaDTO;
import br.com.lojavirtual.mapper.CategoriaMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.repository.CategoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaDAO categoriaDAO;
    @Autowired
    private CategoriaMapper categoriaMapper;

    // Pesquisar sobre Businness Exception
    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaDAO.buscarPorId(id);

        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada");
        } else {
            return categoriaMapper.toDTO(categoria);
        }
    }

    public List<CategoriaDTO> listar(){
        List<Categoria> categoria = categoriaDAO.listar();

        return categoria.stream().map(categoriaMapper::toDTO).toList();
    }

    public CategoriaDTO incluir(Categoria categoria) {
        if(Objects.nonNull(categoria.getIdCategoriaPai()) && !categoriaDAO.existe(categoria.getIdCategoriaPai())) {
            throw new RuntimeException("Categoria pai informada não existe");
        }
        categoriaDAO.incluir(categoria);

        return categoriaMapper.toDTO(categoria);
    }

    public CategoriaDTO atualizar(Long id, Categoria categoria) {
        if (categoriaDAO.buscarPorId(id) == null) {
            System.out.println("Categoria não encontrada");
        } else {
            categoriaDAO.atualizar(id, categoria);
            return categoriaMapper.toDTO(categoria);
        }
        return null;
    }

    public CategoriaDTO excluir(Long id) {
        Categoria categoria = categoriaDAO.buscarPorId(id);

        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada");
        } else {
            categoriaDAO.excluir(id);
        }
        return categoriaMapper.toDTO(categoria);
    }
}
