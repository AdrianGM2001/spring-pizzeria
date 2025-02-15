package es.adr.repository;

import es.adr.model.lineapedido.LineaPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LineaPedidoRepository extends JpaRepository<LineaPedido, Integer> {
    boolean existsByPedidoIdAndProductoId(int pedidoId, int productoId);
    Optional<LineaPedido> findByPedidoIdAndProductoId(int pedidoId, int productoId);
}
