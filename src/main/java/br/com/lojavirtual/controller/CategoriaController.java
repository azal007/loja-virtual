package br.com.lojavirtual.controller;

import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

//    @GetMapping
//    public List<Categoria> listar() {
//        return categoriaService.listar();
//    }

//    @PostMapping
//    public void incluir(Categoria categoria) {
//        categoriaService.incluir(categoria);
//    }

    @GetMapping(value = "/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @GetMapping
    public List<Categoria> lista(){
        return categoriaService.listar();
    }

    @PostMapping
    public int incluir(@RequestBody Categoria categoria) {
        return categoriaService.incluir(categoria);
    }

    @PutMapping(value = "/{id}")
    public int atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaService.atualizar(id, categoria);
    }

    @DeleteMapping(value = "/{id}")
    public int excluir(@PathVariable Long id) {
        return categoriaService.excluir(id);
    }
}
