package es.adr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalizarPedidoRequest {
    private int clienteId;
    private String metodoPago;
}
