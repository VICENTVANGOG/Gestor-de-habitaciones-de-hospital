package hospital.estructuras;

/**
 * Matriz dispersa de ocupación.
 * Solo guarda las posiciones ocupadas; las demás se consideran disponibles.
 */
public class MatrizDispersa {
    private final int filas;
    private final int columnas;
    private final Tripleta[] datos;
    private int cantidad;

    public MatrizDispersa(int filas, int columnas) {
        if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("Las dimensiones deben ser mayores que cero.");
        }
        this.filas = filas;
        this.columnas = columnas;
        this.datos = new Tripleta[filas * columnas];
        this.cantidad = 0;
    }

    // Revisa si una posición tiene una tripleta con valor 1.
    public boolean estaOcupada(int fila, int columna) {
        validar(fila, columna);
        for (int i = 0; i < cantidad; i++) {
            if (datos[i].getFila() == fila && datos[i].getColumna() == columna) {
                return datos[i].getValor() == 1;
            }
        }
        return false;
    }

    // Guarda una tripleta para marcar la posición como ocupada.
    public void ocupar(int fila, int columna) {
        validar(fila, columna);
        if (estaOcupada(fila, columna)) {
            throw new IllegalStateException("La posición ya está ocupada.");
        }
        datos[cantidad++] = new Tripleta(fila, columna, 1);
    }

    // Elimina la tripleta correspondiente a la posición liberada.
    public void liberar(int fila, int columna) {
        validar(fila, columna);
        for (int i = 0; i < cantidad; i++) {
            if (datos[i].getFila() == fila && datos[i].getColumna() == columna) {
                for (int j = i; j < cantidad - 1; j++) {
                    datos[j] = datos[j + 1];
                }
                datos[--cantidad] = null;
                return;
            }
        }
        throw new IllegalStateException("La posición ya está disponible.");
    }

    // Muestra las tripletas guardadas. Es útil para revisar la estructura.
    public void mostrarTripletas() {
        System.out.println("Tripletas (fila, columna, valor):");
        if (cantidad == 0) {
            System.out.println("No hay habitaciones ocupadas.");
        }
        for (int i = 0; i < cantidad; i++) {
            System.out.println(datos[i]);
        }
    }

    // Imprime la matriz completa y luego las tripletas.
    public void mostrarMatriz() {
        System.out.println("Matriz de ocupación (1 = ocupada, 0 = disponible)");
        for (int fila = 1; fila <= filas; fila++) {
            for (int columna = 1; columna <= columnas; columna++) {
                System.out.print((estaOcupada(fila, columna) ? "1" : "0") + " ");
            }
            System.out.println();
        }
        mostrarTripletas();
    }

    // Comprueba que las coordenadas estén dentro de la matriz.
    private void validar(int fila, int columna) {
        if (fila < 1 || fila > filas || columna < 1 || columna > columnas) {
            throw new IndexOutOfBoundsException("Coordenadas fuera de la matriz.");
        }
    }
}
