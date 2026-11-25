package hospital.estructuras;

import hospital.modelos.Paciente;
import java.util.ArrayList;
import java.util.List;

/** Lista simplemente enlazada propia para almacenar pacientes. */
public class ListaPacientes {
    private NodoPaciente cabeza;

    // Revisa si ya existe un paciente con la misma identificación.
    public boolean existe(String id) {
        for (NodoPaciente actual = cabeza; actual != null; actual = actual.getSiguiente()) {
            if (actual.getDato().getIdentificacion().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Agrega un paciente al final y evita identificaciones repetidas.
    public void agregar(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }
        if (existe(paciente.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un paciente con esa identificación.");
        }

        NodoPaciente nuevo = new NodoPaciente(paciente);
        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }

        NodoPaciente actual = cabeza;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        actual.setSiguiente(nuevo);
    }

    // Busca un paciente por su identificación.
    public Paciente buscar(String id) {
        for (NodoPaciente actual = cabeza; actual != null; actual = actual.getSiguiente()) {
            if (actual.getDato().getIdentificacion().equals(id)) {
                return actual.getDato();
            }
        }
        return null;
    }

    // Elimina el paciente con la identificación indicada; devuelve true si lo encontró.
    public boolean eliminar(String id) {
        if (cabeza == null) {
            return false;
        }
        if (cabeza.getDato().getIdentificacion().equals(id)) {
            cabeza = cabeza.getSiguiente();
            return true;
        }
        for (NodoPaciente actual = cabeza; actual.getSiguiente() != null; actual = actual.getSiguiente()) {
            if (actual.getSiguiente().getDato().getIdentificacion().equals(id)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                return true;
            }
        }
        return false;
    }

    // Copia los pacientes a una lista para mostrarlos en la interfaz.
    public List<Paciente> obtenerTodos() {
        List<Paciente> resultado = new ArrayList<>();
        for (NodoPaciente actual = cabeza; actual != null; actual = actual.getSiguiente()) {
            resultado.add(actual.getDato());
        }
        return resultado;
    }
}
