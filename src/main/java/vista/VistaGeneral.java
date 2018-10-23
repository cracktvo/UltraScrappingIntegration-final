/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Scraping.Scraping;
import controlador.GeneradorControlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import modelo.entidades.Cliente;
import uploader.Uploader;

/**
 * @author oscar
 */
public class VistaGeneral implements Initializable {

    private static final Logger LOG = Logger.getLogger(Scraping.class.getName());

    private Task generador;

    @FXML
    private TextArea textArea;

    @FXML
    private Button button;

    @FXML
    private CheckBox checkBox;

    @FXML
    private CheckBox numSearch;

    Scraping scraping = new Scraping();

    GeneradorControlador controlador = new GeneradorControlador();


    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, Exception {
        List<Cliente> clientes;
        ProgresoPUController puController;
        if (event.getSource().equals(button)) {
            String cadena = textArea.getText();
            Stage stage = null;
            stage = (Stage) button.getScene().getWindow();
            ProgressIndicator indicator = new ProgressIndicator();
            try {
                clientes = controlador.getClientesCollection(cadena,checkBox.isSelected(),textArea,numSearch.isSelected());
                puController = new ProgresoPUController(generarDatos(clientes), stage);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Log al sisact: Error: {0}", e);
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                Alert error = new Alert(AlertType.ERROR);
                error.setTitle("Error Interno");
                error.setContentText("Descripcion del error: " + exceptionAsString);
                error.showAndWait();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Generar Datos");
        alert.setHeaderText("Conectando...");
        alert.getButtonTypes().clear();
        alert.show();
        alert.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        try {
            scraping.inicializar();
            alert.close();
        } catch (IOException e) {
            e.printStackTrace();
            alert.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            alert.close();
        }
    }

    public ObtenerProcess generarDatos(List<Cliente> clientes) throws Exception {
        return new ObtenerProcess() {
            List<Cliente> resultado = new ArrayList<>();
            double i = 1;
            double tamanio = clientes.size();

            @Override
            protected Object call() throws Exception {
                Cliente nuevo;
                Cliente pasado = new Cliente();
                List<String> noExisten = new ArrayList<>();
                for (Cliente entity : clientes) {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        if(numSearch.isSelected()){
                            getlNumero().setText(entity.getNumeroTelefonico());
                        }else{
                            getlNumero().setText(entity.getCuenta());
                        }
                    });
                    nuevo = scraping.obtenerCliente(entity,numSearch.isSelected());
                    if ((nuevo.getNombre()==null||nuevo.getPlan()==null||nuevo.getCuenta()==null||nuevo.getPuntosCA()==null)&&!"0000000000".equals(nuevo.getNumeroTelefonico())) {
                        scraping.obtenerCliente(new Cliente("0000000000",null),numSearch.isSelected());
                        nuevo = scraping.obtenerCliente(entity,numSearch.isSelected());
                    }
                    if (nuevo.getNombre()!=null&&!"0000000000".equals(nuevo.getNumeroTelefonico())) {
                        if(nuevo.getPuntosCA()==null) {
                            scraping.obtenerCliente(new Cliente("0000000000",null),numSearch.isSelected());
                            nuevo = scraping.obtenerCliente(entity,numSearch.isSelected());
                        }
                    }
                    if (nuevo.getNombre()!=null&&!"0000000000".equals(nuevo.getNumeroTelefonico())) {
                        if(nuevo.getPuntosCA()==null) {
                            scraping.obtenerCliente(new Cliente("0000000000",null),numSearch.isSelected());
                            nuevo = scraping.obtenerCliente(entity,numSearch.isSelected());
                        }
                    }
                    if (nuevo.getNombre()!=null&&!"0000000000".equals(nuevo.getNumeroTelefonico())) {
                        if(nuevo.getPuntosCA()==null) {
                            scraping.obtenerCliente(new Cliente("0000000000",null),numSearch.isSelected());
                            nuevo = scraping.obtenerCliente(entity,numSearch.isSelected());
                        }
                    }
                    if (nuevo.getNombre()!=null&&!"0000000000".equals(nuevo.getNumeroTelefonico())) {
                        if(nuevo.getPuntosCA()==null) {
                            scraping.obtenerCliente(new Cliente("0000000000",null),numSearch.isSelected());
                            nuevo = scraping.obtenerCliente(entity,numSearch.isSelected());
                        }
                    }
                    if (nuevo.getCuenta() != null&&!"0000000000".equals(nuevo.getNumeroTelefonico())) {
                        if (nuevo.getCuenta().equals(pasado.getCuenta())&&(!nuevo.getNumeroTelefonico().equals(pasado.getNumeroTelefonico()))) {
                            LOG.log(Level.INFO, "cuentaRepetida: {0}", nuevo.getCuenta());
                        }
                        else{
                            if(nuevo.getNombre()!=null) {
                                Uploader.insertarCliente(nuevo);
                            }
                        }
                    }
                    if (nuevo.getNombre()==null&&!"0000000000".equals(nuevo.getNumeroTelefonico())&&!"0000000000".equals(nuevo.getCuenta())) {
                        noExisten.add(entity.getNumeroTelefonico()+"\n");
                    }
                    pasado = nuevo;
                    updateProgress((10 / tamanio) * i++, 10);
                }
                Platform.runLater(() -> {
                    try {
                        this.stage.close();
                        scraping.finalizar();
                        if (noExisten.isEmpty()) {
                            Alert fin = new Alert(AlertType.INFORMATION);
                            fin.setTitle("Fin");
                            fin.setHeaderText("Datos generados");
                            fin.showAndWait();
                        } else {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("No resuletos");
                            alert.setHeaderText("Por alguna razon no fue existosa la extracción de los siguientes numeros");
                            alert.setContentText("Verificar!");

                            Label label = new Label("Los números son:");

                            String mensaje = "";
                            for (String numero:noExisten){
                                mensaje+=numero;
                            }
                            TextArea textArea = new TextArea(mensaje);
                            textArea.setEditable(false);
                            textArea.setWrapText(true);

                            textArea.setMaxWidth(Double.MAX_VALUE);
                            textArea.setMaxHeight(Double.MAX_VALUE);
                            GridPane.setVgrow(textArea, Priority.ALWAYS);
                            GridPane.setHgrow(textArea, Priority.ALWAYS);

                            GridPane expContent = new GridPane();
                            expContent.setMaxWidth(Double.MAX_VALUE);
                            expContent.add(label, 0, 0);
                            expContent.add(textArea, 0, 1);

                            // Set expandable Exception into the dialog pane.
                            alert.getDialogPane().setExpandableContent(expContent);

                            alert.showAndWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                return true;
            }
        };
    }

    public abstract class ObtenerProcess extends Task {

        public Stage stage;

        @FXML
        private Label lNumero;

        public Stage getStage() {
            return stage;
        }

        public void setStage(Stage stage) {
            this.stage = stage;
        }

        public Label getlNumero() {
            return lNumero;
        }

        public void setlNumero(Label lNumero) {
            this.lNumero = lNumero;
        }
    }
}
