package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.UsuarioRequest;
import br.com.lojavirtual.dto.UsuarioResponse;
import br.com.lojavirtual.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "usuarios/")
public class UsuarioController {
    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // TODO: Testar o endpoint
    @PostMapping
    public ResponseEntity<UsuarioResponse> incluir(@RequestBody UsuarioRequest request) {
        request.validate();
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.incluir(request));
    }
}
