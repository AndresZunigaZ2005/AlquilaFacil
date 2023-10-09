package co.edu.uniquindio.alquila.model;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo implements Serializable {


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
        return "Marca:"+marca+"- Referencia"+referencia+"- Modelo:"+modelo+"- Precio diario:"+precioDia;
    }
}
