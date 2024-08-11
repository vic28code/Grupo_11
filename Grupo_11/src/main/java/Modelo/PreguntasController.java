/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Modelo;

import Estructura.Arbol;
import Estructura.ArbolBuilder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
/**
 * FXML Controller class
 *
 * @author USER
 */
public class PreguntasController implements Initializable {


    @FXML
    private Label pregunta;
    @FXML
    private Label nrestantes;
    @FXML
    private Button btnsi;
    @FXML
    private Button btnno;
    
    private int indexPreguntaActual = 0;
    private String preguntaActual;
    private Arbol arbolReducido;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.nrestantes.setText(String.valueOf(JuegoController.npreguntas));
        mostrarPreguntas();
        btnsi.setOnAction(event -> manejarRespuesta(true));
        btnno.setOnAction(event -> manejarRespuesta(false));
    }    
    
    //Se asume preguntas.txt como principal, falta implementar cuando el usuario elige 
    public void mostrarPreguntas(){
        ArbolBuilder.inicializarArbol();
        preguntaActual=ArbolBuilder.listaPreguntas.get(indexPreguntaActual);
        pregunta.setText(preguntaActual);
        
    }
    
    private void manejarRespuesta(boolean respuesta) {
      //  ArbolBuilder.ReducirArbol(String.valueOf(respuesta),preguntaActual );
        System.out.println(respuesta );
        System.out.println("!!!!!IMPORTANTE: SIZE LISTAPREGUNTAS: " +ArbolBuilder.listaPreguntas.size());
        
         
        JuegoController.npreguntas--;
        this.nrestantes.setText(String.valueOf(JuegoController.npreguntas));
        indexPreguntaActual++;
        if (JuegoController.npreguntas <= 0) {
            resultado();
        } else {
            mostrarPreguntas();
        }
    }
     
    private void resultado(){
//        System.out.println(arbolReducido.recorrer());
        try {
            App.setRoot("resultado");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     
    }
    
}
