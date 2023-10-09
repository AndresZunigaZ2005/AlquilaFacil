package co.edu.uniquindio.alquila.controllers;

import co.edu.uniquindio.alquila.exceptions.VehiculoException;
import co.edu.uniquindio.alquila.model.AlquilaFacil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RegistroVehiculosController {


    //private SingletonController singletonController = SingletonController.getInstance();
    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    @FXML
    private TextField placaField;

    @FXML
    private TextField referenciaField;

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private Button btnBuscarImagen;

    @FXML
    private CheckBox automaticoCheckbox;

    @FXML
    private TextField kilometrajeField;

    @FXML
    private TextField precioDiaField;

    @FXML
    private TextField sillasField;

    @FXML
    private Button btnGuardarVehiculo;

    @FXML
    private Button btnIrAtras;

    private String rutaImagen="";

    @FXML
    private void irAtras() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ventanas/VentanaPrincipal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        ((Stage) btnIrAtras.getScene().getWindow()).close();
    }

    @FXML
    public void buscarImagen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            rutaImagen = filePath;
        }
    }

    @FXML
    private void guardarVehiculo(ActionEvent event) {
        // Aquí puedes obtener los valores de los campos y realizar la lógica de guardar el vehículo
        String placa = placaField.getText();
        String referencia = referenciaField.getText();
        String marca = marcaField.getText();
        int modelo = Integer.parseInt(modeloField.getText());
        String imagen = rutaImagen;
        rutaImagen = "";
        boolean isAutomatico = automaticoCheckbox.isSelected();
        double kilometraje = Double.parseDouble(kilometrajeField.getText());
        double precioDia = Double.parseDouble(precioDiaField.getText());
        int numeroSillas = Integer.parseInt(sillasField.getText());

        try {
            alquilaFacil.crearVehiculos(placa,referencia,marca,modelo,imagen,kilometraje,precioDia,isAutomatico,numeroSillas);
            alerta(Alert.AlertType.INFORMATION, "Información", "Información", "El vehículo se ha creado con éxito");

            placaField.clear();
            referenciaField.clear();
            marcaField.clear();
            modeloField.clear();
            automaticoCheckbox.setSelected(false);
            kilometrajeField.clear();
            precioDiaField.clear();
            sillasField.clear();
        } catch (VehiculoException e) {
            alerta(Alert.AlertType.ERROR, "Error", "El vehículo no se ha creado con éxito", e.getMessage());
        }
    }

    public void setStage(Stage primaryStage) {
    }

    public static void alerta(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

