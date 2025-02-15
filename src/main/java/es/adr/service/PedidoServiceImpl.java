package es.adr.service;

import es.adr.model.cliente.Cliente;
import es.adr.model.dto.CarritoRequest;
import es.adr.model.dto.FinalizarPedidoRequest;
import es.adr.model.pedido.EstadoPedido;
import es.adr.model.pedido.Pedido;
import es.adr.model.lineapedido.LineaPedido;
import es.adr.model.producto.Producto;
import es.adr.model.pago.Pagable;
import es.adr.model.pago.PagarEfectivo;
import es.adr.model.pago.PagarTarjeta;
import es.adr.repository.ClienteRepository;
import es.adr.repository.LineaPedidoRepository;
import es.adr.repository.PedidoRepository;
import es.adr.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public List<Pedido> findAllPedido() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido findPedidoById(int id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el pedido con id " + id));
    }

    @Override
    public Pedido updatePedido(Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(pedido.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el pedido con id " + pedido.getId()));
        pedidoExistente.setFecha(pedido.getFecha());
        pedidoExistente.setEstado(pedido.getEstado());
        pedidoExistente.setMetodoPago(pedido.getMetodoPago());

        return pedidoRepository.save(pedidoExistente);
    }

    @Override
    public void deletePedido(int id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el pedido con id " + id);
        }

        pedidoRepository.deleteById(id);
    }

    @Override
    public Pedido addLineaPedido(CarritoRequest carritoRequest) {
        if (!clienteRepository.existsById(carritoRequest.getClienteId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el cliente con id " + carritoRequest.getClienteId());
        }

        if (!productoRepository.existsById(carritoRequest.getProductoId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el producto con id " + carritoRequest.getProductoId());
        }

        if (carritoRequest.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor que 0");
        }

        Cliente cliente = clienteRepository.findById(carritoRequest.getClienteId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        Producto producto = productoRepository.findById(carritoRequest.getProductoId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        Pedido pedido;
        List<Pedido> pedidosPendientes = pedidoRepository.findByClienteIdAndEstado(carritoRequest.getClienteId(), EstadoPedido.PENDIENTE);

        if (pedidosPendientes.isEmpty()) {
            pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setEstado(EstadoPedido.PENDIENTE);
            pedido.setFecha(Date.valueOf(LocalDate.now()));
            pedido.setLineas(new ArrayList<>());
            pedidoRepository.save(pedido);
            pedido = pedidoRepository.findByClienteIdAndEstado(carritoRequest.getClienteId(), EstadoPedido.PENDIENTE).getFirst();
        } else if (pedidosPendientes.size() == 1) {
            pedido = pedidosPendientes.getFirst();
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen múltiples pedidos pendientes para el cliente");
        }

        LineaPedido lineaPedido = lineaPedidoRepository.findByPedidoIdAndProductoId(pedido.getId(), producto.getId()).orElse(new LineaPedido());

        if (lineaPedido.getId() != 0) {
            lineaPedido.addCantidad(carritoRequest.getCantidad());
        } else {
            lineaPedido.setCantidad(carritoRequest.getCantidad());
            lineaPedido.setProducto(producto);
            lineaPedido.setPedidoId(pedido.getId());
            pedido.addLinea(lineaPedido);
        }

        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido finalizarPedido(FinalizarPedidoRequest finalizarPedidoRequest) {
        if (!pedidoRepository.existsByClienteIdAndEstado(finalizarPedidoRequest.getClienteId(), EstadoPedido.PENDIENTE)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el pedido pendiente del cliente con id " + finalizarPedidoRequest.getClienteId());
        }

        Pedido pedido = pedidoRepository.findByClienteIdAndEstado(finalizarPedidoRequest.getClienteId(), EstadoPedido.PENDIENTE).getFirst();
        Pagable metodoPago = finalizarPedidoRequest.getMetodoPago().equals("tarjeta") ? new PagarTarjeta() : new PagarEfectivo();
        pedido.pagar(metodoPago);

        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido cancelarPedido(int clienteId) {
        if (!pedidoRepository.existsByClienteIdAndEstado(clienteId, EstadoPedido.PENDIENTE)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el pedido pendiente del cliente con id " + clienteId);
        }

        Pedido pedido = pedidoRepository.findByClienteIdAndEstado(clienteId, EstadoPedido.PENDIENTE).getFirst();
        pedido.setEstado(EstadoPedido.CANCELADO);

        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido entregarPedido(int pedidoId) {
        if (!pedidoRepository.existsByIdAndEstado(pedidoId, EstadoPedido.FINALIZADO)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado un pedido finalizado con id " + pedidoId);
        }

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el pedido con id " + pedidoId));
        pedido.setEstado(EstadoPedido.ENTREGADO);

        return pedidoRepository.save(pedido);
    }
}
