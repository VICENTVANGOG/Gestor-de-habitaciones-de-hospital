package hospital.controlador;

import hospital.gestion.GestorHospital;
import hospital.modelos.*;
import java.util.List;

/** Recibe las acciones de la vista y las envía al gestor. */
public class HospitalController {
    private final GestorHospital gestor = new GestorHospital();

    // Registra un paciente con los datos recibidos del formulario.
    public void registrarPaciente(String id, String nombre, int edad, int triage, String motivo) {
        gestor.registrarPaciente(new Paciente(id, nombre, edad, triage, motivo));
    }

    // Asigna una habitación o registra la salida del paciente.
    public void asignarHabitacion(String id, int numero) { gestor.asignarHabitacion(id, numero); }
    public void darSalida(String id) { gestor.darSalida(id); }

    // Consultas que utilizan las páginas de la interfaz.
    public List<Paciente> pacientes() { return gestor.obtenerPacientes(); }
    public List<Habitacion> habitaciones() { return gestor.obtenerHabitaciones(); }
    public List<RegistroHistorial> historial() { return gestor.obtenerHistorial(); }
    public String matriz() { return gestor.obtenerMatrizTexto(); }
}
