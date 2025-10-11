package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.CategoriaRequest;
import br.com.lojavirtual.dto.CategoriaResponse;
import br.com.lojavirtual.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listar());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> incluir(@RequestBody CategoriaRequest request) {
        request.validate();
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.incluir(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(@PathVariable Long id, @RequestBody CategoriaRequest request) {
        request.validate();
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.atualizar(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
