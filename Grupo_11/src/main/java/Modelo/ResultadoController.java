/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Modelo;

import Estructura.Arbol;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ResultadoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Arbol arbol=PreguntasController.arbol_resultante;
    int numero=PreguntasController.nresultado;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultado();
    }    
    public void resultado(){
        if(numero==0){
            label1.setText("Lo siento pero no conozco un animal con esas caracteristicas");
        }else if(numero==1){
            label1.setText("¡El animal es: " + arbol.obtenerAnimalesPosibles(arbol.getRaiz()).get(0) + "!");
        }else{ 
            label1.setText("No se pudo determinar el animal con las preguntas realizadas.");
            List<String> animalesPosibles = arbol.obtenerAnimalesPosibles(arbol.getRaiz());
            label2.setText("Animales posibles: " + String.join(", ", animalesPosibles));
    }    
    }
}
