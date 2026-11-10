package hospital.estructuras;

import hospital.modelos.RegistroHistorial;

/** Nodo que guarda un registro y apunta al siguiente. */
public class NodoHistorial {
    private final RegistroHistorial dato;
    private NodoHistorial siguiente;

    public NodoHistorial(RegistroHistorial dato) {
        this.dato = dato;
    }

    public RegistroHistorial getDato() { return dato; }
    public NodoHistorial getSiguiente() { return siguiente; }
    public void setSiguiente(NodoHistorial siguiente) { this.siguiente = siguiente; }
}
