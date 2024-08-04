/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructura;

/**
 *
 * @author vv
 */
public class Nodo {

    protected String contenido;
    protected Nodo nodoNo, nodoSi;
    protected boolean esHoja;

    public Nodo(String e) {
        contenido = e;
        nodoNo = null;
        nodoSi = null;
        esHoja = true;
    }

    public void addNodoSi(Nodo nuevoNodo) {
        this.esHoja = false;
        this.nodoSi = nuevoNodo;
    }

    public void addNodoNo(Nodo nuevoNodo) {
        this.esHoja = false;
        this.nodoNo = nuevoNodo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Nodo nodo = (Nodo) obj;
        return contenido.equals(nodo.contenido);
    }

    @Override
    public int hashCode() {
        return contenido.hashCode();
    }

    @Override
    public String toString() {
        return contenido;
    }

}
