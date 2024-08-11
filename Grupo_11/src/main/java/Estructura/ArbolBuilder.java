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
    
    private static final String ARCHIVO_PREGUNTAS = "src/main/resources/archivos/preguntas.txt";
    private static final String ARCHIVO_RESPUESTAS = "src/main/resources/archivos/respuestas.txt";
    
    public static List<Respuesta> respuestas;
    public static List<String> listaPreguntas;
    public static Map<String, Integer> preguntasContadas;
    public static List<Map.Entry<String, Integer>> preguntasOrdenadas;
    
    
    public static int cantidadRespuestasPosibles(){
        return respuestas.size();
    }
    
    //Se usa solo al inicio para cargar los datos de los archivos de texto
    public static Arbol inicializarArbol(){
        ArbolBuilder.cargarPreguntas();
        ArbolBuilder.cargarRespuestas();
        ArbolBuilder.ordenasPreguntas();
        return ArbolBuilder.construirArbol();
    }
    
    //Se usa cada vez que se hace una pregunta para reducir los animales posibles
    //y las preguntas que no tengan nigun si como respuesta
    public static Arbol ReducirArbol(String respuestaUser, String preguntaActual){
        ArbolBuilder.reducirAnimales(respuestaUser, preguntaActual);
        ArbolBuilder.ordenasPreguntas();
        return ArbolBuilder.construirArbol();
    }
    
    
    private static void cargarPreguntas(){
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

    
    private static void cargarRespuestas() {
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
    
    //Cuenta la cantidad de "si" que tiene cada pregunta entre los animales actuales
    private static void ordenasPreguntas() {
        preguntasContadas = new HashMap<>();
        
        for (Respuesta respuesta : respuestas) {
            for(String p: listaPreguntas){
                //Se agregan solo preguntas que tiene al menos un Si en algun animal
                if(respuesta.getRespuestas().get(p).equals("si")){
                    preguntasContadas.put(p, preguntasContadas.getOrDefault(p, 0)+1);
                }
            }
        }
        preguntasOrdenadas = new ArrayList<>(preguntasContadas.entrySet());
        preguntasOrdenadas.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        System.out.println("Tamaño lista preguntas Ordenadas -> "+preguntasOrdenadas.size());
        System.out.println(preguntasOrdenadas.toString());
    }

    private static Arbol construirArbol() {
        Nodo raiz = new Nodo(preguntasOrdenadas.get(0).getKey());
        String[] respuestasPorAnimal;
        int c;
        
        for(Respuesta r: respuestas){
            respuestasPorAnimal = new String[preguntasOrdenadas.size()];
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
        }if (respuesta.equals("no")) {
            if (nodo.no == null) {
                nodo.no = new Nodo( (indice + 1 < preguntasOrdenadas.size() ) ? 
                        ( preguntasOrdenadas.get(indice + 1).getKey() ) : null);
            }
            agregarNodo(nodo.no, respuestas, indice + 1, animal);
        }
    }
    
    
    private static void reducirAnimales(String respuesta, String pregunta){
        List<Respuesta> temp = new ArrayList<>();
        listaPreguntas.remove(pregunta);//Se borra la pregunta ya lanzada del banco de preguntas

        for(Respuesta r: respuestas){
            //Añade solo los animales que coincidan con la respuesta del usuario 
            if((r.getRespuestas().get(pregunta).equals(respuesta))){
                r.borrarRespuesta(pregunta);//borra la pregunta ya lanzada de las respuestas de cada animal
                temp.add(r);
            }
        }
        respuestas = temp;//se actualizan los elementos de la lista respuestas
        System.out.println("Posibles respuestas: -> "+respuestas.size());
        System.out.println("RESPUESTAS -> "+respuestas);
    }
}
