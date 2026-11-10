package hospital.modelos;

/** Datos de un paciente y validaciones básicas de sus campos. */
public class Paciente {
    private String identificacion;
    private String nombre;
    private int edad;
    private int nivelTriage;
    private String motivoIngreso;

    public Paciente(String identificacion, String nombre, int edad,
                    int nivelTriage, String motivoIngreso) {
        setIdentificacion(identificacion);
        setNombre(nombre);
        setEdad(edad);
        setNivelTriage(nivelTriage);
        setMotivoIngreso(motivoIngreso);
    }

    public String getIdentificacion() { return identificacion; }

    // La identificación es obligatoria y se guarda sin espacios externos.
    public void setIdentificacion(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria.");
        }
        this.identificacion = value.trim();
    }

    public String getNombre() { return nombre; }

    public void setNombre(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente es obligatorio.");
        }
        this.nombre = value.trim();
    }

    public int getEdad() { return edad; }

    public void setEdad(int value) {
        if (value < 0 || value > 130) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 130 años.");
        }
        this.edad = value;
    }

    public int getNivelTriage() { return nivelTriage; }

    public void setNivelTriage(int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("El nivel de triage debe estar entre 1 y 5.");
        }
        this.nivelTriage = value;
    }

    public String getMotivoIngreso() { return motivoIngreso; }

    public void setMotivoIngreso(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de ingreso es obligatorio.");
        }
        this.motivoIngreso = value.trim();
    }

    @Override
    public String toString() {
        return "Paciente{" + "identificacion='" + identificacion + '''
                + ", nombre='" + nombre + ''' + ", edad=" + edad
                + ", nivelTriage=" + nivelTriage + ", motivoIngreso='"
                + motivoIngreso + ''' + '}';
    }
}
