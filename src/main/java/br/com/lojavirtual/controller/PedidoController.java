package br.com.lojavirtual.controller;


import br.com.lojavirtual.dto.pedido.ItemPedidoRequest;
import br.com.lojavirtual.dto.pedido.PedidoResponse;
import br.com.lojavirtual.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> incluir(@Valid @RequestBody List<ItemPedidoRequest> request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.incluirPedido(request));
    }
}
