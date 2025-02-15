package es.adr.model.pedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.adr.model.cliente.Cliente;
import es.adr.model.lineapedido.LineaPedido;
import es.adr.model.pago.MetodoPago;
import es.adr.model.pago.Pagable;
import es.adr.model.pago.PagarEfectivo;
import es.adr.model.pago.PagarTarjeta;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private Cliente cliente;
    @OneToMany(mappedBy = "pedidoId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaPedido> lineas;
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    public void addLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public void pagar(Pagable metodoPago) {
        metodoPago.pagar(getPrecioTotal());
        this.metodoPago = switch (metodoPago) {
            case PagarEfectivo ignored-> MetodoPago.EFECTIVO;
            case PagarTarjeta ignored -> MetodoPago.TARJETA;
            default -> null;
        };
        estado = EstadoPedido.FINALIZADO;
    }

    public double getPrecioTotal() {
        return lineas.stream().mapToDouble(LineaPedido::getTotal).sum();
    }
}
