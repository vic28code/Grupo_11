/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructura;

/**
 *
 * @author Victoria
 */
public class Nodo {
    public String pregunta;
    protected Nodo si;
    protected Nodo no;
    protected String animal;

    public Nodo(String pregunta) {
        this.pregunta = pregunta;
    }

    public Nodo(String pregunta, String animal) {
        this.pregunta = pregunta;
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "Nodo{" + "pregunta=" + pregunta + ", si=" + si + ", no=" + no + ", animal=" + animal + '}';
    }
}