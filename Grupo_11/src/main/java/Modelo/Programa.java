package Modelo;

import java.io.IOException;
import javafx.fxml.FXML;

public class Programa {

    @FXML
    private void play() throws IOException {
        App.setRoot("juego");
    }
}
