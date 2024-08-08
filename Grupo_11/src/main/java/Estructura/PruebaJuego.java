/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructura;

import Estructura.Arbol;
import Estructura.ArbolBuilder;
import Estructura.Nodo;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class PruebaJuego {
    static Scanner scanner = new Scanner(System.in);
    static String preguntaActual;
    static Nodo nodoActual;

    public static void main(String[] args){

        ArbolBuilder.cargarPreguntas();
        ArbolBuilder.cargarRespuestas();
        ArbolBuilder.PreguntasOrdenadas();
        Arbol arbol = ArbolBuilder.construirArbol();
        nodoActual = arbol.getRaiz();

        int preguntasMaximas = 20; // Por defecto, el número máximo de preguntas
        int preguntasActuales = 0;

        System.out.print("Ingrese el número de preguntas (por defecto 20): ");
        if (scanner.hasNextInt()) {
            preguntasMaximas = scanner.nextInt();
            scanner.nextLine();
        }

        while (nodoActual != null && preguntasActuales < preguntasMaximas) {
            if (nodoActual.pregunta == null) {
                break;
            }

            if(ArbolBuilder.lanzarRespuesta()){
                System.out.println("¡El animal es: " + nodoActual.animal + "!");
                break;
            }

            preguntaActual = nodoActual.pregunta;
            String respuestaUser = getRespuestaUser(nodoActual, preguntasActuales);

            if (respuestaUser.equals("si")) {
                nodoActual = nodoActual.si;
            } 
            else if (respuestaUser.equals("no")) {
                nodoActual = nodoActual.no;
            }

            try{
            ArbolBuilder.reducirAnimales(arbol, respuestaUser, preguntaActual);
            ArbolBuilder.PreguntasOrdenadas();
            arbol = ArbolBuilder.construirArbol();
            preguntasActuales++;


            }catch(Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        if (nodoActual != null && nodoActual.animal != null) {
            System.out.println("¡El animal es: " + nodoActual.animal + "!");
        } else {
            System.out.println("No se pudo determinar el animal con las preguntas realizadas.");
            List<String> animalesPosibles = arbol.obtenerAnimalesPosibles(nodoActual);
            System.out.println("Animales posibles: " + String.join(", ", animalesPosibles));
        }

        scanner.close();

        arbol.obtenerAnimalesPosibles(nodoActual);
        }

    public static String getRespuestaUser(Nodo nodoActual, int numPregunta){
        System.out.println("Pregunta "+ (numPregunta+1) );
        System.out.print("¿"+nodoActual.pregunta);
        System.out.println("(si/no): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();


        //        while(!respuesta.equals("si")|| !respuesta.equals("no")){
        //            System.out.print("Ingrese una respuesta valida(si/no): ");
        //            respuesta = scanner.nextLine().trim().toLowerCase();
        //        }
        return respuesta;
    }
}
