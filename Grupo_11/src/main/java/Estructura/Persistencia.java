package Estructura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;
import javafx.stage.FileChooser;

/**
 *
 * @author vv
 */
public class Persistencia {
    
    private static final String ARCHIVO_PATH = "src/main/resources/archivos/";
    private static int numeroDePreguntasActuales = 0;
    
    public static List<String> cargarPreguntas(String nombreArchivo){
        List<String> listaPreguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PATH + nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                listaPreguntas.add(linea);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Tamaño lista preguntas -> "+listaPreguntas.size());
        System.out.println(listaPreguntas.toString());
        return listaPreguntas;
    }

    
    public static List<Respuesta> cargarRespuestas(String nombreArchivo, List<String> preguntas) {
        List<Respuesta> respuestas = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PATH + nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");
                Respuesta animal = new Respuesta(partes[0]);
                List<String> respuestasAnimal = Arrays.asList(partes[1].split(","));            
                for(int c=0; c<respuestasAnimal.size();c++){
                    animal.setRespuesta(preguntas.get(c), respuestasAnimal.get(c));
                }
                respuestas.add(animal);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Tamaño lista respuestas -> "+respuestas.size());
        System.out.println(respuestas.toString());
        return respuestas;
    }
    
    public static List<String> cargarPreguntasExternas() throws FileNotFoundException, IOException, IllegalFormatException, ArrayIndexOutOfBoundsException {
        List<String> listaPreguntas = new ArrayList<>();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.txt"));
        File filePreguntas = fileChooser.showOpenDialog(null);
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePreguntas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if(linea.contains("¿")){
                    linea = linea.replace("¿","");
                }
                listaPreguntas.add(linea);
            }
        }
        numeroDePreguntasActuales = listaPreguntas.size();
        return listaPreguntas;
    }
    
    public static List<Respuesta> cargarRespuestasExternas(List<String> preguntas) throws FileNotFoundException, IOException, IllegalFormatException, ArrayIndexOutOfBoundsException{
        List<Respuesta> respuestas = new ArrayList<>();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.txt"));
        File filePreguntas = fileChooser.showOpenDialog(null);
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePreguntas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");
                Respuesta animal = new Respuesta(partes[0]);
                
                List<String> respuestasAnimal = Arrays.asList(partes[1].split(","));
                if(respuestasAnimal.size()!= numeroDePreguntasActuales){
                    throw new ArrayIndexOutOfBoundsException();
                }
                
                for(int c=0; c<respuestasAnimal.size();c++){
                    animal.setRespuesta(preguntas.get(c), respuestasAnimal.get(c));
                }
                respuestas.add(animal);
            }
        }
        System.out.println("Tamaño lista respuestas -> "+respuestas.size());
        System.out.println(respuestas.toString());
        return respuestas;
    }
    
}
