package Modelo;

import Estructura.ArbolBuilder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class JuegoController implements Initializable {

    @FXML
    private TextField ningresado;
    @FXML private ToggleButton btnAnimales;
    @FXML private ToggleButton btnPersonajes;
    @FXML private ToggleButton btnObjetos;
    @FXML private ToggleButton btnCargarMiArbol;
    
    public static Integer npreguntas;
    private ToggleButton btnSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        cargarArbolAnimales();
        cargarArbolPersonajes();
        cargarArbolOnjetos();
        cargarArbolExterno();
    }    
    
    @FXML
    private void siguiente(ActionEvent event) throws IOException {
        if(App.arbol==null){
            System.out.print("Arbol nulo");
            return;
        }
        System.out.print(App.arbol.toString());
        
        if(isInteger(ningresado.getText()) && Integer.parseInt(ningresado.getText())<=20 ){
            this.npreguntas=Integer.parseInt(ningresado.getText());
            App.setRoot("preguntas");
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Numero ingresado no válido");
            alert.show();
        }
    }
    
    public void cargarArbolAnimales(){
        btnAnimales.setOnAction((ActionEvent e)->{
            App.arbol = ArbolBuilder.inicializarArbol("preguntasAnimales.txt", "respuestasAnimales.txt");
            deseleccionarBotones(btnAnimales);
        });
    }
    
    public void cargarArbolPersonajes(){
        btnPersonajes.setOnAction((ActionEvent e)->{
            App.arbol = ArbolBuilder.inicializarArbol("preguntasPersonajes.txt", "respuestasPersonajes.txt");
            deseleccionarBotones(btnPersonajes);
        });
    }
    
    public void cargarArbolOnjetos(){
        btnObjetos.setOnAction((ActionEvent e)->{
            App.arbol = ArbolBuilder.inicializarArbol("preguntasObjetos.txt", "respuestasObjetos.txt");
            deseleccionarBotones(btnObjetos);
        });
    }
    
    public void cargarArbolExterno(){
        btnCargarMiArbol.setOnAction((ActionEvent e)->{
            try {
                deseleccionarBotones(btnObjetos);
                App.setRoot("cargarMiArbol");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    private void deseleccionarBotones(ToggleButton tgb){
         if (btnSeleccionado == tgb) {
            tgb.setSelected(false);
            btnSeleccionado = null;
        }
        else {
            if (btnSeleccionado != null) {
                btnSeleccionado.setSelected(false);
            }
            tgb.setSelected(true);
            btnSeleccionado = tgb;
        }
    }
    
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
