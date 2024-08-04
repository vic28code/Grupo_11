/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructura;

import java.util.ArrayList;
import java.util.List;

/**
 * Correr solo este archivo para ver el funcionamiento de los metodos del
 * Arbol Binario
 * @author vv
 */
public class PruebasArbolBinario {
    
     public static void main(String[] args) {
        
        ArbolBinario arbol = new ArbolBinario();
        List<String> hojas = new ArrayList<>();
      
        //Leer datos del archivo .txt
        Persistencia.cargarDatos(arbol);
        
        Nodo nodoActual = arbol.getRaiz(); //Nodo Actual = raiz
        
        //Imprime los datos guardados del arbol(indice, elemento)
        arbol.imprimirArbol();
        
        //Lista de todas las hojas actualmente del arbol desde la raiz
        arbol.getHojas(arbol.getRaiz(),hojas);
        System.out.println(hojas);
        
        //Cambio del nodoActual(raiz) al nodoNo de la raiz (En este caso Tiburon)
        //Cuando el usuario vaya respondiendo las preguntas el nodo actual se ira
        //actualizando segun las opciones que coja
        nodoActual = arbol.getNextNodo(nodoActual, 0);
        
        //AQUI SE LLAMA A GETKEY() Y OCURRE EL ERROR
        //Agregar pregunta "Tiene aletas?" en la posicion de tiburon y mover a tiburon
        arbol.addPregunta("Tiene aletas?", nodoActual, 0);
        
        //Imprimir el Arbol actual
        arbol.imprimirArbol();
        
    }
    
}
