package es.adr.service;

import es.adr.model.producto.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAllProducto();
    Producto findProductoById(int id);
    Producto saveProducto(Producto producto);
    Producto updateProducto(Producto producto);
    void deleteProducto(int id);
}
