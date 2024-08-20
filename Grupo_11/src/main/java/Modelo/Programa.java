package Modelo;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Programa {

    @FXML
    private Button jugarButton;

    @FXML
    private void play() throws IOException {
        App.setRoot("juego");
    }
}
