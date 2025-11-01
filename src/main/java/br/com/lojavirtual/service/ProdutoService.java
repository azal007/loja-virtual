package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.produto.ProdutoRequest;
import br.com.lojavirtual.dto.produto.ProdutoResponse;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.ProdutoMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.repository.CategoriaDAO;
import br.com.lojavirtual.repository.ProdutoDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProdutoService extends BaseService<ProdutoDAO> {
    private final ProdutoDAO produtoDAO;
    private final ProdutoMapper produtoMapper;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoDAO produtoDAO, ProdutoMapper produtoMapper, CategoriaService categoriaService) {
        super(produtoDAO);
        this.produtoDAO = produtoDAO;
        this.produtoMapper = produtoMapper;
        this.categoriaService = categoriaService;
    }

    public ProdutoResponse buscarPorId(Long id) {
        return produtoMapper.toResponse(validaBuscarPorId(id));
    }

    public List<ProdutoResponse> listar(String nome, Long categoriaId, Double precoMin, Double precoMax, Boolean ativo, Integer numeroPagina, Integer tamanhoPagina) {
        List<Produto> produto = produtoDAO.listar(nome, categoriaId, precoMin, precoMax, ativo, numeroPagina, tamanhoPagina);

        return produto.stream().map(produtoMapper::toResponse).toList();
    }

    public ProdutoResponse incluir(ProdutoRequest request) {
        Long id = request.getId();
        String nome = request.getNome();
        Long categoriaId = request.getCategoriaId();
        Produto produto = produtoMapper.toEntity(request);

        validaCategoriaExiste(categoriaId);
        validaPossuiFilhos(categoriaId);
        // TODO: REVISAR
        validaEntidadePossuiMesmoNome(nome, id);

        return produtoMapper.toResponse(produtoDAO.incluir(produto));
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        String nome = request.getNome();
        Long categoriaId = request.getCategoriaId();

        validaBuscarPorId(id);
        validaCategoriaExiste(categoriaId);
        // TODO: REVISAR
        validaEntidadePossuiMesmoNome(nome, id);

        Produto produto = produtoMapper.toEntity(request);
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