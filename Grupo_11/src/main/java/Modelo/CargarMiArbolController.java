package Modelo;

import Estructura.Arbol;
import Estructura.ArbolBuilder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class CargarMiArbolController implements Initializable {

    @FXML Button btnVolver;
    @FXML Button btnContinuar;
    @FXML Button btnSubirPreguntas;
    @FXML Button btnSubirRespuestas;
    @FXML Label estadoSubirPreguntas;
    @FXML Label estadoSubirRespuestas;
    @FXML ImageView iconoAyuda;
    
    private Arbol arbol;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        arbol = null;
        estadoSubirPreguntas.setText("");
        estadoSubirRespuestas.setText("");
        
        subirRespuestas();
        subirPreguntas();
        volver();
        continuar();
        botonAyuda();
    }
    
    public void subirPreguntas(){
        btnSubirPreguntas.setOnAction((ActionEvent e)->{
            estadoSubirPreguntas.setText("");
            try{
            ArbolBuilder.cargarPreguntasExternas();
            estadoSubirPreguntas.setText("Archivo cargado con exito");
            estadoSubirPreguntas.setStyle("-fx-text-fill: green;");
            arbol = null;
            btnSubirRespuestas.setDisable(false);
            btnContinuar.setDisable(true);
            }
            catch(Exception ex){
                estadoSubirPreguntas.setText("Hubo un error al cargar el archivo");
                estadoSubirPreguntas.setStyle("-fx-text-fill: red;");
                ex.printStackTrace();
            }
        });
    }
    
    public void subirRespuestas(){
        btnSubirRespuestas.setOnAction((ActionEvent e)->{
            estadoSubirRespuestas.setText("");
            try{
            ArbolBuilder.cargarRespuestasExternas();
            arbol = ArbolBuilder.inicializarArbolExterno();
            estadoSubirRespuestas.setText("Archivo cargado con exito");
            estadoSubirRespuestas.setStyle("-fx-text-fill: green;");
            btnContinuar.setDisable(false);
            }
            catch(Exception ex){
                estadoSubirRespuestas.setText("El archivo no cuenta con el formato adecuado");
                estadoSubirRespuestas.setStyle("-fx-text-fill: red;");
                ex.printStackTrace();
            }
        });
    }
    
    public void volver(){
        btnVolver.setOnAction((ActionEvent e)->{
            try {
                App.setRoot("juego");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public void continuar(){
        btnContinuar.setOnAction((ActionEvent e)->{
            if(arbol!= null){
                try {
                    App.arbol = arbol;
                    App.setRoot("juego");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public void botonAyuda(){
        iconoAyuda.setOnMouseClicked((MouseEvent e)->{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ayudaFormatos.fxml"));
            VBox vbox;
            try {
                vbox = loader.load();
                Alert alert = new Alert(AlertType.NONE);
                alert.getDialogPane().setContent(vbox);
                alert.setTitle("Formato de los archivos");
                alert.getButtonTypes().addAll(ButtonType.CLOSE);
                alert.showAndWait();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }
    
}
