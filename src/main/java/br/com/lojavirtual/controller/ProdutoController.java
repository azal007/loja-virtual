package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.ProdutoRequest;
import br.com.lojavirtual.dto.ProdutoResponse;
import br.com.lojavirtual.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Adicionar teste unitários para facilitar o desenvolvimento
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar(
            @RequestParam(value = "nome", required=false) String nome,
            @RequestParam(value = "id", required = false) Long categoriaId,
            @RequestParam(value = "min", required = false) Double precoMin,
            @RequestParam(value = "max", required = false) Double precoMax,
            @RequestParam(value = "ativo", required = false, defaultValue = "true") Boolean ativo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listar(nome, categoriaId, precoMin, precoMax, ativo));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> incluir(@RequestBody ProdutoRequest request) {
        request.validate();
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.incluir(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @RequestBody ProdutoRequest request) {
        request.validate();
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.atualizar(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
