package hospital.estructuras;

import hospital.modelos.Paciente;
import hospital.modelos.RegistroHistorial;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstructurasTest {
    @Test void listaPacientesAgregaBuscaYRechazaDuplicados() {
        ListaPacientes list = new ListaPacientes();
        Paciente p = new Paciente("1001", "Ana", 25, 2, "Dolor");
        list.agregar(p);
        assertTrue(list.existe("1001"));
        assertSame(p, list.buscar("1001"));
        assertEquals(1, list.obtenerTodos().size());
        assertThrows(IllegalArgumentException.class, () -> list.agregar(p));
    }
    @Test void listaHistorialConservaOrdenDeMovimientos() {
        ListaHistorial list = new ListaHistorial();
        Paciente p = new Paciente("1001", "Ana", 25, 2, "Dolor");
        list.agregar(new RegistroHistorial(8, p, "INGRESO"));
        list.agregar(new RegistroHistorial(8, p, "SALIDA"));
        assertEquals(2, list.obtenerTodos().size());
        assertEquals("INGRESO", list.obtenerTodos().get(0).getAccion());
        assertEquals("SALIDA", list.obtenerTodos().get(1).getAccion());
    }
    @Test void matrizDispersaRegistraYOcultaTripletaAlLiberar() {
        MatrizDispersa matrix = new MatrizDispersa(5, 6);
        assertFalse(matrix.estaOcupada(2, 2));
        matrix.ocupar(2, 2);
        assertTrue(matrix.estaOcupada(2, 2));
        assertThrows(IllegalStateException.class, () -> matrix.ocupar(2, 2));
        matrix.liberar(2, 2);
        assertFalse(matrix.estaOcupada(2, 2));
        assertThrows(IllegalStateException.class, () -> matrix.liberar(2, 2));
    }
}
