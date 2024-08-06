package Estructura;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author vv
 */
public class PruebaJuego {
    
    static ArbolBinario arbol = new ArbolBinario();
    static List<String> hojas = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static Integer preguntasMax;
    static Nodo nodoActual;
    
    public static void main(String[] args) {

        while(true){
            int contadorPreguntas = 0;
            Integer respuesta = -1;
            
            //Leer datos del archivo .txt
            Persistencia.cargarDatos(arbol);
            nodoActual = arbol.getRaiz();

            //Arbol de animales para esta ronda
            arbol.imprimirArbol();
        
            System.out.println("Ingrese el maximo de preguntas (MAX: 20): ");
            preguntasMax = sc.nextInt();
            sc.nextLine();

            while(contadorPreguntas<preguntasMax){
                //Mientras sea pregunta
                if(!nodoActual.esHoja){
                    contadorPreguntas++;
                    System.out.println("Pregunta "+contadorPreguntas);
                    System.out.println(nodoActual.contenido);
                    System.out.println("0. No");
                    System.out.println("1. Si");
                    respuesta = sc.nextInt();
                    sc.nextLine();
                    
                    //Si el siguente nodo es diferente de null sigue sino se
                    // rompe el while
                    if(arbol.getNextNodo(nodoActual, respuesta)==null){
                        break;
                    }
                    nodoActual = arbol.getNextNodo(nodoActual, respuesta);
                }
                else if(nodoActual.esHoja){
                    //Lanza prediccion e intenta verificar si es correcta
                    System.out.println("Pensaste en un: "+nodoActual.contenido);
                    System.out.println("0. No");
                    System.out.println("1. Si");
                    int respAcierto = sc.nextInt();
                    sc.nextLine();
                    
                    //De no ser correcta agrega el animal al archivo .txt
                    if(respAcierto==0){
                        String AnimalNuevo = "";
                        String preguntaNueva = "";
                        
                        System.out.println("Que animal pensaste");
                        AnimalNuevo = sc.nextLine();
                        
                        //Realiza la pregunta que filtre entre ambos animales
                        System.out.println("En que se diferencia el"+AnimalNuevo+" de un "+nodoActual.contenido);
                        preguntaNueva = sc.nextLine();
                        
                        Nodo nodoPregunta = arbol.addPregunta(preguntaNueva, nodoActual, respuesta);
                        arbol.addAnimal(AnimalNuevo, nodoPregunta, respuesta);
                        System.out.println("Gracias hemos actualizado nuestra base de datos.");
                    }
                    break;
                }
            }
            if(preguntasMax==contadorPreguntas){
                System.out.println("No he podido identificar el animal, estas era las posibles opciones");
                arbol.getHojas(nodoActual, hojas);
                System.out.println(hojas);
            }
            
            Persistencia.guardarDatos(arbol);
            hojas.clear();
            arbol.clear();
        }
    }
}




//               else if(arbol.getNextNodo(nodoActual, respuesta)==null){
//                //En caso que se haya salido del while porque no hubo una prediccion
//                //osea el nodo que seguia era null, se ingresa el animal pensado
//                //por el usuario
//                System.out.println("No tengo registro de un animal con esas caracteristicas");
//                 String AnimalNuevo = "";
//                
//                System.out.println("Que animal pensaste");
//                AnimalNuevo = sc.nextLine();
//                arbol.addAnimal(AnimalNuevo, nodoActual, respuesta);
//                break;
//                }