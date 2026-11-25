package hospital.modelos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorPacienteTest {
    @Test void triajeSoloAceptaDelUnoAlCinco() {
        for (int i = 1; i <= 5; i++) assertNull(ValidadorPaciente.validarTriaje(String.valueOf(i)));
        assertEquals(ValidadorPaciente.TRIAJE_INCORRECTO, ValidadorPaciente.revisarTriaje("6"));
        assertEquals(ValidadorPaciente.TRIAJE_INCORRECTO, ValidadorPaciente.revisarTriaje("0"));
        assertEquals(ValidadorPaciente.TRIAJE_INCORRECTO, ValidadorPaciente.revisarTriaje("a"));
        assertEquals(ValidadorPaciente.TRIAJE_INCORRECTO, ValidadorPaciente.revisarTriaje("12"));
        assertNotNull(ValidadorPaciente.validarTriaje(""));
    }

    @Test void edadSoloAceptaNumerosHasta130() {
        assertNull(ValidadorPaciente.validarEdad("0"));
        assertNull(ValidadorPaciente.validarEdad("130"));
        assertNotNull(ValidadorPaciente.revisarEdad("2a"));
        assertNotNull(ValidadorPaciente.revisarEdad("131"));
        assertNotNull(ValidadorPaciente.validarEdad(""));
    }

    @Test void identificacionSoloAceptaNumeros() {
        assertNull(ValidadorPaciente.validarIdentificacion("1001234567"));
        assertNotNull(ValidadorPaciente.revisarIdentificacion("10A"));
        assertNotNull(ValidadorPaciente.validarIdentificacion("123"));
        assertNotNull(ValidadorPaciente.revisarIdentificacion("1234567890123"));
    }

    @Test void nombreSoloAceptaLetras() {
        assertNull(ValidadorPaciente.validarNombre("Ana María Pérez"));
        assertNotNull(ValidadorPaciente.revisarNombre("Ana2"));
        assertNotNull(ValidadorPaciente.revisarNombre("Ana@"));
        assertNotNull(ValidadorPaciente.validarNombre("  "));
    }

    @Test void motivoEsObligatorioYLimitado() {
        assertNull(ValidadorPaciente.validarMotivo("Dolor abdominal"));
        assertNotNull(ValidadorPaciente.validarMotivo(""));
        assertNotNull(ValidadorPaciente.revisarMotivo("x".repeat(121)));
    }
}
