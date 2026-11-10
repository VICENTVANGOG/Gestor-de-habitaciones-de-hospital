package hospital.gestion;

import hospital.estructuras.*;
import hospital.modelos.*;
import java.util.ArrayList;
import java.util.List;

/** Contiene las reglas para registrar pacientes y administrar habitaciones. */
public class GestorHospital {
    private final Habitacion[] habitaciones = new Habitacion[30];
    private final ListaPacientes pacientes = new ListaPacientes();
    private final ListaHistorial historial = new ListaHistorial();
    private final MatrizDispersa ocupacion = new MatrizDispersa(5, 6);

    public GestorHospital() {
        // Crea las 30 habitaciones: cinco pisos con seis habitaciones cada uno.
        for (int i = 0; i < habitaciones.length; i++) {
            habitaciones[i] = new Habitacion(i + 1);
        }
    }

    public void registrarPaciente(Paciente paciente) {
        pacientes.agregar(paciente);
    }

    public Paciente buscarPaciente(String identificacion) {
        return pacientes.buscar(identificacion);
    }

    public List<Paciente> obtenerPacientes() {
        return pacientes.obtenerTodos();
    }

    // Prepara las habitaciones para que la interfaz pueda mostrarlas.
    public List<Habitacion> obtenerHabitaciones() {
        List<Habitacion> resultado = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            resultado.add(habitacion);
        }
        return resultado;
    }

    public List<RegistroHistorial> obtenerHistorial() {
        return historial.obtenerTodos();
    }

    // Construye una representación de la matriz para consultas académicas.
    public String obtenerMatrizTexto() {
        StringBuilder texto = new StringBuilder(
                "Matriz de ocupación (1 = ocupada, 0 = disponible)\n");

        for (int fila = 1; fila <= 5; fila++) {
            for (int columna = 1; columna <= 6; columna++) {
                texto.append(ocupacion.estaOcupada(fila, columna) ? "1 " : "0 ");
            }
            texto.append('\n');
        }

        texto.append("\nTripletas (fila, columna, valor):\n");
        for (int fila = 1; fila <= 5; fila++) {
            for (int columna = 1; columna <= 6; columna++) {
                if (ocupacion.estaOcupada(fila, columna)) {
                    texto.append("(").append(fila).append(", ")
                            .append(columna).append(", 1)\n");
                }
            }
        }
        return texto.toString();
    }

    // Comprueba los datos y actualiza habitación, matriz e historial.
    public void asignarHabitacion(String identificacion, int numero) {
        Paciente paciente = pacientes.buscar(identificacion);
        if (paciente == null) {
            throw new IllegalArgumentException(
                    "No existe un paciente con esa identificación.");
        }
        if (buscarHabitacionActiva(identificacion) != null) {
            throw new IllegalStateException(
                    "El paciente ya tiene una habitación asignada.");
        }

        Habitacion habitacion = obtenerHabitacion(numero);
        if (habitacion.isOcupada()) {
            throw new IllegalStateException("La habitación ya está ocupada.");
        }

        habitacion.ocupar(paciente);
        ocupacion.ocupar(habitacion.getPiso(), habitacion.getPosicion());
        historial.agregar(new RegistroHistorial(numero, paciente, "INGRESO"));
    }

    // Registra la salida y deja libre la habitación y su posición en la matriz.
    public void darSalida(String identificacion) {
        Habitacion habitacion = buscarHabitacionActiva(identificacion);
        if (habitacion == null) {
            throw new IllegalStateException(
                    "El paciente no tiene una hospitalización activa.");
        }

        Paciente paciente = habitacion.getPaciente();
        historial.agregar(new RegistroHistorial(
                habitacion.getNumero(), paciente, "SALIDA"));
        ocupacion.liberar(habitacion.getPiso(), habitacion.getPosicion());
        habitacion.liberar();
    }

    // Busca si el paciente ya está ocupando una habitación.
    private Habitacion buscarHabitacionActiva(String id) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.isOcupada()
                    && habitacion.getPaciente().getIdentificacion().equals(id)) {
                return habitacion;
            }
        }
        return null;
    }

    // Obtiene una habitación por su número (del 1 al 30).
    private Habitacion obtenerHabitacion(int numero) {
        if (numero < 1 || numero > 30) {
            throw new IllegalArgumentException(
                    "La habitación debe estar entre 1 y 30.");
        }
        return habitaciones[numero - 1];
    }
}
