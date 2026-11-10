package hospital.estructuras;

public class MatrizDispersa {
    private final int filas;
    private final int columnas;
    private final Tripleta[] datos;
    private int cantidad;

    public MatrizDispersa(int filas, int columnas) {
        this.filas=filas; this.columnas=columnas;
        this.datos=new Tripleta[filas*columnas]; this.cantidad=0;
    }
    public boolean estaOcupada(int fila,int columna) {
        validar(fila,columna);
        for(int i=0;i<cantidad;i++)
            if(datos[i].getFila()==fila && datos[i].getColumna()==columna) return datos[i].getValor()==1;
        return false;
    }
    public void ocupar(int fila,int columna) {
        validar(fila,columna);
        if(estaOcupada(fila,columna)) throw new IllegalStateException("La posición ya está ocupada.");
        datos[cantidad++]=new Tripleta(fila,columna,1);
    }
    public void liberar(int fila,int columna) {
        validar(fila,columna);
        for(int i=0;i<cantidad;i++) if(datos[i].getFila()==fila && datos[i].getColumna()==columna) {
            for(int j=i;j<cantidad-1;j++) datos[j]=datos[j+1];
            datos[--cantidad]=null; return;
        }
        throw new IllegalStateException("La posición ya está disponible.");
    }
    public void mostrarTripletas() {
        System.out.println("Tripletas (fila, columna, valor):");
        if(cantidad==0) System.out.println("No hay habitaciones ocupadas.");
        for(int i=0;i<cantidad;i++) System.out.println(datos[i]);
    }
    public void mostrarMatriz() {
        System.out.println("Matriz de ocupación (1 = ocupada, 0 = disponible)");
        for(int f=1;f<=filas;f++) {
            for(int c=1;c<=columnas;c++) System.out.print((estaOcupada(f,c)?"1":"0")+" ");
            System.out.println();
        }
        mostrarTripletas();
    }
    private void validar(int f,int c) {
        if(f<1||f>filas||c<1||c>columnas) throw new IndexOutOfBoundsException("Coordenadas fuera de la matriz.");
    }
}
