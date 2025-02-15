package es.adr.controller;

import es.adr.model.cliente.Cliente;
import es.adr.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> findAllCliente(@RequestParam(value = "nombre", required = false) String nombre) {
        if (nombre != null) {
            return new ResponseEntity<>(clienteService.findAllClienteByNombre(nombre), HttpStatus.OK);
        }

        return new ResponseEntity<>(clienteService.findAllCliente(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Cliente> findClienteById(@PathVariable("id") int id) {
        return new ResponseEntity<>(clienteService.findClienteById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
        return new ResponseEntity<>(clienteService.saveCliente(cliente), HttpStatus.CREATED);
    }

    @PutMapping
    public Cliente updateCliente(@RequestBody Cliente cliente) {
        return clienteService.updateCliente(cliente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable("id") int id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
