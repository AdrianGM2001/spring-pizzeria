package es.adr.service;

import es.adr.model.dto.CarritoRequest;
import es.adr.model.dto.FinalizarPedidoRequest;
import es.adr.model.pedido.Pedido;

import java.util.List;

public interface PedidoService {
    List<Pedido> findAllPedido();
    Pedido findPedidoById(int id);
    // TODO - Como hay un @JsonIgnore en atributo cliente, no se puede guardar un pedido porque no se asigna el cliente
    //Pedido savePedido(Pedido pedido);
    Pedido updatePedido(Pedido pedido);
    void deletePedido(int id);
    Pedido addLineaPedido(CarritoRequest carritoRequest);
    Pedido finalizarPedido(FinalizarPedidoRequest finalizarPedidoRequest);
    Pedido cancelarPedido(int clienteId);
    Pedido entregarPedido(int pedidoId);
}
