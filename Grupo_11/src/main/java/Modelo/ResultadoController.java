/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Modelo;

import Estructura.Arbol;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ResultadoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Arbol arbol = PreguntasController.arbol_resultante;
    int numero = PreguntasController.nresultado;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private HBox hboxBotones;
    @FXML
    private Button btnsi;
    @FXML
    private Button btnno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultado();
    }

    public void resultado() {
        if (numero == 0) {
            label1.setText("Lo siento pero no conozco algo con esas características.");
            label2.setText("¿Te gustaría añadir tu respuesta a mi base de datos?");
            btnsi = new Button();
            btnno = new Button();
            btnsi.setText("Si");
            btnno.setText("No");
            hboxBotones.getChildren().clear();
            hboxBotones.getChildren().addAll(btnsi, btnno);
            btnsi.setOnAction(event -> iniciarFormularioNuevoAnimal());
            btnno.setOnAction(event -> mostrarMensajeFinal());

        } else if (numero == 1) {
            label1.setText("¡El animal es: " + arbol.obtenerAnimalesPosibles(arbol.getRaiz()).get(0) + "!");
        } else {
            label1.setText("No se pudo determinar el animal con las preguntas realizadas.");
            List<String> animalesPosibles = arbol.obtenerAnimalesPosibles(arbol.getRaiz());
            label2.setText("Animales posibles: " + String.join(", ", animalesPosibles));
        }
    }

    private void iniciarFormularioNuevoAnimal() {
        try {
            App.setRoot("formulario");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void mostrarMensajeFinal() {
        label1.setText("Gracias por jugar, ¿Te gustaría jugar de nuevo?");
        label2.setText("");

        btnsi.setText("Sí");
        btnno.setText("No");

        btnsi.setOnAction(event -> {
            try {
                App.setRoot("juego"); // Regresa a la pantalla de juego inicial
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnno.setOnAction(event -> Platform.exit()); // Cierra el programa
        hboxBotones.getChildren().clear();
        hboxBotones.getChildren().addAll(btnsi, btnno);
    }

}
