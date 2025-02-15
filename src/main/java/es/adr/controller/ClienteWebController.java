package es.adr.controller;

import es.adr.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteWebController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.findAllCliente());
        return "clientes";
    }

    @GetMapping("/{id}")
    public String detalleCliente(@PathVariable Integer id, Model model) {
        model.addAttribute("cliente", clienteService.findClienteById(id));
        return "cliente-detalle";
    }
}
