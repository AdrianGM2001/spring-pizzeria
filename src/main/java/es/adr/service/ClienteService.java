package es.adr.service;

import es.adr.model.cliente.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAllCliente();
    List<Cliente> findAllClienteByNombre(String nombre);
    Cliente findClienteById(int id);
    Cliente saveCliente(Cliente cliente);
    Cliente updateCliente(Cliente cliente);
    void deleteCliente(int id);
}
