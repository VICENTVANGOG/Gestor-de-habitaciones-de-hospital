package hospital;

import hospital.gestion.GestorHospital;
import hospital.modelos.Paciente;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final GestorHospital GESTOR = new GestorHospital();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            try {
                switch (opcion) {
                    case 1 -> registrarPaciente();
                    case 2 -> asignarHabitacion();
                    case 3 -> GESTOR.mostrarDisponibles();
                    case 4 -> GESTOR.mostrarOcupadas();
                    case 5 -> GESTOR.darSalida(leerTexto("Identificación del paciente: "));
                    case 6 -> GESTOR.mostrarPacientes();
                    case 7 -> GESTOR.mostrarHistorialHabitacion(leerEntero("Número de habitación (1-30): "));
                    case 8 -> GESTOR.mostrarMatriz();
                    case 9 -> buscarPaciente();
                    case 10 -> GESTOR.mostrarHistorialCompleto();
                    case 0 -> System.out.println("Gracias por utilizar el Gestor Hospitalario.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Operación no realizada: " + ex.getMessage());
            }
            if (opcion != 0) System.out.println();
        } while (opcion != 0);
    }
    private static void mostrarMenu() {
        System.out.println("========== GESTOR HOSPITALARIO ==========");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Asignar habitación");
        System.out.println("3. Ver habitaciones disponibles");
        System.out.println("4. Ver habitaciones ocupadas");
        System.out.println("5. Dar salida a un paciente");
        System.out.println("6. Ver todos los pacientes");
        System.out.println("7. Ver historial de una habitación");
        System.out.println("8. Ver matriz dispersa (tripletas)");
        System.out.println("9. Buscar paciente por identificación");
        System.out.println("10. Ver historial completo");
        System.out.println("0. Salir");
    }
    private static void registrarPaciente() {
        String id = leerTexto("Identificación: ");
        String nombre = leerTexto("Nombre completo: ");
        int edad = leerEntero("Edad: ");
        int triage = leerEntero("Nivel de triage (1-5): ");
        if (edad <= 0) throw new IllegalArgumentException("La edad debe ser mayor que cero.");
        if (triage < 1 || triage > 5) throw new IllegalArgumentException("El triage debe estar entre 1 y 5.");
        String motivo = leerTexto("Motivo de ingreso: ");
        GESTOR.registrarPaciente(new Paciente(id, nombre, edad, triage, motivo));
    }
    private static void asignarHabitacion() {
        String id = leerTexto("Identificación del paciente: ");
        int numero = leerEntero("Número de habitación (1-30): ");
        GESTOR.asignarHabitacion(id, numero);
    }
    private static void buscarPaciente() {
        String id = leerTexto("Identificación: ");
        Paciente paciente = GESTOR.buscarPaciente(id);
        System.out.println(paciente == null ? "Paciente no encontrado." : paciente);
    }
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String valor = SC.nextLine().trim();
        if (valor.isEmpty()) throw new IllegalArgumentException("El campo no puede estar vacío.");
        return valor;
    }
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try { return Integer.parseInt(SC.nextLine().trim()); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("Debe ingresar un número entero."); }
    }
}
