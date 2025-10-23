package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.CategoriaRequest;
import br.com.lojavirtual.dto.CategoriaResponse;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.EntityNotFoundException;
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

    public CategoriaResponse buscarPorId(Long id) {
        try {
            Categoria categoria = categoriaDAO.buscarPorId(id);
            return categoriaMapper.toResponse(categoria);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Categoria.class.getSimpleName(), id);
        }
    }

    public List<CategoriaResponse> listar() {
        List<Categoria> categoria = categoriaDAO.listar();

        return categoria.stream().map(categoriaMapper::toResponse).toList();
    }

    public CategoriaResponse incluir(CategoriaRequest request) {
        // Verifica se o campo "categoriId" está preenchido na requisição
        if (request.getIdCategoriaPai() != null) {
            // obtendo id da Categoria que veio na requisição
            Long categoriaPai = request.getIdCategoriaPai();
            // verificando se a categoria informada existe
            Boolean existe = categoriaDAO.existeCategoria(categoriaPai);
            if (!existe) {
                throw new BusinessException("Categoria pai informada não existe");
            }
        }
        // verificando se o nome da categoria informada já existe
        Boolean possuiMesmoNome = categoriaDAO.possuiMesmoNome(request.getNome());
        if (possuiMesmoNome) {
            throw new BusinessException("Não é possível cadastrar categorias com o mesmo nome.");
        }
        Categoria categoria = categoriaMapper.toEntity(request);
        return categoriaMapper.toResponse(categoriaDAO.incluir(categoria));
    }

    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        try {
            // Se, no momento de atualizar uma categoria, eu informar uma categoria que não existe, lanço uma exception.
            categoriaDAO.buscarPorId(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Produto.class.getSimpleName(), id);
        }
        // Se, no momento de atualizar uma categoria, eu informar uma categoria pai
        if (request.getIdCategoriaPai() != null) {
            // Se, no momento de atualizar uma categoria, for informada uma categoria pai que não existe, lanço uma exception.
            Boolean existe = categoriaDAO.existeCategoria(request.getIdCategoriaPai());
            if (!existe) {
                throw new BusinessException("Categoria pai informada não existe");
            }
        }
        Categoria categoria = categoriaMapper.toEntity(request);
        return categoriaMapper.toResponse(categoriaDAO.atualizar(id, categoria));
    }

    public void excluir(Long id) {
        try {
            Categoria obterCategoria = categoriaDAO.buscarPorId(id);
            if (!Objects.isNull(obterCategoria)) {
                categoriaDAO.excluir(id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Categoria.class.getSimpleName(), id);
        }
    }
}
