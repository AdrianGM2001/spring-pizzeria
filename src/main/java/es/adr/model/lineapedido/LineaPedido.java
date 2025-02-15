package es.adr.model.lineapedido;

import es.adr.model.producto.Producto;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "pedido_producto")
public class LineaPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "pedido_id", nullable = false)
    private int pedidoId;
    @Column(nullable = false)
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    public void addCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public double getTotal() {
        return cantidad * producto.getPrecio();
    }
}
