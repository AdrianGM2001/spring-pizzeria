package es.adr.repository;

import es.adr.model.producto.Pizza;
import es.adr.model.producto.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    boolean existsByNombreAndSize(String nombre, Size size);
}
