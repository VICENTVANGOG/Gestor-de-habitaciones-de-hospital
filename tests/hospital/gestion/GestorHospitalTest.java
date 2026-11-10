package hospital.gestion;

import hospital.modelos.Paciente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GestorHospitalTest {
    @Test void iniciaConTreintaHabitacionesDisponibles() {
        GestorHospital gestor = new GestorHospital();
        assertEquals(30, gestor.obtenerHabitaciones().size());
        assertTrue(gestor.obtenerHabitaciones().stream().noneMatch(h -> h.isOcupada()));
    }
    @Test void registraAsignaLiberaYGuardaHistorial() {
        GestorHospital gestor = new GestorHospital();
        gestor.registrarPaciente(new Paciente("1001", "Ana", 25, 2, "Dolor abdominal"));
        gestor.asignarHabitacion("1001", 8);
        var room = gestor.obtenerHabitaciones().get(7);
        assertTrue(room.isOcupada());
        assertEquals("Ana", room.getPaciente().getNombre());
        assertTrue(gestor.obtenerMatrizTexto().contains("(2, 2, 1)"));
        gestor.darSalida("1001");
        assertFalse(room.isOcupada());
        assertEquals(2, gestor.obtenerHistorial().size());
        assertEquals("INGRESO", gestor.obtenerHistorial().get(0).getAccion());
        assertEquals("SALIDA", gestor.obtenerHistorial().get(1).getAccion());
        assertFalse(gestor.obtenerMatrizTexto().contains("(2, 2, 1)"));
    }
    @Test void impideAsignarHabitacionOcupadaYSalidaRepetida() {
        GestorHospital gestor = new GestorHospital();
        gestor.registrarPaciente(new Paciente("1001", "Ana", 25, 2, "Dolor"));
        gestor.registrarPaciente(new Paciente("1002", "Luis", 31, 3, "Fiebre"));
        gestor.asignarHabitacion("1001", 8);
        assertThrows(IllegalStateException.class, () -> gestor.asignarHabitacion("1002", 8));
        gestor.darSalida("1001");
        assertThrows(IllegalStateException.class, () -> gestor.darSalida("1001"));
    }
}
