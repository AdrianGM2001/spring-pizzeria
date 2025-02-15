package es.adr.service;

import es.adr.model.producto.Bebida;
import es.adr.model.producto.Pasta;
import es.adr.model.producto.Pizza;
import es.adr.model.producto.Producto;
import es.adr.model.ingrediente.Ingrediente;
import es.adr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Override
    public List<Producto> findAllProducto() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findProductoById(int id) {
        return productoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con Id: " + id));
    }

    @Override
    public Producto saveProducto(Producto producto) {
        boolean exite = switch (producto) {
            case Pizza pizza -> pizzaRepository.existsByNombreAndSize(pizza.getNombre(), pizza.getSize());
            case Bebida bebida -> bebidaRepository.existsByNombreAndSize(bebida.getNombre(), bebida.getSize());
            default -> productoRepository.existsByNombre(producto.getNombre());
        };

        if (exite) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Producto ya existe");
        }

        List<Ingrediente> ingredientes = switch (producto) {
            case Pizza pizza -> pizza.getIngredientes();
            case Pasta pasta -> pasta.getIngredientes();
            default -> new ArrayList<>();
        };

        ingredientes = ingredientes.stream()
                .map(ingrediente -> ingredienteRepository.findByNombre(ingrediente.getNombre()).orElse(ingrediente))
                .toList();

        switch (producto) {
            case Pizza pizza -> pizza.setIngredientes(ingredientes);
            case Pasta pasta -> pasta.setIngredientes(ingredientes);
            default -> { /* 👾 */}
        }

        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Producto producto) {
        if (!productoRepository.existsById(producto.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con Id: " + producto.getId());
        }

        return productoRepository.save(producto);
    }

    @Override
    public void deleteProducto(int id) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con Id: " + id);
        }

        productoRepository.deleteById(id);
    }
}
