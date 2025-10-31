package br.com.lojavirtual.service;

import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.repository.BaseDAO;
import br.com.lojavirtual.repository.CategoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T extends BaseDAO> {
    @Autowired
    private CategoriaDAO categoriaDAO;
    private final T entidadeDAO;

    public BaseService(T entityDAO) {
        this.entidadeDAO = entityDAO;
    }

    public void validaEntidadePossuiMesmoNome(String nome) {
        // verificando se o nome da categoria informada já existe
        Boolean possuiMesmoNome = entidadeDAO.verificaPossuiMesmoNome(nome);
        if (possuiMesmoNome) {
            throw new BusinessException("O nome informado já existe.");
        }
    }

    public void validaCategoriaExiste(Long categoriaId) {
        Boolean existe = categoriaDAO.existeCategoria(categoriaId);
        if (!existe) {
            throw new BusinessException("A categoria informada não existe.");
        }
    }
}
