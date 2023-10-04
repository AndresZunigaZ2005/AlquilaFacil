package co.edu.uniquindio.alquila.controllers;

import co.edu.uniquindio.alquila.exceptions.ClienteException;
import co.edu.uniquindio.alquila.exceptions.FacturaException;
import co.edu.uniquindio.alquila.exceptions.VehiculoException;
import co.edu.uniquindio.alquila.model.AlquilaFacil;
import co.edu.uniquindio.alquila.model.Cliente;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GenerarFacturaController implements Initializable {

    private Stage stage;

    //private SingletonController singletonController = SingletonController.getInstance();

    @FXML
    private ComboBox<Vehiculo> comboBoxVehiculos;
    @FXML
    private TextField cedulaClienteField;

    @FXML
    private Button crearFacturaButton;

    @FXML
    private Button atrasButton;

    private AlquilaFacil alquilaFacil=AlquilaFacil.getInstance();

    private LocalDateTime localDateTimeRecogida=Propiedades.getInstanceLocal();
    private LocalDateTime localDateTimeDevolucion=Propiedades.getInstanceLocal();

    @FXML
    void crearFactura(ActionEvent event) {
        try {
            String cedulaCliente = cedulaClienteField.getText();
            Vehiculo vehiculoElegido = comboBoxVehiculos.getValue();
            vehiculoElegido.setDisponible(false);
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmación");
            confirmationAlert.setHeaderText("¿Desea continuar con esta acción?");
            confirmationAlert.setContentText("Valor Total: "+ (((int) ChronoUnit.DAYS.between(localDateTimeRecogida, localDateTimeDevolucion)))*vehiculoElegido.getPrecioDia());
            ButtonType buttonTypeYes = new ButtonType("Sí", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == buttonTypeYes) {
                alquilaFacil.crearFactura(cedulaCliente,vehiculoElegido.getPlaca(), LocalDate.now(), localDateTimeRecogida, localDateTimeDevolucion);
                for (Vehiculo vehiculo: alquilaFacil.getListaVehiculos()) {
                    if(vehiculo.isDisponible()){
                        comboBoxVehiculos.getItems().add(vehiculo);
                        comboBoxVehiculos.getSelectionModel().selectFirst();
                    }
                }
            }else{
                vehiculoElegido.setDisponible(true);
            }
            } catch (ClienteException | VehiculoException | FacturaException e) {
            alerta(Alert.AlertType.ERROR, "Error", "Error", e.getMessage());
        }

        /*List<Vehiculo> listaArreglada = new ArrayList<>();
        for (Vehiculo vehiculo:alquilaFacil.getListaVehiculos()) {
            if(vehiculo.isDisponible()){
                listaArreglada.add(vehiculo);
            }
        }
        comboBoxVehiculos.getItems().addAll(listaArreglada);
        comboBoxVehiculos.getSelectionModel().selectFirst();
        try {
            alquilaFacil.obtenerCliente(cedulaCliente);
        } catch (ClienteException e) {
            alerta(Alert.AlertType.ERROR, "Error", "Error", e.getMessage());
        }*/
    }

    @FXML
    void atras(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/VentanaPrincipal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        ((Stage) atrasButton.getScene().getWindow()).close();
    }

    public void setStage(Stage stage){

    }

    public static void alerta(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*List<Vehiculo> listaArreglada = new ArrayList<>();
        for (Vehiculo vehiculo:alquilaFacil.getListaVehiculos()) {
            if(vehiculo.isDisponible()){
                listaArreglada.add(vehiculo);
            }
        }*/
        comboBoxVehiculos.getItems().addAll(alquilaFacil.getListaVehiculos());
        comboBoxVehiculos.getSelectionModel().selectFirst();
    }
}

