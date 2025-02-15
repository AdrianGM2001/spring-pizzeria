package es.adr.model.ingrediente;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String nombre;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ingrediente_alergeno", joinColumns = @JoinColumn(name = "id_ingrediente"))
    @Column(name = "alergeno")
    private List<String> alergenos;
}
