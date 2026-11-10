package hospital;
import hospital.controlador.HospitalController;import hospital.vista.plantillas.MainTemplate;import hospital.vista.paginas.HospitalPage;
import javax.swing.*;import java.awt.*;
/** Arranque de la aplicación de escritorio Java Swing. */
public class Main {
 public static void main(String[] args){SwingUtilities.invokeLater(()->{try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ignored){}HospitalController c=new HospitalController();final MainTemplate[] ref=new MainTemplate[1];MainTemplate root=new MainTemplate(key->{String name=switch(key){case "pacientes"->"Pacientes";case "habitaciones"->"Habitaciones";case "historial"->"Historial";default->"Resumen";};ref[0].showPage("main",name);});ref[0]=root;root.addPage("main",new HospitalPage(c));JFrame frame=new JFrame("Gestor Hospitalario");frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);frame.setMinimumSize(new Dimension(1000,650));frame.setSize(1180,740);frame.setLocationRelativeTo(null);frame.setContentPane(root);frame.setVisible(true);});}
}