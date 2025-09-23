package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.CategoriaDTO;
import br.com.lojavirtual.model.Categoria;
import br.com.lojavirtual.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = "/{id}")
    public CategoriaDTO buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> lista(){
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listar());
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> incluir(@RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.incluir(categoria));

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoriaService.atualizar(id, categoria));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> excluir(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoriaService.excluir(id));
    }
}
