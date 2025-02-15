package es.adr.repository;

import es.adr.model.ingrediente.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    Optional<Ingrediente> findByNombre(String nombre);
}
