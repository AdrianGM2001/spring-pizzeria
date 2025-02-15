package es.adr.repository;

import es.adr.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByDniOrEmail(String dni, String email);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    Optional<Cliente> findByDni(String dni);
    Optional<Cliente> findByEmail(String email);
}
