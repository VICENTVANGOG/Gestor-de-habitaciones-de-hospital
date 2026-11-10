package hospital.gestion;

import hospital.estructuras.*;
import hospital.modelos.*;
import java.util.ArrayList;
import java.util.List;

/** Servicio de dominio: aplica las reglas de negocio del hospital. No construye interfaces. */
public class GestorHospital {
    private final Habitacion[] habitaciones = new Habitacion[30];
    private final ListaPacientes pacientes = new ListaPacientes();
    private final ListaHistorial historial = new ListaHistorial();
    private final MatrizDispersa ocupacion = new MatrizDispersa(5, 6);

    public GestorHospital() {
        for (int i = 0; i < habitaciones.length; i++) habitaciones[i] = new Habitacion(i + 1);
    }
    public void registrarPaciente(Paciente paciente) { pacientes.agregar(paciente); }
    public Paciente buscarPaciente(String identificacion) { return pacientes.buscar(identificacion); }
    public List<Paciente> obtenerPacientes() { return pacientes.obtenerTodos(); }
    public List<Habitacion> obtenerHabitaciones() {
        List<Habitacion> resultado = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) resultado.add(habitacion);
        return resultado;
    }
    public List<RegistroHistorial> obtenerHistorial() { return historial.obtenerTodos(); }
    public String obtenerMatrizTexto() {
        StringBuilder texto = new StringBuilder("Matriz de ocupación (1 = ocupada, 0 = disponible)\n");
        for (int f = 1; f <= 5; f++) {
            for (int c = 1; c <= 6; c++) texto.append(ocupacion.estaOcupada(f,c) ? "1 " : "0 ");
            texto.append('\n');
        }
        texto.append("\nTripletas (fila, columna, valor):\n");
        for (int f = 1; f <= 5; f++) for (int c = 1; c <= 6; c++)
            if (ocupacion.estaOcupada(f,c)) texto.append("(").append(f).append(", ").append(c).append(", 1)\n");
        return texto.toString();
    }
    public void asignarHabitacion(String identificacion, int numero) {
        Paciente paciente = pacientes.buscar(identificacion);
        if (paciente == null) throw new IllegalArgumentException("No existe un paciente con esa identificación.");
        if (buscarHabitacionActiva(identificacion) != null) throw new IllegalStateException("El paciente ya tiene una habitación asignada.");
        Habitacion habitacion = obtenerHabitacion(numero);
        if (habitacion.isOcupada()) throw new IllegalStateException("La habitación ya está ocupada.");
        habitacion.ocupar(paciente);
        ocupacion.ocupar(habitacion.getPiso(), habitacion.getPosicion());
        historial.agregar(new RegistroHistorial(numero, paciente, "INGRESO"));
    }
    public void darSalida(String identificacion) {
        Habitacion habitacion = buscarHabitacionActiva(identificacion);
        if (habitacion == null) throw new IllegalStateException("El paciente no tiene una hospitalización activa.");
        Paciente paciente = habitacion.getPaciente();
        historial.agregar(new RegistroHistorial(habitacion.getNumero(), paciente, "SALIDA"));
        ocupacion.liberar(habitacion.getPiso(), habitacion.getPosicion());
        habitacion.liberar();
    }
    private Habitacion buscarHabitacionActiva(String id) {
        for (Habitacion h : habitaciones)
            if (h.isOcupada() && h.getPaciente().getIdentificacion().equals(id)) return h;
        return null;
    }
    private Habitacion obtenerHabitacion(int numero) {
        if (numero < 1 || numero > 30) throw new IllegalArgumentException("La habitación debe estar entre 1 y 30.");
        return habitaciones[numero - 1];
    }
}