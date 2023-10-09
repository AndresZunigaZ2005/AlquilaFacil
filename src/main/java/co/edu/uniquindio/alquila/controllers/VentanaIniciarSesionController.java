package co.edu.uniquindio.alquila.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class VentanaIniciarSesionController {

    private Stage stage;

    public void setStage(Stage stage){

    }

    //private SingletonController singletonController = SingletonController.getInstance();

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrate;

    @FXML
    private ImageView imagenLogo;

    @FXML
    private Label lblIniciarSesion;

    @FXML
    private Label lblNoTienesCuenta;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtUsuario;

    @FXML
    void iniciarSesion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ventanas/VentanaPrincipal.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("La papu ventana :v");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();

        ((Stage) btnIniciarSesion.getScene().getWindow()).close();
    }




    @FXML
    void registrarse(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ventanas/RegistroClientes.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("La papu ventana :v");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();

        ((Stage) btnRegistrate.getScene().getWindow()).close();
    }

}

