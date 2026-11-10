package hospital.estructuras;

import hospital.modelos.RegistroHistorial;
import java.util.ArrayList;
import java.util.List;

/** Lista simplemente enlazada propia para conservar los movimientos hospitalarios. */
public class ListaHistorial {
    private NodoHistorial cabeza;
    public void agregar(RegistroHistorial r) {
        NodoHistorial n=new NodoHistorial(r);
        if(cabeza==null)cabeza=n;
        else {NodoHistorial a=cabeza;while(a.getSiguiente()!=null)a=a.getSiguiente();a.setSiguiente(n);}
    }
    public List<RegistroHistorial> obtenerTodos() {
        List<RegistroHistorial> resultado=new ArrayList<>();
        for(NodoHistorial n=cabeza;n!=null;n=n.getSiguiente()) resultado.add(n.getDato());
        return resultado;
    }
}