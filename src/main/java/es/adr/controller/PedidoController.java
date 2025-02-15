package es.adr.controller;

import es.adr.model.dto.CarritoRequest;
import es.adr.model.dto.FinalizarPedidoRequest;
import es.adr.model.pedido.Pedido;
import es.adr.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAllPedido() {
        return new ResponseEntity<>(pedidoService.findAllPedido(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Pedido> findPedidoById(@PathVariable("id") int id) {
        return new ResponseEntity<>(pedidoService.findPedidoById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoService.updatePedido(pedido), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePedido(@PathVariable("id") int id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/add")
    public ResponseEntity<Pedido> addLineaPedido(@RequestBody CarritoRequest carritoRequest) {
        Pedido pedido = pedidoService.addLineaPedido(carritoRequest);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @PutMapping("/finalizar")
    public ResponseEntity<Pedido> finalizarPedido(@RequestBody FinalizarPedidoRequest finalizarPedidoRequest) {
        Pedido pedido = pedidoService.finalizarPedido(finalizarPedidoRequest);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable int id) {
        Pedido pedido = pedidoService.cancelarPedido(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @PutMapping("/entregar/{pedidoId}")
    public ResponseEntity<Pedido> entregarPedido(@PathVariable int pedidoId) {
        Pedido pedido = pedidoService.entregarPedido(pedidoId);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }
}
