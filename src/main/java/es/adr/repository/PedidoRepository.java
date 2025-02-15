package es.adr.repository;

import es.adr.model.pedido.EstadoPedido;
import es.adr.model.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByClienteIdAndEstado(int clienteId, EstadoPedido estado);
    boolean existsByClienteIdAndEstado(int clienteId, EstadoPedido estado);
    boolean existsByIdAndEstado(int pedidoId, EstadoPedido estado);
}
