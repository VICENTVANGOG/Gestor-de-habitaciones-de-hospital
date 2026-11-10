package hospital.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroHistorial {
    private final int numeroHabitacion;
    private final String identificacionPaciente;
    private final String nombrePaciente;
    private final String accion;
    private final LocalDateTime fechaHora;

    public RegistroHistorial(int numeroHabitacion, Paciente paciente, String accion) {
        this.numeroHabitacion = numeroHabitacion;
        this.identificacionPaciente = paciente.getIdentificacion();
        this.nombrePaciente = paciente.getNombre();
        this.accion = accion;
        this.fechaHora = LocalDateTime.now();
    }
    public int getNumeroHabitacion() { return numeroHabitacion; }
    public String getIdentificacionPaciente() { return identificacionPaciente; }
    public String getAccion() { return accion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    @Override public String toString() {
        return fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
               " | " + accion + " | Habitación " + numeroHabitacion +
               " | " + nombrePaciente + " (" + identificacionPaciente + ")";
    }
}
