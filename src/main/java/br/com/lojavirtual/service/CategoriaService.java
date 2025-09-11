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

    public List<Categoria> listar() {
        return categoriaDAO.listar();
    }

    public void incluir(Categoria categoria) {
        categoriaDAO.incluir(categoria);
    }
}
