package br.com.lojavirtual.repository;

import br.com.lojavirtual.model.Categoria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaDAO {
    
    public List<Categoria> listar() {
        return List.of(new Categoria(1L, "Hardware"), new Categoria(2L, "Periféricos"));
    }

    public void incluir(Categoria categoria) {
        System.out.println("Incluindo nova categoria");
    }
}
