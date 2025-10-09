package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.CategoriaDTO;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.CustomEmptyResultDataAccessException;
import br.com.lojavirtual.mapper.CategoriaMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.repository.CategoriaDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoriaService {
    private final CategoriaDAO categoriaDAO;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaDAO categoriaDAO, CategoriaMapper categoriaMapper) {
        this.categoriaDAO = categoriaDAO;
        this.categoriaMapper = categoriaMapper;
    }

    // TODO: Adicionar validação que não permita cadastrar categorias com o mesmo nome
    public CategoriaDTO buscarPorId(Long id) throws CustomEmptyResultDataAccessException{
        try {
            Categoria categoria = categoriaDAO.buscarPorId(id);
            return categoriaMapper.toDTO(categoria);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomEmptyResultDataAccessException(Categoria.class.getSimpleName(), id);
        }
    }

    public List<CategoriaDTO> listar(){
        List<Categoria> categoria = categoriaDAO.listar();

        return categoria.stream().map(categoriaMapper::toDTO).toList();
    }

    public CategoriaDTO incluir(CategoriaDTO categoriaDTO) {
        if (Objects.isNull(categoriaDTO)) {
            throw new BusinessException("Preencha os campos obrigatórios");
        }

        // Se, no momento de incluir uma categoria, eu informar uma categoria pai diferente de null, prossigo.
        if (categoriaDTO.getIdCategoriaPai() != null) {
            // Se, no momento que eu for incluir uma categoria, eu informar uma categoria pai que não existe, lanço uma exception
            Boolean existe = categoriaDAO.existeCategoriaPai(categoriaDTO.getIdCategoriaPai());
            if (!existe) {
                throw new BusinessException("Categoria pai informada não existe");
            }
        }
        System.out.println(categoriaDTO.getNome());
        Boolean possuiMesmoNome = categoriaDAO.possuiMesmoNome(categoriaDTO.getNome());
        System.out.println(possuiMesmoNome);

        if (possuiMesmoNome) {
            throw new BusinessException("Não é possível cadastrar categorias com o mesmo nome.");
        }

        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        return categoriaMapper.toDTO(categoriaDAO.incluir(categoria));
    }

    public CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDTO) {
        // Se, no momento de atualizar uma categoria, eu não informar os dados obrigatórios, lanço uma exception.
        if (Objects.isNull(categoriaDTO)) {
            throw new BusinessException("Preencha os campos obrigatórios");
        }
        // Se, no momento de atualizar uma categoria, eu informar uma categoria que não existe, lanço uma exception.
        Categoria obterCategoria = categoriaDAO.buscarPorId(id);
        if (Objects.isNull(obterCategoria)) {
            throw new BusinessException("Categoria pai informada não existe");
         }

        // Se, no momento de atualizar uma categoria, eu informar uma categoria pai
        if (categoriaDTO.getIdCategoriaPai() != null) {
            // Se, no momento de atualizar uma categoria, for informada uma categoria pai que não existe, lanço uma exception.
            Boolean existe = categoriaDAO.existeCategoriaPai(categoriaDTO.getIdCategoriaPai());
            if (!existe) {
                throw new BusinessException("Categoria pai informada não existe");
            }
        }
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        return categoriaMapper.toDTO(categoriaDAO.atualizar(id, categoria));
    }

    public void excluir(Long id) {
        Categoria obterCategoria = categoriaDAO.buscarPorId(id);
        // Se, no momento de excluir uma categoria, eu não informar um ID
        // ou se o ID informado não existir no banco de dados, lanço uma exception.
        if (Objects.isNull(id) || Objects.isNull(obterCategoria)) {
            throw new BusinessException("Categoria não encontrada");
        }
        categoriaDAO.excluir(id);
    }
}
