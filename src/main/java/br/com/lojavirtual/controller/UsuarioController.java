package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.UsuarioRequest;
import br.com.lojavirtual.dto.UsuarioResponse;
import br.com.lojavirtual.service.UsuarioService;
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
            @RequestParam(value = "ativo", required = false) Boolean ativo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listar(nome, cpf, email, ativo));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> incluir(@RequestBody UsuarioRequest request) {
        request.validate();
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.incluir(request));
    }
}
