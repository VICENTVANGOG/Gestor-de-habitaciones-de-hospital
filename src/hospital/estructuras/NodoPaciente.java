package hospital.estructuras;

import hospital.modelos.Paciente;

/** Nodo que guarda un paciente y apunta al siguiente. */
public class NodoPaciente {
    private final Paciente dato;
    private NodoPaciente siguiente;

    public NodoPaciente(Paciente dato) {
        this.dato = dato;
    }

    public Paciente getDato() { return dato; }
    public NodoPaciente getSiguiente() { return siguiente; }
    public void setSiguiente(NodoPaciente siguiente) { this.siguiente = siguiente; }
}
