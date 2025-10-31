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

    public void verificaEntidadePossuiMesmoNome(String nome, String nomeEntidade) {
        // verificando se o nome da categoria informada já existe
        Boolean possuiMesmoNome = entidadeDAO.possuiMesmoNome(nome);
        if (possuiMesmoNome) {
            throw new BusinessException(String.format("Não é possível cadastrar %s com o mesmo nome.", nomeEntidade));
        }
    }

    public void verificaCategoriaExiste(Long categoriaId) {
        // Verifica se o campo "categoriId" está preenchido na requisição
        if (categoriaId != null) {
            // verificando se a categoria informada existe
            Boolean existe = categoriaDAO.existeCategoria(categoriaId);
            if (!existe) {
                throw new BusinessException("A categoria informada não existe.");
            }
        }
    }
}
