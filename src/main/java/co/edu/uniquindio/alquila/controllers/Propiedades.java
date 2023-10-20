package co.edu.uniquindio.alquila.controllers;

import javafx.beans.property.SimpleObjectProperty;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

//----------------------------------------------------------------

public class Propiedades {
/*    @Getter
    private final ResourceBundle resourceBundle;*/
    private static Propiedades instancia;
    private static LocalDateTime fechaCompartida1, fechaCompartida2;
//    private final String RUTA= "config/textos";
//    private final SimpleObjectProperty<ResourceBundle> bdProperty = new SimpleObjectProperty<>();
    private ResourceBundle recursos;



    public static Propiedades getInstance(){
        if(instancia == null){
            instancia = new Propiedades();
        }
        return instancia;
    }

    public void setFechasCompartidas(LocalDateTime fecha1, LocalDateTime fecha2){
        fechaCompartida1 = fecha1;
        fechaCompartida2 = fecha2;
    }

    public LocalDateTime[] getFechas(){
        return new LocalDateTime[]{fechaCompartida1, fechaCompartida2};
    }

 /*   public ResourceBundle obtenerRecursos() {
        return recursos;
    }

    public void setLanguage(Locale locale) {
        if (!locale.equals(bdProperty.getValue().getLocale()))
            bdProperty.setValue(ResourceBundle.getBundle(RUTA, locale));
    }

    public void setLanguage(String localeString) {
        setLanguage(new Locale.Builder().setLanguage(localeString).build());
    }

    public void addListener(Consumer<ResourceBundle> listener) {
        listener.accept(bdProperty.getValue());
        bdProperty.addListener((observable, oldValue, newValue) -> listener.accept(newValue));
    }

    public void setProperties(Consumer<ResourceBundle> listener) {
        listener.accept(bdProperty.getValue());
    }

*/
}
