package hospital.modelos;

/** Representa una habitación y el paciente que la ocupa, si existe. */
public class Habitacion {
    private final int numero;
    private final int piso;
    private final int posicion;
    private boolean ocupada;
    private Paciente paciente;

    public Habitacion(int numero) {
        if (numero < 1 || numero > 30) {
            throw new IllegalArgumentException("Número de habitación inválido.");
        }
        this.numero = numero;
        this.piso = (numero - 1) / 6 + 1;
        this.posicion = (numero - 1) % 6 + 1;
        this.ocupada = false;
    }

    public int getNumero() { return numero; }
    public int getPiso() { return piso; }
    public int getPosicion() { return posicion; }
    public boolean isOcupada() { return ocupada; }
    public Paciente getPaciente() { return paciente; }

    // Asigna un paciente a la habitación.
    public void ocupar(Paciente paciente) {
        if (ocupada) {
            throw new IllegalStateException("La habitación ya está ocupada.");
        }
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }
        this.paciente = paciente;
        this.ocupada = true;
    }

    // Deja la habitación disponible y elimina la referencia al paciente.
    public void liberar() {
        if (!ocupada) {
            throw new IllegalStateException("La habitación ya está disponible.");
        }
        this.paciente = null;
        this.ocupada = false;
    }

    @Override
    public String toString() {
        return "Habitación " + numero + " | Piso " + piso + " | Posición " + posicion
                + " | " + (ocupada ? "Ocupada - " + paciente.getNombre() : "Disponible");
    }
}
