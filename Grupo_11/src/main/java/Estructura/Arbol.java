package Estructura;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victoria
 */
public class Arbol {
    protected Nodo raiz;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }
    
    public Nodo getRaiz(){
        return raiz;
    }

    public List<String> obtenerAnimalesPosibles(Nodo nodo) {
        List<String> animales = new ArrayList<>();
        if (nodo == null) {
            return animales;
        }
        if (nodo.animal != null) {
            animales.add(nodo.animal);
        } else {
            if (nodo.si != null) {
                animales.addAll(obtenerAnimalesPosibles(nodo.si));
            }
            if (nodo.no != null) {
                animales.addAll(obtenerAnimalesPosibles(nodo.no));
            }
        }
        return animales;
    }   
}
