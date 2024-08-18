package Estructura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vv
 */
public class ArbolBuilder {
    
    public static List<Respuesta> respuestas;
    public static List<String> listaPreguntas;
    public static Map<String, Integer> preguntasContadas;
    public static List<Map.Entry<String, Integer>> preguntasOrdenadas;
    
    
    public static int cantidadRespuestasPosibles(){
        return respuestas.size();
    }
    
    //Se usa solo al inicio para cargar los datos de los archivos de texto
    //Crea el arbol con los archivos de texto existentes en el programa
    public static Arbol inicializarArbol(String nombreFilePreguntas, String nombreFileRespuestas){
        ArbolBuilder.cargarPreguntas(nombreFilePreguntas);
        ArbolBuilder.cargarRespuestas(nombreFileRespuestas);
        ArbolBuilder.ordenasPreguntas();
        return ArbolBuilder.construirArbol();
    }
    
    //Se usa solo al inicio para cargar los datos de los archivos de texto
    //Crea el arbol a partir de los archivos que suba el usuario
    public static Arbol inicializarArbolExterno(){
        ArbolBuilder.ordenasPreguntas();
        return ArbolBuilder.construirArbol();
    }
    
    //Se usa cada vez que se hace una pregunta para reducir los animales posibles
    //y las preguntas que no tengan nigun si como respuesta
    public static Arbol ReducirArbol(String respuestaUser, String preguntaActual){
        reducirAnimales(respuestaUser, preguntaActual);
        ordenasPreguntas();
        if (respuestas.isEmpty()) {
            return new Arbol(null);  // O maneja esto según tus necesidades
        }
        
        return construirArbol();
    }
    
    
    public static void cargarPreguntasExternas() throws Exception{
        listaPreguntas = new ArrayList<>(); 
        listaPreguntas = Persistencia.cargarPreguntasExternas();
        System.out.println("Tamaño lista preguntas -> "+listaPreguntas.size());
    }
    public static void cargarRespuestasExternas() throws Exception{
        respuestas = new ArrayList<>();
        respuestas = Persistencia.cargarRespuestasExternas(listaPreguntas);
        System.out.println("Tamaño lista respuestas -> "+respuestas.size());
    }
    
    
    private static void cargarPreguntas(String nombreArchivo){
        listaPreguntas = new ArrayList<>(); 
        listaPreguntas = Persistencia.cargarPreguntas(nombreArchivo);
        System.out.println("Tamaño lista preguntas -> "+listaPreguntas.size());
    }
    private static void cargarRespuestas(String nombreArchivo) {
        respuestas = new ArrayList<>();
        respuestas = Persistencia.cargarRespuestas(nombreArchivo, listaPreguntas);
        System.out.println("Tamaño lista respuestas -> "+respuestas.size());
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
        if (preguntasOrdenadas.isEmpty()) {
            System.out.println("No hay preguntas disponibles para construir el árbol.");
            return new Arbol(null);  // O maneja esto de otra manera según tus necesidades
        }
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
