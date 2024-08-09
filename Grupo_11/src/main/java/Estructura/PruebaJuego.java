package Estructura;

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

        Arbol arbol = ArbolBuilder.inicializarArbol();
        nodoActual = arbol.getRaiz();

        int preguntasMaximas = 20; // Por defecto, el número máximo de preguntas
        int preguntasActuales = 0;
        System.out.print("Ingrese el número de preguntas (por defecto 20): ");
        
        if (scanner.hasNextInt()) {
            preguntasMaximas = scanner.nextInt();
            scanner.nextLine();
        }

        //Se detiene cuando se alcance el maximo de preguntas o cuando 
        //solo alla un animal posible en el arbol
        while( preguntasActuales<preguntasMaximas && ArbolBuilder.cantidadRespuestasPosibles()!=0 ){
            
            //Arroja la prediccion si ya solo hay un animal en el arbol
            if(ArbolBuilder.cantidadRespuestasPosibles()==1){
                System.out.println("¡El animal es: " + arbol.obtenerAnimalesPosibles(nodoActual).get(0) + "!");
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

            //Se lanzara un error unicamente cuando no haya un animal que cuente
            //con las caracteristicas del usuario terminando con las preguntas
            try{
            arbol = ArbolBuilder.ReducirArbol(respuestaUser, preguntaActual);
            nodoActual = arbol.getRaiz();
            preguntasActuales++;

            }catch(Exception e){
                System.out.println("Cantidad de Animales Posibles -> "+ArbolBuilder.cantidadRespuestasPosibles());
                System.out.println("Lo siento pero no conozco un animal con esas caracteristicas");
                System.out.println("!GANASTE!");
            }
        }
        
        if (ArbolBuilder.cantidadRespuestasPosibles()>0 && preguntasActuales == preguntasMaximas) {
            System.out.println("No se pudo determinar el animal con las preguntas realizadas.");
            List<String> animalesPosibles = arbol.obtenerAnimalesPosibles(nodoActual);
            System.out.println("Animales posibles: " + String.join(", ", animalesPosibles));
        }
        scanner.close();
    }

    public static String getRespuestaUser(Nodo nodoActual, int numPregunta){
        System.out.println("\nPregunta "+ (numPregunta+1) );
        System.out.print("¿" + nodoActual.pregunta+" (si/no): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();

        return respuesta;
    }
}
