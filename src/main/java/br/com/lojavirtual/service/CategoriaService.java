package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.categoria.CategoriaRequest;
import br.com.lojavirtual.dto.categoria.CategoriaResponse;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.CategoriaMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.repository.CategoriaDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService extends BaseService<CategoriaDAO> {
    private final CategoriaDAO categoriaDAO;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaDAO categoriaDAO, CategoriaMapper categoriaMapper) {
        super(categoriaDAO);
        this.categoriaDAO = categoriaDAO;
        this.categoriaMapper = categoriaMapper;
    }

    public CategoriaResponse buscarPorId(Long id) {
        return categoriaMapper.toResponse(validaBuscarPorId(id));
    }

    public List<CategoriaResponse> listar(Boolean ativo, Integer numeroPagina, Integer tamanhoPagina) {
        List<Categoria> categoria = categoriaDAO.listar(ativo, numeroPagina, tamanhoPagina);

        return categoria.stream().map(categoriaMapper::toResponse).toList();
    }

    public CategoriaResponse incluir(CategoriaRequest request) {
        String nome = request.getNome();
        Long categoriaPaiId = request.getIdCategoriaPai();
        Categoria categoria = categoriaMapper.toEntity(request);

        if (request.getIdCategoriaPai() != null) {
            validaCategoriaExiste(categoriaPaiId);
        }
        validaEntidadePossuiMesmoNome(nome);

        return categoriaMapper.toResponse(categoriaDAO.incluir(categoria));
    }

    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        String nome = request.getNome();
        Long categoriaPaiId = request.getIdCategoriaPai();

        validaBuscarPorId(id);
        validaCategoriaExiste(categoriaPaiId);
        validaEntidadePossuiMesmoNome(nome);

        Categoria categoria = categoriaMapper.toEntity(request);
        return categoriaMapper.toResponse(categoriaDAO.atualizar(id, categoria));
    }

    @Transactional
    public void excluir(Long id) {
        validaBuscarPorId(id);
        categoriaDAO.excluir(id);
    }

    public Categoria validaBuscarPorId(Long id) {
        try {
            return categoriaDAO.buscarPorId(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Categoria.class.getSimpleName(), id);
        }
    }

    public Boolean existeFilhosNaCategoria(Long categoriaPaiId) {
        return categoriaDAO.existeFilhosNaCategoria(categoriaPaiId);
    }
}
