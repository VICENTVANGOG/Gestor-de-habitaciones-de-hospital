package hospital.estructuras;

public class Tripleta {
    private final int fila;
    private final int columna;
    private final int valor;
    public Tripleta(int fila, int columna, int valor) {
        this.fila=fila; this.columna=columna; this.valor=valor;
    }
    public int getFila(){return fila;}
    public int getColumna(){return columna;}
    public int getValor(){return valor;}
    @Override public String toString(){return "("+fila+", "+columna+", "+valor+")";}
}
