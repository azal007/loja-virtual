package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.CategoriaDTO;
import br.com.lojavirtual.mapper.CategoriaMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.repository.CategoriaDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

// TODO: Ver como fazer o tratamento de exceções no spring boot sem usar ControlerAdvice, entender melhor o fluxo das exceções e quando lançá-las
@Service
public class CategoriaService {
    private final CategoriaDAO categoriaDAO;
    private final CategoriaDTO categoriaDTO;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaDAO categoriaDAO, CategoriaDTO categoriaDTO, CategoriaMapper categoriaMapper) {
        this.categoriaDAO = categoriaDAO;
        this.categoriaDTO = categoriaDTO;
        this.categoriaMapper = categoriaMapper;
    }

    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaDAO.buscarPorId(id);

        // Se no momento que eu for buscar uma categoria, eu não informar um id,
        // ou se no banco de dados não existir o id informado, eu lanço uma exception
        if (Objects.isNull(id) || Objects.isNull(categoria)) {
            throw new RuntimeException("Categoria não encontrada");
        }
        return categoriaMapper.toDTO(categoria);
    }

    public List<CategoriaDTO> listar(){
        List<Categoria> categoria = categoriaDAO.listar();

        return categoria.stream().map(categoriaMapper::toDTO).toList();
    }

    public CategoriaDTO incluir(CategoriaDTO categoriaDto) {
        // Se no momento que eu for incluir uma categoria, eu informar uma categoria pai que não existe,
        // ou se no banco de dados não existir categoria que contenha a categoria pai informada, eu lanço uma exception
        if (Objects.isNull(categoriaDto.getIdCategoriaPai()) || categoriaDAO.ehNulo(categoriaDto.getIdCategoriaPai())) {
            throw new RuntimeException("Categoria pai informada não existe");
        }
        Categoria categoria = categoriaMapper.toEntity(categoriaDto);

        return categoriaMapper.toDTO(categoriaDAO.incluir(categoria));
    }

    public CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDto) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDto);
        Categoria obterCategoria = categoriaDAO.buscarPorId(id);

        // Se no momento que eu for atualizar uma categoria, eu não informar um id,
        // ou se no banco de dados não existir o id informado, eu lanço uma exception
        if (Objects.isNull(id) || Objects.isNull(obterCategoria)) {
            throw new RuntimeException("Categoria pai informada não existe");
         }
        return categoriaMapper.toDTO(categoriaDAO.atualizar(id, categoria));
    }

    public void excluir(Long id) {
        Categoria obterCategoria = categoriaDAO.buscarPorId(id);

        // Se no momento que eu for excluir uma categoria, eu não informar um id,
        // ou se no banco de dados não existir o id informado, eu lanço uma exception
        if (Objects.isNull(id) || Objects.isNull(obterCategoria)) {
            throw new RuntimeException("Categoria não encontrada");
        }
        categoriaDAO.excluir(id);
    }
}
