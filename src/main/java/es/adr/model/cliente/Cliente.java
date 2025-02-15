package es.adr.model.cliente;

import es.adr.model.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;
    @Column(nullable = false)
    private boolean admin;

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }
}
