package es.adr.repository;

import es.adr.model.producto.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsByNombre(String nombre);
}
