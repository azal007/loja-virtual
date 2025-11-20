package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.produto.ProdutoPatchRequest;
import br.com.lojavirtual.dto.produto.ProdutoRequest;
import br.com.lojavirtual.dto.produto.ProdutoResponse;
import br.com.lojavirtual.dto.produto.ProdutoUpdateRequest;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.PageMapper;
import br.com.lojavirtual.mapper.ProdutoMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.model.Page;
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.repository.ProdutoDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService extends BaseService<ProdutoDAO> {
    private final ProdutoDAO produtoDAO;
    private final ProdutoMapper produtoMapper;
    private final PageMapper<ProdutoResponse> produtoResponsePageMapper;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoDAO produtoDAO, ProdutoMapper produtoMapper, PageMapper<ProdutoResponse> produtoResponsePageMapper, CategoriaService categoriaService) {
        super(produtoDAO);
        this.produtoDAO = produtoDAO;
        this.produtoMapper = produtoMapper;
        this.produtoResponsePageMapper = produtoResponsePageMapper;
        this.categoriaService = categoriaService;
    }

    public ProdutoResponse buscarPorId(Long id) {
        return produtoMapper.toResponse(validaBuscarPorId(id));
    }

    public PageResponse<ProdutoResponse> listar(String nome, Long categoriaId, Double precoMin, Double precoMax, Boolean ativo, Integer numeroPagina, Integer tamanhoPagina) {
        List<Produto> produto = produtoDAO.listar(nome, categoriaId, precoMin, precoMax, ativo, numeroPagina, tamanhoPagina);

        int totalElementos = obterTotalElementos();
        int totalPaginas = (int) Math.ceil((double) totalElementos / tamanhoPagina);

        Page page = new Page(numeroPagina, tamanhoPagina, totalElementos, totalPaginas);

        return produtoResponsePageMapper.toResponse(
                page,
                produto.stream().map(produtoMapper::toResponse).toList()
        );
    }

    public ProdutoResponse incluir(ProdutoRequest request) {
        Long id = request.getId();
        String nome = request.getNome();
        Long categoriaId = request.getCategoriaId();
        Produto produto = produtoMapper.toEntity(request);

        validaCategoriaExiste(categoriaId);
        validaPossuiFilhos(categoriaId);
        validaEntidadePossuiMesmoNome(nome, id);

        return produtoMapper.toResponse(produtoDAO.incluir(produto));
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoUpdateRequest request) {
        String nome = request.getNome();
        Long categoriaId = request.getCategoriaId();

        validaBuscarPorId(id);
        validaCategoriaExiste(categoriaId);
        validaEntidadePossuiMesmoNome(nome, id);

        Produto produto = produtoMapper.toEntityUpdate(request);
        return produtoMapper.toResponse(produtoDAO.atualizar(id, produto));
    }

    @Transactional
    public ProdutoResponse atualizarParcial(Long id, ProdutoPatchRequest request) {
        String nome = request.getNome();
        Long categoriaId = request.getCategoriaId();

        validaBuscarPorId(id);
        validaCategoriaExiste(categoriaId);
        validaEntidadePossuiMesmoNome(nome, id);

        Produto produto = produtoMapper.toEntityPatch(request);
        return produtoMapper.toResponse(produtoDAO.atualizar(id, produto));
    }

    @Transactional
    public void excluir(Long id) {
        validaBuscarPorId(id);
        produtoDAO.excluir(id);
    }

    private Produto validaBuscarPorId(Long id) {
        try {
            return produtoDAO.buscarPorId(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Produto.class.getSimpleName(), id);
        }
    }

    private void validaPossuiFilhos(Long categoriaId) {
        // obtendo a entidade Categoria por intermédio do "categoriaId" presente na requisição
        Categoria categoriaPai = categoriaService.validaBuscarPorId(categoriaId);
        // verificando se existe filhos na categoria obtida passando o id da mesma
        Boolean possuiFilhos = categoriaService.existeFilhosNaCategoria(categoriaPai.getId());
        if (possuiFilhos) {
            throw new BusinessException("A categoria informada é inválida pois possui subcategorias.");
        }
    }
}