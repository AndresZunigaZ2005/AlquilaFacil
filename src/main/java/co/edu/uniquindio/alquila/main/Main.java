package co.edu.uniquindio.alquila.main;

import co.edu.uniquindio.alquila.controllers.RegistroClientesController;
import co.edu.uniquindio.alquila.controllers.RegistroVehiculosController;
import co.edu.uniquindio.alquila.controllers.VentanaIniciarSesionController;
import co.edu.uniquindio.alquila.controllers.VentanaPrincipalController;
import co.edu.uniquindio.alquila.model.AlquilaFacil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main extends Application {

    private AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaPrincipal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("La papu sesion :v");
            VentanaPrincipalController loginController = loader.getController();
            loginController.setStage(primaryStage);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
