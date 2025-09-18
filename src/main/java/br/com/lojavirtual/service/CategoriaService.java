package br.com.lojavirtual.service;

import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.repository.CategoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaDAO categoriaDAO;

//    public List<Categoria> listar() {
//        return categoriaDAO.listar();
//    }

//    public void incluir(Categoria categoria) {
//        categoriaDAO.incluir(categoria);
//    }
    public Categoria buscarPorId(Long id) {
        return categoriaDAO.buscarPorId(id);
    }

    public List<Categoria> listar(){
        return categoriaDAO.listar();
    }

    // Para criar uma nova categoria é necessário que haja no mínimo uma folha existente para criar uma nova
//    public int incluir(Categoria categoria) {
//        return categoriaDAO.incluir(categoria);
//    }
    public int incluir(Categoria categoria) {
        if(categoriaDAO.obterFolhas(categoria)) {
            throw new RuntimeException("Não existe categoria folha para incluir uma nova categoria");
        }

        return categoriaDAO.incluir(categoria);
    }

    public int atualizar(Long id, Categoria categoria) {
        return categoriaDAO.atualizar(id, categoria);
    }

    public int excluir(Long id) {
        return categoriaDAO.excluir(id);
    }
}
