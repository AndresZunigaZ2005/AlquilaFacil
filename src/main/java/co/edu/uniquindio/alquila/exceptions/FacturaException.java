package co.edu.uniquindio.alquila.exceptions;

public class FacturaException extends Exception{

    public FacturaException(String mensaje){
        super("Error en la creación de la factura"+mensaje);
    }
}
