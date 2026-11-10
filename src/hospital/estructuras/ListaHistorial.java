package hospital.estructuras;
import hospital.modelos.RegistroHistorial;
public class ListaHistorial {
    private NodoHistorial cabeza;
    public void agregar(RegistroHistorial r){NodoHistorial n=new NodoHistorial(r);if(cabeza==null)cabeza=n;else{NodoHistorial a=cabeza;while(a.getSiguiente()!=null)a=a.getSiguiente();a.setSiguiente(n);}}
    public void mostrarTodos(){if(cabeza==null){System.out.println("No hay movimientos registrados.");return;}for(NodoHistorial n=cabeza;n!=null;n=n.getSiguiente())System.out.println(n.getDato());}
    public void mostrarPorHabitacion(int numero){boolean hay=false;for(NodoHistorial n=cabeza;n!=null;n=n.getSiguiente())if(n.getDato().getNumeroHabitacion()==numero){System.out.println(n.getDato());hay=true;}if(!hay)System.out.println("No hay movimientos para esa habitación.");}
}
