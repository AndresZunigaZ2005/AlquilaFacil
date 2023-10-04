package co.edu.uniquindio.alquila.controllers;
import co.edu.uniquindio.alquila.exceptions.ClienteException;
import co.edu.uniquindio.alquila.model.AlquilaFacil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class RegistroClientesController {


    private Stage stage;

    //private SingletonController singletonController = SingletonController.getInstance();

    @FXML
    private TextField cedulaField;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField telefonoField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ciudadField;

    @FXML
    private TextField direccionField;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnIrAtras;

    private final AlquilaFacil alquilaFacil=AlquilaFacil.getInstance();

    @FXML
    private void registrar(ActionEvent event) {
        String cedula = cedulaField.getText();
        String nombre = nombreField.getText();
        String telefono = telefonoField.getText();
        String email = emailField.getText();
        String ciudad = ciudadField.getText();
        String direccion = direccionField.getText();

        try {
            alquilaFacil.crearCliente(cedula, nombre, telefono, email, ciudad, direccion);
            alerta(Alert.AlertType.INFORMATION, "Confirmacion", "Registro exitoso","El cliente "+nombre+" se ha registrado en la base de datos");
        } catch (ClienteException e) {
            alerta(Alert.AlertType.ERROR, "Error", "Error en el registro", e.getMessage());
        }

        // Limpia los campos después del registro
        cedulaField.clear();
        nombreField.clear();
        telefonoField.clear();
        emailField.clear();
        ciudadField.clear();
        direccionField.clear();
    }

    @FXML
    private void irAtras(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/VentanaPrincipal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        ((Stage) btnIrAtras.getScene().getWindow()).close();
    }

    public void setStage(Stage primaryStage) {

    }

    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////
     */

    public static void alerta(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
