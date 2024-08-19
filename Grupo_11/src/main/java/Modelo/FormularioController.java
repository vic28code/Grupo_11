package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FormularioController implements Initializable {

    @FXML
    private Label question;
    @FXML
    private Button si;
    @FXML
    private Button no;
    @FXML
    private HBox hboxPreguntas;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    private int preguntaActual = 0;  // Índice de la pregunta actual
    private List<String> camposRespuestas = new ArrayList<>();  // Lista para almacenar las respuestas ("sí" o "no")
    private List<String> listaPreguntas;  // Lista de preguntas
    private String rutaArc="src/main/resources/archivos/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarPregunta();
        si.setOnAction(event -> registrarRespuesta("si"));
        no.setOnAction(event -> registrarRespuesta("no"));
        btnGuardar = new Button();
        btnGuardar.setVisible(false);
        btnGuardar.setText("Guardar");
        btnCancelar = new Button();
        btnCancelar.setText("Cancelar");
        hboxPreguntas.getChildren().addAll(btnGuardar, btnCancelar);
        btnGuardar.setOnAction(event -> guardarNuevo());
        btnCancelar.setOnAction(event -> {
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
        });

    }
    
     public List<String> cargarPreguntas(String rutaArchivo) {
       listaPreguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                listaPreguntas.add(linea.trim()); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaPreguntas;
    }

    private void mostrarPregunta() {
        if (preguntaActual < cargarPreguntas(rutaArc+JuegoController.tipoPreguntas).size()) {
            question.setText(listaPreguntas.get(preguntaActual));
        } else {
            si.setDisable(true);
            no.setDisable(true);
            btnGuardar.setVisible(true);

        }
    }

    private void registrarRespuesta(String respuesta) {
        camposRespuestas.add(respuesta);
        preguntaActual++;
        mostrarPregunta();
    }

    private void guardarNuevo() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Animal");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingresa el nombre de tu decisión:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String nuevo = result.get().trim();
            String linea = nuevo + "-" + String.join(",", camposRespuestas);
            System.out.println(JuegoController.tipoArbol);
            String archivo = "src/main/resources/archivos/";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo + JuegoController.tipoArbol, true))) {
                bw.write(linea);
                bw.newLine();
                System.out.println("se dio");
            } catch (IOException e) {
                System.out.println("Error al cargar");
            }
        }
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

}
