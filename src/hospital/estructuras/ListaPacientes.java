package hospital.estructuras;

import hospital.modelos.Paciente;
import java.util.ArrayList;
import java.util.List;

/** Lista simplemente enlazada propia para almacenar pacientes. */
public class ListaPacientes {
    private NodoPaciente cabeza;
    public boolean existe(String id) {
        for (NodoPaciente n=cabeza; n!=null; n=n.getSiguiente())
            if (n.getDato().getIdentificacion().equals(id)) return true;
        return false;
    }
    public void agregar(Paciente p) {
        if (p == null) throw new IllegalArgumentException("El paciente no puede ser nulo.");
        if (existe(p.getIdentificacion())) throw new IllegalArgumentException("Ya existe un paciente con esa identificación.");
        NodoPaciente n=new NodoPaciente(p);
        if(cabeza==null)cabeza=n;
        else {NodoPaciente a=cabeza;while(a.getSiguiente()!=null)a=a.getSiguiente();a.setSiguiente(n);}
    }
    public Paciente buscar(String id) {
        for(NodoPaciente n=cabeza;n!=null;n=n.getSiguiente())
            if(n.getDato().getIdentificacion().equals(id)) return n.getDato();
        return null;
    }
    public List<Paciente> obtenerTodos() {
        List<Paciente> resultado=new ArrayList<>();
        for(NodoPaciente n=cabeza;n!=null;n=n.getSiguiente()) resultado.add(n.getDato());
        return resultado;
    }
}