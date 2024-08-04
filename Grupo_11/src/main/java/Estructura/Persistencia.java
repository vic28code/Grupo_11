/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author vv
 */
public class Persistencia {
    
    private static final String RUTA_ARCHIVOS = "src/main/java/Estructura/";
    
    public static void cargarDatos(ArbolBinario arbol){
        
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVOS+"Info.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                int ind = Integer.parseInt(datos[0]) , indNodoAnterior;
                Nodo nuevoNodo = new Nodo(datos[1]);
                arbol.getListaNodos().put(ind, nuevoNodo);
                
                //Caso especial con el primer indice
                if(ind==1){
                    arbol.setRaiz(nuevoNodo);
                }
                //Indices impar -> Opcion Si
                else if(ind%2!=0){
                    indNodoAnterior = (ind-1)/2;
                    arbol.getListaNodos().get(indNodoAnterior).addNodoSi(nuevoNodo);
                }
                //Indices par -> Opcion No
                else{
                    indNodoAnterior = (ind)/2;
                    arbol.getListaNodos().get(indNodoAnterior).addNodoNo(nuevoNodo);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    public static void guardarDatos(ArbolBinario arbol){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVOS+"Info.txt"))) {
            String linea;
            for (Map.Entry<Integer,Nodo> elemento : arbol.getListaNodos().entrySet()) {
                linea = elemento.getKey()+","+elemento.getValue(); 
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
}
