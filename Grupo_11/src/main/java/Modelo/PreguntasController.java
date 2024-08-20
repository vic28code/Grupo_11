package Modelo;

import Estructura.Arbol;
import Estructura.ArbolBuilder;
import Estructura.Nodo;
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
    
    public static int nresultado;
    public static Arbol arbol_resultante;
    private int indexPreguntaActual = 0;
    private String preguntaActual;
    private Nodo nodoActual;
    private String respuesta_user;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.nrestantes.setText(String.valueOf(JuegoController.npreguntas));
        arbol_resultante = App.arbol;
        nodoActual = arbol_resultante.getRaiz();
        preguntaActual = nodoActual.pregunta;
        pregunta.setText(preguntaActual);
        
        btnsi.setOnAction(event -> manejarRespuesta("si"));
        btnno.setOnAction(event -> manejarRespuesta("no"));}    
    
    public void manejarRespuesta(String respuesta){
        respuesta_user = respuesta;
        arbol_resultante = ArbolBuilder.ReducirArbol(respuesta_user, preguntaActual);
       
        if (arbol_resultante.getRaiz() == null) {
            resultado(0);  
            return;
        }
        nodoActual = arbol_resultante.getRaiz();
        nresultado = arbol_resultante.recorrer().size();
        
        if (indexPreguntaActual >= JuegoController.npreguntas - 1 || nresultado==0 || arbol_resultante.obtenerAnimalesPosibles(nodoActual).size()==1) {
            resultado(nresultado);
        } else {
            preguntaActual = nodoActual.pregunta;
            pregunta.setText(preguntaActual);
            indexPreguntaActual++;
            nrestantes.setText(String.valueOf(JuegoController.npreguntas - indexPreguntaActual));
        }
    }
    
     
    private void resultado(int n){
        try {
            this.nresultado=n;
            App.setRoot("resultado");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
