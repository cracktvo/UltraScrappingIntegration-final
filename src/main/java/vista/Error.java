/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author oscar
 */
public class Error {

    public static void showError(String error) throws IOException {
        FXMLLoader loader = new FXMLLoader(Error.class.getClass().getClassLoader().getResource(("ErrorPU.fxml")));
        ErrorPUController controller = new ErrorPUController(error);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setTitle("Error");
        stage.showAndWait();
    }
}
