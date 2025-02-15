package es.adr.model.producto;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("BEBIDA")
public class Bebida extends Producto {
    @Enumerated(EnumType.STRING)
    private Size size;
}
