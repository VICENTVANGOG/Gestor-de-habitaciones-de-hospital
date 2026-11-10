package hospital.estructuras;

import hospital.modelos.RegistroHistorial;
import java.util.ArrayList;
import java.util.List;

/** Lista simplemente enlazada que guarda los movimientos del hospital. */
public class ListaHistorial {
    private NodoHistorial cabeza;

    // Agrega un registro al final de la lista.
    public void agregar(RegistroHistorial registro) {
        NodoHistorial nuevo = new NodoHistorial(registro);

        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }

        NodoHistorial actual = cabeza;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        actual.setSiguiente(nuevo);
    }

    // Devuelve los registros en el mismo orden en que fueron guardados.
    public List<RegistroHistorial> obtenerTodos() {
        List<RegistroHistorial> resultado = new ArrayList<>();
        for (NodoHistorial actual = cabeza; actual != null; actual = actual.getSiguiente()) {
            resultado.add(actual.getDato());
        }
        return resultado;
    }
}
