/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Modelo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class JuegoController implements Initializable {

    @FXML
    private TextField ningresado;
    public static Integer npreguntas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void siguiente(ActionEvent event) throws IOException {
        if(isInteger(ningresado.getText()) && Integer.parseInt(ningresado.getText())<20 ){
            this.npreguntas=Integer.parseInt(ningresado.getText());
            App.setRoot("preguntas");
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Numero ingresado no válido");
            alert.show();
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
