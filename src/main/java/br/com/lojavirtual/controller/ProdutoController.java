package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.ProdutoDTO;
import br.com.lojavirtual.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Adicionar teste unitários para facilitar o desenvolvimento
// TODO: Adicionar tratamento de exceções
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar(
            @RequestParam(value = "nome", required=false) String nome,
            @RequestParam(value = "id", required = false) Long categoriaId,
            @RequestParam(value = "min", required = false) Double precoMin,
            @RequestParam(value = "max", required = false) Double precoMax
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listar(nome, categoriaId, precoMin, precoMax));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> incluir(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.incluir(produtoDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.atualizar(id, produtoDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
