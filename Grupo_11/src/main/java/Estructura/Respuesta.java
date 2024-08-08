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
 * @author vv
 */
public class Respuesta {
    
    private String animal;
    private Map<String,String> respuestas;

    public Respuesta(String animal) {
        this.animal = animal;
        this.respuestas = new HashMap<>();
    }

    public String getAnimal() {
        return animal;
    }

    public Map<String, String> getRespuestas() {
        return respuestas;
    }

    public void setRespuesta(String pregunta, String respuesta) {
        this.respuestas.put(pregunta, respuesta);
    }
    
    public void borrarRespuesta(String pregunta){
        Map<String, String> nuevasRespuestas = new HashMap<>();
        for(Map.Entry<String, String> entry : respuestas.entrySet()){
            if(!entry.getKey().equals(pregunta)){
                nuevasRespuestas.put(entry.getKey(), entry.getValue());
            }
        }
        respuestas = nuevasRespuestas;
    }

    @Override
    public String toString() {
        return "Respuesta{" + "animal=" + animal + ", respuestas=" + respuestas.toString() + '}';
    }

    
    
}
