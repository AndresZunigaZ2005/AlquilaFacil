package co.edu.uniquindio.alquila.model;
import lombok.*;

import java.util.ArrayList;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    private String placa;
    private String referencia;
    private String marca;
    private int modelo;
    private String imagen;
    private double kilometraje;
    private double precioDia;
    private boolean isAutomatico;
    private int numeroSillas;
    private boolean isDisponible;

    @Override
    public String toString() {
        return marca+"-"+referencia+"-"+modelo+"-"+placa;
    }
}
