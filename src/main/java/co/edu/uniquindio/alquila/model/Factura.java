package co.edu.uniquindio.alquila.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    private Cliente cliente;
    private LocalDate pagoFactura;
    private LocalDateTime fechaAlquiler;
    private LocalDateTime fechaRegreso;
    private Vehiculo vehiculo;
    private double precioTotal;

}
