package es.adr.controller;

import es.adr.model.producto.Producto;
import es.adr.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> findAllProducto() {
        return new ResponseEntity<>(productoService.findAllProducto(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Producto> findProductoById(@PathVariable("id") int id) {
        return new ResponseEntity<>(productoService.findProductoById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producto> saveProducto(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.saveProducto(producto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.updateProducto(producto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable("id") int id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
