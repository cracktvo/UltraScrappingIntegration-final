/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class ProgresoPUController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lNumero;
    @FXML
    ProgressBar pBprogreso;

    private Task generador;

    double avance;

    double contador;


    Stage stage;


    public ProgresoPUController(VistaGeneral.ObtenerProcess task, Stage parent) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ProgresoPU.fxml"));
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.initOwner(parent);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setTitle("Cargando datos");
        stage.show();
        this.pBprogreso.progressProperty().unbind();
        this.pBprogreso.progressProperty().bind(task.progressProperty());
        task.setStage(stage);
        task.setlNumero(lNumero);
        Thread hilo = new Thread(task);
        hilo.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void avanzar(){
        pBprogreso.setProgress(contador++);
        lNumero.setText(contador + "%");
        stage.show();
    }

    public void cerrar() throws IOException {
        Stage stage;
        stage = (Stage) pBprogreso.getScene().getWindow();
        stage.close();
    }

    public double getContador() {
        return contador;
    }

    public void setContador(double contador) {
        this.contador = contador;
    }
}

