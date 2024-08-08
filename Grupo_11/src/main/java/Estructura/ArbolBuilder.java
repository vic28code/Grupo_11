package Estructura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vv
 */
public class ArbolBuilder {
    private static Map<String, Integer> preguntas;
    private static List<Respuesta> respuestas;
    private static List<String> listaPreguntas;
    private static List<Map.Entry<String, Integer>> preguntasOrdenadas;
    private static final String ARCHIVO_PREGUNTAS = "src/main/resources/archivos/preguntas.txt";
    private static final String ARCHIVO_RESPUESTAS = "src/main/resources/archivos/respuestas.txt";

    
    public static void cargarPreguntas(){
        listaPreguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PREGUNTAS))) {
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
    }

    public static void cargarRespuestas() {
        respuestas = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_RESPUESTAS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");
                Respuesta animal = new Respuesta(partes[0]);
                List<String> respuestasAnimal = Arrays.asList(partes[1].split(","));            
                for(int c=0; c<respuestasAnimal.size();c++){
                    animal.setRespuesta(listaPreguntas.get(c), respuestasAnimal.get(c));
                }
                respuestas.add(animal);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Tamaño lista respuestas -> "+respuestas.size());
        System.out.println(respuestas.toString());
        
    }
    
    public static void PreguntasOrdenadas() {
        preguntas = new HashMap<>();
        
        for (Respuesta respuesta : respuestas) {
            for(String p: listaPreguntas){
                if(respuesta.getRespuestas().getOrDefault(p,"no").equals("si")){
                    preguntas.put(p, preguntas.getOrDefault(p, 0)+1);
                }
            }
        }
        preguntasOrdenadas = new ArrayList<>(preguntas.entrySet());
        preguntasOrdenadas.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        System.out.println("Tamaño lista preguntas Ordenadas -> "+preguntasOrdenadas.size());
        System.out.println(preguntasOrdenadas.toString());
    }

    public static Arbol construirArbol() {
        Nodo raiz = new Nodo(preguntasOrdenadas.get(0).getKey());
        String[] respuestasPorAnimal;
        int c;
        
        for(Respuesta r: respuestas){
            respuestasPorAnimal = new String[r.getRespuestas().size()];
            c = 0;
            for(Map.Entry<String, Integer> entry : preguntasOrdenadas){
                respuestasPorAnimal[c] = r.getRespuestas().get(entry.getKey());
                c++;
            }
            agregarNodo(raiz, respuestasPorAnimal, 0, r.getAnimal());
        }
        return new Arbol(raiz);
    }

    private static void agregarNodo(Nodo nodo, String[] respuestas, int indice, String animal) {
        if (indice >= respuestas.length) {
            nodo.animal = animal;
            return;
        }

        String respuesta = respuestas[indice];
        if (respuesta.equals("si")) {
            if (nodo.si == null) {
                nodo.si = new Nodo( (indice + 1 < preguntasOrdenadas.size() ) ? 
                        ( preguntasOrdenadas.get(indice + 1).getKey() ) : null);
            }
            agregarNodo(nodo.si, respuestas, indice + 1, animal);
        } else if (respuesta.equals("no")) {
            if (nodo.no == null) {
                nodo.no = new Nodo( (indice + 1 < preguntasOrdenadas.size() ) ? 
                        ( preguntasOrdenadas.get(indice + 1).getKey() ) : null);
            }
            agregarNodo(nodo.no, respuestas, indice + 1, animal);
        }
    }
    
    
    public static void reducirAnimales(Arbol arbol, String respuesta, String pregunta){
        List<Respuesta> temp = new ArrayList<>();
        listaPreguntas.remove(pregunta);
        
        System.out.println("RESPUESTAS ANTES -> "+respuestas);
        for(Respuesta r: respuestas){

            if((r.getRespuestas().get(pregunta).equals(respuesta))){
                r.borrarRespuesta(pregunta);
                temp.add(r);
            }
        }
        respuestas = temp;
        System.out.println("RESPUESTAS DESPUES -> "+respuestas);
        
    }
    
    public static boolean lanzarRespuesta(){
        return respuestas.size()==1;
    }
    
    

 
}
