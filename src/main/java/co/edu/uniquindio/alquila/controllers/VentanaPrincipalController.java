package co.edu.uniquindio.alquila.controllers;

import co.edu.uniquindio.alquila.model.AlquilaFacil;
import co.edu.uniquindio.alquila.model.Cliente;
import co.edu.uniquindio.alquila.model.Factura;
import co.edu.uniquindio.alquila.model.Vehiculo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class VentanaPrincipalController implements Initializable {

    //private final SingletonController singletonController= SingletonController.getInstance();

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();
    @FXML
    private Hyperlink HyperLinkRegistro;

    @FXML
    private Button btnBuscar;

    @FXML
    private DatePicker datePickerFechaDevolucion;

    @FXML
    private DatePicker datePickerFechaRecogida;

    @FXML
    private Label lblAlquilaFacil;

    @FXML
    private Label lblFechaDevolucion;

    @FXML
    private Label lblFechaRecogida;

    @FXML
    private Spinner<Integer> spinnerHoraRecogida;

    @FXML
    private Spinner<Integer> spinnerMinutoRecogida;

    @FXML
    private Spinner<Integer>spinnerHoraDevolucion;

    @FXML
    private Spinner<Integer>spinnerMinutoDevolucion;

    @FXML
    private Button btnAnadirVehiculo;

    @FXML
    private Button btnMasAlquilado;

    private LocalDateTime localDateTimeRecogida=Propiedades.getInstanceLocal();
    private LocalDateTime localDateTimeDevolucion=Propiedades.getInstanceLocal();


    //datePickerFechaRecogida.getValue().atTime(LocalTime.of(spinnerHoraRecogida.getValue(), spinnerMinutoRecogida.getValue()));


    @FXML
    void buscarVehiculo(ActionEvent event) throws IOException {
        if(datePickerFechaDevolucion.getValue() == null || datePickerFechaRecogida.getValue() == null){
            alerta(Alert.AlertType.ERROR, "Error", "Error", "No hay un intervalo de fechas disponible");
        } else if (datePickerFechaRecogida.getValue().isAfter(datePickerFechaDevolucion.getValue()) || datePickerFechaRecogida.getValue().equals(datePickerFechaDevolucion.getValue())) {
            alerta(Alert.AlertType.ERROR, "Error", "Error", "La fecha de recogida no puede estar después a la fecha de devolución");
        } else {
            localDateTimeRecogida = datePickerFechaRecogida.getValue().atTime(LocalTime.of(spinnerHoraRecogida.getValue(), spinnerMinutoRecogida.getValue()));
            localDateTimeDevolucion = datePickerFechaDevolucion.getValue().atTime(LocalTime.of(spinnerHoraDevolucion.getValue(), spinnerMinutoDevolucion.getValue()));
            Parent root = FXMLLoader.load(getClass().getResource("/GenerarFactura.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            ((Stage) btnBuscar.getScene().getWindow()).close();
        }
    }

    @FXML
    void registrar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/RegistroClientes.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        ((Stage) HyperLinkRegistro.getScene().getWindow()).close();
    }

    @FXML
    public void anadirVehiculo(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/RegistroVehiculos.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        ((Stage) btnAnadirVehiculo.getScene().getWindow()).close();
    }

    @FXML
    public void mostrarMasAlquilado(ActionEvent event){
        List<String> listaDeCadenas = new ArrayList<String>();
        for (Factura factura: alquilaFacil.getListaFactura()) {
            listaDeCadenas.add(factura.getVehiculo().getMarca());
        }
        Map<String, Integer> recuentoPalabras = new HashMap<>();

        for (String palabra : listaDeCadenas) {
            // Convierte la palabra a minúsculas para hacer el recuento sin distinción entre mayúsculas y minúsculas
            palabra = palabra.toLowerCase();

            // Verifica si la palabra ya existe en el mapa
            if (recuentoPalabras.containsKey(palabra)) {
                recuentoPalabras.put(palabra, recuentoPalabras.get(palabra) + 1);
            } else {
                recuentoPalabras.put(palabra, 1);
            }
        }

        // Encuentra la palabra con el recuento máximo
        String palabraMasRepetida = "";
        int maxRecuento = 0;

        for (Map.Entry<String, Integer> entry : recuentoPalabras.entrySet()) {
            if (entry.getValue() > maxRecuento) {
                maxRecuento = entry.getValue();
                palabraMasRepetida = entry.getKey();
            }
        }

        alerta(Alert.AlertType.INFORMATION,"Información", "La marca más alquilada es:", palabraMasRepetida);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Metodos para abrir ventanas
     */
    private VentanaIniciarSesionController ventanaIniciarSesionController;

    private RegistroClientesController registroClientesController;

    private Stage stage;

    public void setStage(Stage stage) {

    }

    public void init(Stage stage, VentanaIniciarSesionController ventana) {
        this.ventanaIniciarSesionController= ventana;
        this.stage= stage;
    }

    public void init(Stage stage, RegistroClientesController ventana){
        this.registroClientesController= ventana;
        this.stage= stage;
    }

    public static void alerta(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /*
    Metodo para incilizar los objetos escenciales de la ventana
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerHoraRecogida.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        spinnerMinutoRecogida.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        spinnerHoraDevolucion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        spinnerMinutoDevolucion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        ////////////////////////////////////////////////////////////////////////
        //Metodo para leer los archivos
        /*try {
            ObjectInputStream flujoEntrados = new ObjectInputStream(new FileInputStream("alquilaFacil.data"));
            while (true) {
                alquilaFacil.getListaClientes().add((Cliente) flujoEntrados.readObject());
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
    }
}
