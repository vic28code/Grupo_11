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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    private Button btnsi;
    private Button btnno;
    private Button salir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultado();
    }

    public void resultado() {
        if (numero == 0) {
            label1.setText("Lo siento pero no conozco\nalgo con esas características.");
            label2.setText("¿Te gustaría añadir tu respuesta\na mi base de datos?");
            btnsi = new Button();
            btnno = new Button();
            btnsi.setText("Si");
            btnno.setText("No");
            hboxBotones.getChildren().clear();
            hboxBotones.getChildren().addAll(btnsi, btnno);
            btnsi.setOnAction(event -> iniciarFormularioNuevoAnimal());
            btnno.setOnAction(event -> mostrarMensajeFinal());

        } else if (numero == 1) {
            label1.setText("¡La respuesta es: " + arbol.obtenerAnimalesPosibles(arbol.getRaiz()).get(0) + "!");
            salir = new Button();
            salir.setText("->");
            hboxBotones.getChildren().addAll(salir);
            salir.setOnAction(event -> mostrarMensajeFinal());
        } else {
            label1.setText("No se pudo determinar en lo que pensaste con las preguntas realizadas.");
            List<String> respuestas = arbol.obtenerAnimalesPosibles(arbol.getRaiz());
            label2.setText("Respuestas posibles:\n" + String.join("\n", respuestas));
            salir = new Button();
            salir.setText("->");
            hboxBotones.getChildren().addAll(salir);
            salir.setOnAction(event -> mostrarMensajeFinal());
        }
    }

    private void iniciarFormularioNuevoAnimal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formulario.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Formulario");
            newStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void mostrarMensajeFinal() {
        hboxBotones.getChildren().clear();
        label1.setText("Gracias por jugar \n ¿Te gustaría jugar de nuevo?");
        label2.setText("");
        btnsi = new Button();
        btnno = new Button();
        btnsi.setText("Sí");
        btnno.setText("No");
        hboxBotones.getChildren().addAll(btnsi, btnno);
        btnsi.setOnAction(event -> {
            try {
                App.setRoot("juego"); // Regresa a la pantalla de juego inicial
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnno.setOnAction(event -> Platform.exit()); // Cierra el programa

    }

}
