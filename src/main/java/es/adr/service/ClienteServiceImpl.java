package es.adr.service;

import es.adr.model.cliente.Cliente;
import es.adr.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> findAllCliente() {
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> findAllClienteByNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Cliente findClienteById(int id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el cliente con id " + id));
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        if (clienteRepository.existsByDniOrEmail(cliente.getDni(), cliente.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con esos datos");
        }

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente updateCliente(Cliente cliente) {
        if (!clienteRepository.existsById(cliente.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el cliente con id " + cliente.getId());
        }

        Optional<Cliente> clienteDni = clienteRepository.findByDni(cliente.getDni());
        Optional<Cliente> clienteEmail = clienteRepository.findByEmail(cliente.getEmail());

        if (clienteDni.isPresent() && clienteDni.get().getId() != cliente.getId() || clienteEmail.isPresent() && clienteEmail.get().getId() != cliente.getId()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con esos datos");
        }

        if (cliente.getPedidos() == null) {
            cliente.setPedidos(new ArrayList<>());
        }

        cliente.getPedidos().forEach(pedido -> pedido.setCliente(cliente));

        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(int id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el cliente con id " + id);
        }

        clienteRepository.deleteById(id);
    }
}
