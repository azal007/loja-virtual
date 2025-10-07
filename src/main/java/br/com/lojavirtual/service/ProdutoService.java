package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.ProdutoDTO;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.mapper.ProdutoMapper;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.model.Produto;
import br.com.lojavirtual.repository.CategoriaDAO;
import br.com.lojavirtual.repository.ProdutoDAO;
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

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoDAO.buscarPorId(id);
        // Se ao buscar um produto o ID não for informado ou não existir no banco, lança uma exceção
        if (Objects.isNull(id) || Objects.isNull(produto)) {
            throw new BusinessException("Produto não encontrado");
        }
        return produtoMapper.toDTO(produto);
    }

    public List<ProdutoDTO> listar(String nome, Long categoriaId, Double precoMin, Double precoMax) {
        List<Produto> produto = produtoDAO.listar(nome, categoriaId, precoMin, precoMax);

        return produto.stream().map(produtoMapper::toDTO).toList();
    }

    public ProdutoDTO incluir(ProdutoDTO produtoDTO) {
        if (Objects.isNull(produtoDTO)) {
            throw new BusinessException("Preencha os campos obrigatórios");
        }

        // buscando o id da categoria pai por intermédio do "categoriaId" presente na requisição
        Categoria categoriaPai = categoriaDAO.buscarPorId(produtoDTO.getCategoriaId());
        // verificando se existe filhos na categoria pai passando o id da mesma
        Boolean possuiFilhos = categoriaDAO.existeFilhosNaCategoriaPai(categoriaPai.getId());
        if (possuiFilhos){
            throw new BusinessException("A categoria informada é inválida pois possui subcategorias.");
        }
        // buscar os produtos do banco de dados com o mesmo nome que o da requisicao
        Boolean mesmoNome = produtoDAO.possuiMesmoNome(produtoDTO.getNome());
        if (mesmoNome) {
            throw new BusinessException("Não é possível cadastrar produtos com o mesmo nome.");
        }
        Produto produto = produtoMapper.toEntity(produtoDTO);
        return produtoMapper.toDTO(produtoDAO.incluir(produto));
    }

    public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTO) {
        // Se ao incluir um produto os dados obrigatórios não forem preenchidos, lança uma exceção
        if (Objects.isNull(produtoDTO)) {
            throw new BusinessException("Preencha os campos obrigatórios");
        }

        // Se ao atualizar um produto for informada uma categoria inexistente, lança uma exceção
        Produto obterProduto = produtoDAO.buscarPorId(id);
        if (Objects.isNull(obterProduto)) {
            throw new BusinessException("Produto não encontrado");
        }

        // Se no momento que eu for atualizar uma categoria, eu informar uma categoria pai
        if (produtoDTO.getCategoriaId() != null) {
            // Se no momento que eu for atualizar uma categoria, eu informar uma categoria pai que não existe, lanço uma exception
            Boolean existe = categoriaDAO.existeCategoriaPai(produtoDTO.getCategoriaId());
            if (!existe) {
                throw new BusinessException("Categoria informada não existe");
            }
        }

        Produto produto = produtoMapper.toEntity(produtoDTO);
        return produtoMapper.toDTO(produtoDAO.atualizar(id, produto));
    }

    public void excluir(Long id) {
        if (Objects.isNull(id)) {
            throw new BusinessException("Produto não encontrado");
        }

        produtoDAO.excluir(id);
    }
}