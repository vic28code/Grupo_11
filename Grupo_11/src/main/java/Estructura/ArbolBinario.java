/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructura;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Victoria
 */
public class ArbolBinario {
    private Nodo raiz;
    private Map<Integer,Nodo> listaNodos;
    
    public ArbolBinario(){
        clear();
    }
    
    public void clear(){
        this.raiz = null;
        this.listaNodos = new HashMap<>();
    }
    
    public boolean isEmpty(){
        return raiz==null;
    }
    
    public Nodo getRaiz(){
        return raiz;
    }
    
    public void setRaiz(Nodo nuevoNodo) {
        raiz = nuevoNodo;
    }
    
    public Map<Integer,Nodo> getListaNodos(){
        return listaNodos;
    }

    // Retorna una lista con todos las hojas(Animales) a partir de un nodo dado
    public void getHojas(Nodo n, List hojas){
        if (n.esHoja) {
            hojas.add(n.contenido);
        }else{
            if(n.nodoNo != null)
                getHojas(n.nodoNo,hojas);
            if(n.nodoSi != null)
                getHojas(n.nodoSi,hojas);
        }
    }
    
    //El nodo actual cambia dependiendo de cual seala resupesta del usuario Si(1) o No(0)
    public Nodo getNextNodo(Nodo nodo, int opcion){
        return (opcion==1) ? nodo.nodoSi: nodo.nodoNo;
    }
    
    private Integer getKey(Nodo valor){
        if(valor == null) {return null;}
        for (Map.Entry<Integer,Nodo> elemento : listaNodos.entrySet()) {
            if (elemento.getValue().equals(valor)) {
                return elemento.getKey();
            }
        }
        return null;
    }
    
    //Añade una pregunta al arbol desplazando al nodoActual(Animal) a uno
    //de los nodo Si o No dependiendo la ultima respuesta del usuario
    public Nodo addPregunta(String pregunta, Nodo nodoActual, int opcion ) {
        int indNodoActual = getKey(nodoActual);
        Nodo nodoPregunta = new Nodo(pregunta+"?");
        int indNodoNuevo;
        
        if(opcion ==1){
            nodoPregunta.addNodoNo(nodoActual);
            indNodoNuevo = (indNodoActual*2);
        }else{
             nodoPregunta.addNodoSi(nodoActual);
            indNodoNuevo = (indNodoActual*2)+1;
        }
        
        listaNodos.put(indNodoActual, nodoPregunta);
        listaNodos.put(indNodoNuevo, nodoActual);
        return nodoPregunta;
    }
    
    //Añade un animal al en uno de los nodos Si o No de un nodo pregunta
    //En este caso no desplaza a ningun otro nodo, toma lugar donde habia un null
    public Nodo addAnimal(String animal, Nodo nodoPregunta, int opcion ) {
        int indNodoPregunta = getKey(nodoPregunta), indNodoAnimal;
        Nodo nodoAnimal = new Nodo(animal);

        if(opcion ==1){
            nodoPregunta.addNodoSi(nodoAnimal);
            indNodoAnimal = (indNodoPregunta*2)+1;
        }else{
            nodoPregunta.addNodoNo(nodoAnimal);
            indNodoAnimal = (indNodoPregunta*2);
        }
        
        listaNodos.put(indNodoAnimal, nodoAnimal);
        return nodoAnimal;
    }
    
    public void imprimirArbol(){
        listaNodos.forEach((x,y)->System.out.println("ind: "+x+" - nodo: "+y));
    }
    
}
