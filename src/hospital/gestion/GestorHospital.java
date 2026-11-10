package hospital.gestion;

import hospital.estructuras.*;
import hospital.modelos.*;

public class GestorHospital {
    private final Habitacion[] habitaciones = new Habitacion[30];
    private final ListaPacientes pacientes = new ListaPacientes();
    private final ListaHistorial historial = new ListaHistorial();
    private final MatrizDispersa ocupacion = new MatrizDispersa(5, 6);

    public GestorHospital() {
        for (int i = 0; i < habitaciones.length; i++) habitaciones[i] = new Habitacion(i + 1);
    }
    public void registrarPaciente(Paciente paciente) {
        pacientes.agregar(paciente);
        System.out.println("Paciente registrado correctamente.");
    }
    public Paciente buscarPaciente(String identificacion) { return pacientes.buscar(identificacion); }
    public void mostrarPacientes() { pacientes.mostrarTodos(); }

    public void asignarHabitacion(String identificacion, int numero) {
        Paciente paciente = pacientes.buscar(identificacion);
        if (paciente == null) throw new IllegalArgumentException("No existe un paciente con esa identificación.");
        if (buscarHabitacionActiva(identificacion) != null) throw new IllegalStateException("El paciente ya tiene una habitación asignada.");
        Habitacion habitacion = obtenerHabitacion(numero);
        if (habitacion.isOcupada()) throw new IllegalStateException("La habitación ya está ocupada.");
        habitacion.ocupar(paciente);
        ocupacion.ocupar(habitacion.getPiso(), habitacion.getPosicion());
        historial.agregar(new RegistroHistorial(numero, paciente, "INGRESO"));
        System.out.println("Habitación " + numero + " asignada a " + paciente.getNombre() + ".");
    }
    public void darSalida(String identificacion) {
        Habitacion habitacion = buscarHabitacionActiva(identificacion);
        if (habitacion == null) throw new IllegalStateException("El paciente no tiene una hospitalización activa.");
        Paciente paciente = habitacion.getPaciente();
        historial.agregar(new RegistroHistorial(habitacion.getNumero(), paciente, "SALIDA"));
        ocupacion.liberar(habitacion.getPiso(), habitacion.getPosicion());
        habitacion.liberar();
        System.out.println("Salida registrada. Habitación " + habitacion.getNumero() + " liberada.");
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
    public void mostrarDisponibles() {
        boolean hay = false;
        for (Habitacion h : habitaciones) if (!h.isOcupada()) { System.out.println(h); hay = true; }
        if (!hay) System.out.println("No hay habitaciones disponibles.");
    }
    public void mostrarOcupadas() {
        boolean hay = false;
        for (Habitacion h : habitaciones) if (h.isOcupada()) { System.out.println(h); hay = true; }
        if (!hay) System.out.println("No hay habitaciones ocupadas.");
    }
    public void mostrarHistorialHabitacion(int numero) { obtenerHabitacion(numero); historial.mostrarPorHabitacion(numero); }
    public void mostrarHistorialCompleto() { historial.mostrarTodos(); }
    public void mostrarMatriz() { ocupacion.mostrarMatriz(); }
}
