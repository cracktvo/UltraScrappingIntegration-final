/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class ErrorPUController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lError;
    @FXML
    Button bAceptar;    
    
    String error;

    public ErrorPUController(String error) {
        this.error = error;
    }   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lError.setText(error);
    }    
    
    @FXML
    private void botonAceptar(ActionEvent event) throws IOException {
        Stage stage;
        if (event.getSource() == bAceptar) {
            stage = (Stage) bAceptar.getScene().getWindow();
            stage.close();
        }
    }
    
}
