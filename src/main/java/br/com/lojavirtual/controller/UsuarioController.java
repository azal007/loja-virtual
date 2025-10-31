package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.usuario.UsuarioRequest;
import br.com.lojavirtual.dto.usuario.UsuarioResponse;
import br.com.lojavirtual.dto.usuario.UsuarioUpdateRequest;
import br.com.lojavirtual.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "ativo", required = false, defaultValue = "true") Boolean ativo,
            @RequestParam(value = "numeroPagina",  required = false, defaultValue = "1") Integer numeroPagina,
            @RequestParam(value = "tamanhoPagina",  required = false, defaultValue = "8") Integer tamanhoPagina
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listar(nome, cpf, email, ativo, numeroPagina, tamanhoPagina));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> incluir(@Valid  @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.incluir(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizar(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
