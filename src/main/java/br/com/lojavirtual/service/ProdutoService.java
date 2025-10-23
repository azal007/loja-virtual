package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.ProdutoRequest;
import br.com.lojavirtual.dto.ProdutoResponse;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.ProdutoMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.repository.CategoriaDAO;
import br.com.lojavirtual.repository.ProdutoDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProdutoService {
    private final CategoriaDAO categoriaDAO;
    private final ProdutoDAO produtoDAO;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoDAO produtoDAO, ProdutoMapper produtoMapper, CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
        this.produtoDAO = produtoDAO;
        this.produtoMapper = produtoMapper;
    }

    public ProdutoResponse buscarPorId(Long id) {
        try {
            Produto produto = produtoDAO.buscarPorId(id);
            return produtoMapper.toDTO(produto);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Produto.class.getSimpleName(), id);
        }
    }

    public List<ProdutoResponse> listar(String nome, Long categoriaId, Double precoMin, Double precoMax, Boolean ativo) {
        List<Produto> produto = produtoDAO.listar(nome, categoriaId, precoMin, precoMax, ativo);

        return produto.stream().map(produtoMapper::toDTO).toList();
    }

    public ProdutoResponse incluir(ProdutoRequest request) {
        // Verifica se o campo "categoriId" está preenchido na requisição
        if (request.getCategoriaId() != null) {
            // obtendo id da Categoria que veio na requisição
            Long categoriaId = request.getCategoriaId();
            // verificando se a categoria informada existe
            Boolean existe = categoriaDAO.existeCategoria(categoriaId);
            if (!existe) {
                throw new BusinessException("Categoria pai informada não existe");
            }
            // obtendo a entidade Categoria por intermédio do "categoriaId" presente na requisição
            Categoria categoriaPai = categoriaDAO.buscarPorId(categoriaId);
            // verificando se existe filhos na categoria obtida passando o id da mesma
            Boolean possuiFilhos = categoriaDAO.existeFilhosNaCategoria(categoriaPai.getId());
            if (possuiFilhos) {
                throw new BusinessException("A categoria informada é inválida pois possui subcategorias.");
            }
        }
        // verificando se o nome do produto informado já existe
        Boolean possuiMesmoNome = produtoDAO.possuiMesmoNome(request.getNome());
        if (possuiMesmoNome) {
            throw new BusinessException("Não é possível cadastrar produtos com o mesmo nome.");
        }
        Produto produto = produtoMapper.toEntity(request);
        return produtoMapper.toDTO(produtoDAO.incluir(produto));
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        try {
            // Se ao atualizar um produto for informada uma categoria inexistente, lança uma exceção
            produtoDAO.buscarPorId(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(Produto.class.getSimpleName(), id);
        }
        // Se no momento que eu for atualizar uma categoria, eu informar uma categoria pai
        if (request.getCategoriaId() != null) {
            // Se no momento que eu for atualizar uma categoria, eu informar uma categoria pai que não existe, lanço uma exception
            Boolean existe = categoriaDAO.existeCategoria(request.getCategoriaId());
            if (!existe) {
                throw new BusinessException("Categoria informada não existe");
            }
        }
        Produto produto = produtoMapper.toEntity(request);
        return produtoMapper.toDTO(produtoDAO.atualizar(id, produto));
    }

    public void excluir(Long id) {
        try {
            Produto obterProduto = produtoDAO.buscarPorId(id);
            if (!Objects.isNull(obterProduto)) {
                produtoDAO.excluir(id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Produto.class.getSimpleName(), id);
        }
    }
}