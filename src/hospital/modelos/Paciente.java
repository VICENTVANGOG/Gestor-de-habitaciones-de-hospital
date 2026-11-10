package hospital.modelos;

public class Paciente {
    private String identificacion;
    private String nombre;
    private int edad;
    private int nivelTriage;
    private String motivoIngreso;

    public Paciente(String identificacion, String nombre, int edad,
                    int nivelTriage, String motivoIngreso) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.edad = edad;
        this.nivelTriage = nivelTriage;
        this.motivoIngreso = motivoIngreso;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getNivelTriage() {
        return nivelTriage;
    }

    public void setNivelTriage(int nivelTriage) {
        this.nivelTriage = nivelTriage;
    }

    public String getMotivoIngreso() {
        return motivoIngreso;
    }

    public void setMotivoIngreso(String motivoIngreso) {
        this.motivoIngreso = motivoIngreso;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", nivelTriage=" + nivelTriage +
                ", motivoIngreso='" + motivoIngreso + '\'' +
                '}';
    }
}
