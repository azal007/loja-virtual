package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.pedido.ItemPedidoRequest;
import br.com.lojavirtual.dto.pedido.PedidoResponse;
import br.com.lojavirtual.security.service.JwtService;
import br.com.lojavirtual.service.PedidoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final JwtService jwtService;

    public PedidoController(PedidoService pedidoService, JwtService jwtService) {
        this.pedidoService = pedidoService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> incluir(@Valid @RequestBody List<ItemPedidoRequest> request,
                                                   HttpServletRequest httpRequest) {
        Long userId = extrairUserId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.incluirPedido(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPorUsuario(HttpServletRequest httpRequest) {
        Long userId = extrairUserId(httpRequest);
        return ResponseEntity.ok(pedidoService.listarPedidosPorUsuario(userId));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelar(@PathVariable Long id,
                                                    HttpServletRequest httpRequest) {
        Long userId = extrairUserId(httpRequest);
        return ResponseEntity.ok(pedidoService.cancelarPedido(id, userId));
    }

    private Long extrairUserId(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            return jwtService.getUserIdFromToken(token);
        }
        throw new RuntimeException("Token não fornecido");
    }
}
