package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.categoria.CategoriaPatchRequest;
import br.com.lojavirtual.dto.categoria.CategoriaRequest;
import br.com.lojavirtual.dto.categoria.CategoriaResponse;
import br.com.lojavirtual.dto.categoria.CategoriaUpdateRequest;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.CategoriaMapper;
import br.com.lojavirtual.mapper.PageMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.model.Page;
import br.com.lojavirtual.repository.CategoriaDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoriaService extends BaseService<CategoriaDAO> {
    private final CategoriaDAO categoriaDAO;
    private final CategoriaMapper categoriaMapper;
    private final PageMapper<CategoriaResponse> categoriaResponsePageMapper;

    public CategoriaService(CategoriaDAO categoriaDAO, CategoriaMapper categoriaMapper, PageMapper<CategoriaResponse> categoriaResponsePageMapper) {
        super(categoriaDAO);
        this.categoriaDAO = categoriaDAO;
        this.categoriaMapper = categoriaMapper;
        this.categoriaResponsePageMapper = categoriaResponsePageMapper;
    }

    public CategoriaResponse buscarPorId(Long id) {
        return categoriaMapper.toResponse(validaBuscarPorId(id));
    }

    public PageResponse<CategoriaResponse> listar(Boolean ativo, Integer numeroPagina, Integer tamanhoPagina) {
        List<Categoria> categoria = categoriaDAO.listar(ativo, numeroPagina, tamanhoPagina);

        String sqlFromWhere = categoriaDAO.obterParametros(ativo);
        List<Object> paramentros = categoriaDAO.getPageParametros();

        int totalElementos = obterTotalElementos(sqlFromWhere, paramentros);
        int totalPaginas = (int) Math.ceil((double) totalElementos / tamanhoPagina);

        Page page = new Page(numeroPagina, tamanhoPagina, totalElementos, totalPaginas);

        return categoriaResponsePageMapper.toResponse(
                page,
                categoria.stream().map(categoriaMapper::toResponse).toList()
        );
    }

    public CategoriaResponse incluir(CategoriaRequest request) {
        String nome = request.getNome();
        Long id = request.getId();
        Long categoriaPaiId = request.getIdCategoriaPai();
        Categoria categoria = categoriaMapper.toEntity(request);

        if (request.getIdCategoriaPai() != null) {
            validaCategoriaExiste(categoriaPaiId);
        }
        validaEntidadePossuiMesmoNome(nome, id);

        return categoriaMapper.toResponse(categoriaDAO.incluir(categoria));
    }

    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaUpdateRequest request) {
        String nome = request.getNome();
        Long categoriaPaiId = request.getIdCategoriaPai();

        if (Objects.nonNull(validaBuscarPorId(id).getIdCategoriaPai())) {
            validaCategoriaExiste(categoriaPaiId);
        }
        validaEntidadePossuiMesmoNome(nome, id);

        Categoria categoria = categoriaMapper.toEntityUpdate(request);
        return categoriaMapper.toResponse(categoriaDAO.atualizar(id, categoria));
    }

    @Transactional
    public CategoriaResponse atualizarParcial(Long id, CategoriaPatchRequest request) {
        String nome = request.getNome();
        Long categoriaPaiId = request.getIdCategoriaPai();

        if (Objects.nonNull(validaBuscarPorId(id).getIdCategoriaPai())) {
            validaCategoriaExiste(categoriaPaiId);
        }
        validaEntidadePossuiMesmoNome(nome, id);

        Categoria categoria = categoriaMapper.toEntityPatch(request);
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
