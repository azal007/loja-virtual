package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.CategoriaDTO;
import br.com.lojavirtual.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Modificar o parâmetro para receber CategoriaDTO
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "/{id}")
    public CategoriaDTO buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> lista(){
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listar());
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> incluir(@RequestBody CategoriaDTO categoriaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.incluir(categoriaDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDto) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.atualizar(id, categoriaDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
