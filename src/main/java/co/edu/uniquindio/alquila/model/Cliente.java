package co.edu.uniquindio.alquila.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente /*implements Serializable*/ {

    private String cedula;
    private String nombre;
    private String telefono;
    private String email;
    private String ciudad;
    private String direccion;

}
