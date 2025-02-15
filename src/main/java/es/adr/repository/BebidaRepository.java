package es.adr.repository;

import es.adr.model.producto.Bebida;
import es.adr.model.producto.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BebidaRepository extends JpaRepository<Bebida, Integer> {
    boolean existsByNombreAndSize(String nombre, Size size);
}
