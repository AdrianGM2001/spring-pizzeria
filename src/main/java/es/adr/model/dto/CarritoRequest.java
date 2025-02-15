package es.adr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoRequest {
    private int productoId;
    private int cantidad;
    private int clienteId;
}
