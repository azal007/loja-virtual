package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.categoria.CategoriaPatchRequest;
import br.com.lojavirtual.dto.categoria.CategoriaRequest;
import br.com.lojavirtual.dto.categoria.CategoriaResponse;
import br.com.lojavirtual.dto.categoria.CategoriaUpdateRequest;
import br.com.lojavirtual.service.CategoriaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<CategoriaResponse> buscarPorId(@NotNull @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar(
            @RequestParam(value = "ativo", required = false, defaultValue = "true") Boolean ativo,
            @RequestParam(value = "numeroPagina", required = false, defaultValue = "1") Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina", required = false, defaultValue = "8") Integer tamanhoPagina
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listar(ativo, numeroPagina, tamanhoPagina));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> incluir(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.incluir(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.atualizar(id, request));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<CategoriaResponse> atualizarParcial(@PathVariable Long id, @Valid @RequestBody CategoriaPatchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.atualizarParcial(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
