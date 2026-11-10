package hospital.estructuras;
import hospital.modelos.Paciente;
public class ListaPacientes {
    private NodoPaciente cabeza;
    public boolean existe(String id){for(NodoPaciente n=cabeza;n!=null;n=n.getSiguiente()) if(n.getDato().getIdentificacion().equals(id)) return true; return false;}
    public void agregar(Paciente p){if(existe(p.getIdentificacion())) throw new IllegalArgumentException("Ya existe un paciente con esa identificación."); NodoPaciente n=new NodoPaciente(p); if(cabeza==null)cabeza=n; else {NodoPaciente a=cabeza;while(a.getSiguiente()!=null)a=a.getSiguiente();a.setSiguiente(n);}}
    public Paciente buscar(String id){for(NodoPaciente n=cabeza;n!=null;n=n.getSiguiente())if(n.getDato().getIdentificacion().equals(id))return n.getDato();return null;}
    public void mostrarTodos(){if(cabeza==null){System.out.println("No hay pacientes registrados.");return;}for(NodoPaciente n=cabeza;n!=null;n=n.getSiguiente())System.out.println(n.getDato());}
}
