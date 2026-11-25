package hospital.modelos;

/**
 * Reglas de validación del formulario de registro de pacientes.
 * Los métodos "revisar" se aplican mientras el usuario escribe (texto parcial)
 * y los métodos "validar" se aplican al confirmar el registro.
 * Cada método devuelve el mensaje de error o null cuando el valor es correcto.
 */
public final class ValidadorPaciente {
    public static final int ID_MIN = 5;
    public static final int ID_MAX = 12;
    public static final int NOMBRE_MAX = 60;
    public static final int EDAD_MAX = 130;
    public static final int MOTIVO_MAX = 120;
    public static final String TRIAJE_INCORRECTO = "Triaje incorrecto: solo del 1 al 5.";

    private ValidadorPaciente() {}

    public static String revisarIdentificacion(String texto) {
        if (!texto.matches("\\d*")) return "Solo se permiten números.";
        if (texto.length() > ID_MAX) return "Máximo " + ID_MAX + " dígitos.";
        return null;
    }

    public static String revisarNombre(String texto) {
        if (texto.matches(".*\\d.*")) return "El nombre no admite números.";
        if (!texto.matches("[\\p{L} ]*")) return "Solo se permiten letras.";
        if (texto.length() > NOMBRE_MAX) return "Máximo " + NOMBRE_MAX + " caracteres.";
        return null;
    }

    public static String revisarEdad(String texto) {
        if (!texto.matches("\\d*")) return "La edad solo admite números.";
        if (texto.length() > 3 || (!texto.isEmpty() && Integer.parseInt(texto) > EDAD_MAX)) {
            return "La edad debe estar entre 0 y " + EDAD_MAX + ".";
        }
        return null;
    }

    public static String revisarTriaje(String texto) {
        return texto.matches("[1-5]?") ? null : TRIAJE_INCORRECTO;
    }

    public static String revisarMotivo(String texto) {
        return texto.length() > MOTIVO_MAX ? "Máximo " + MOTIVO_MAX + " caracteres." : null;
    }

    public static String validarIdentificacion(String texto) {
        String valor = texto == null ? "" : texto.trim();
        if (valor.isEmpty()) return "La identificación es obligatoria.";
        String error = revisarIdentificacion(valor);
        if (error != null) return error;
        return valor.length() < ID_MIN ? "Mínimo " + ID_MIN + " dígitos." : null;
    }

    public static String validarNombre(String texto) {
        String valor = texto == null ? "" : texto.trim();
        if (valor.isEmpty()) return "El nombre es obligatorio.";
        String error = revisarNombre(valor);
        if (error != null) return error;
        return valor.replace(" ", "").length() < 3 ? "Escribe al menos 3 letras." : null;
    }

    public static String validarEdad(String texto) {
        String valor = texto == null ? "" : texto.trim();
        if (valor.isEmpty()) return "La edad es obligatoria.";
        return revisarEdad(valor);
    }

    public static String validarTriaje(String texto) {
        String valor = texto == null ? "" : texto.trim();
        if (valor.isEmpty()) return "El nivel de triaje es obligatorio.";
        return revisarTriaje(valor);
    }

    public static String validarMotivo(String texto) {
        String valor = texto == null ? "" : texto.trim();
        if (valor.isEmpty()) return "El motivo es obligatorio.";
        if (valor.length() < 3) return "Mínimo 3 caracteres.";
        return revisarMotivo(valor);
    }
}
