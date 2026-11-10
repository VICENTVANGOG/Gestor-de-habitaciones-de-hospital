package hospital.modelos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {
    @Test void aceptaDatosValidosYNormalizaEspacios() {
        Paciente p = new Paciente(" 1001 ", " Ana Pérez ", 25, 2, " Observación ");
        assertEquals("1001", p.getIdentificacion());
        assertEquals("Ana Pérez", p.getNombre());
        assertEquals("Observación", p.getMotivoIngreso());
    }
    @Test void rechazaIdentificacionVacia() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente(" ", "Ana", 25, 2, "Dolor"));
    }
    @Test void rechazaNombreOMotivoVacios() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente("1", "", 25, 2, "Dolor"));
        assertThrows(IllegalArgumentException.class, () -> new Paciente("1", "Ana", 25, 2, " "));
    }
    @Test void validaEdadYTriage() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente("1", "Ana", -1, 2, "Dolor"));
        assertThrows(IllegalArgumentException.class, () -> new Paciente("1", "Ana", 131, 2, "Dolor"));
        assertThrows(IllegalArgumentException.class, () -> new Paciente("1", "Ana", 25, 0, "Dolor"));
        assertThrows(IllegalArgumentException.class, () -> new Paciente("1", "Ana", 25, 6, "Dolor"));
    }
}
