package Modelo;

import Estructura.Arbol;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Arbol arbol = null;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("programa"));
        stage.setScene(scene);
        stage.getIcons().add(loadImage("src/main/resources/imagenes/iconos/lamparaIcon.png"));
        stage.setTitle("Juego - 20 Preguntas");
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static Image loadImage(String pathImage){
        File f = new File(pathImage);
        return new Image(f.toURI().toString());
    }

    public static void main(String[] args) {
        launch();
    }

}