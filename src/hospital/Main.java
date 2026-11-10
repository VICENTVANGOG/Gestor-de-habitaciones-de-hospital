package hospital;
import hospital.controlador.HospitalController;import hospital.vista.plantillas.MainTemplate;import hospital.vista.paginas.HospitalPage;
import javax.swing.*;import java.awt.*;
/** Arranque de la aplicación de escritorio Java Swing. */
public class Main {
 public static void main(String[] args){SwingUtilities.invokeLater(()->{try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ignored){}
  HospitalController c=new HospitalController();final HospitalPage[] page=new HospitalPage[1];final MainTemplate[] root=new MainTemplate[1];
  root[0]=new MainTemplate(key->{String name=switch(key){case "pacientes"->"Gestión de pacientes";case "habitaciones"->"Habitaciones";case "historial"->"Historial hospitalario";default->"Resumen general";};root[0].showPage("main",name);if(page[0]!=null)page[0].showSection(key);});
  page[0]=new HospitalPage(c);root[0].addPage("main",page[0]);root[0].showPage("main","Resumen general");
  JFrame frame=new JFrame("Gestor Hospitalario");frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);frame.setMinimumSize(new Dimension(1000,650));frame.setSize(1180,740);frame.setLocationRelativeTo(null);frame.setContentPane(root[0]);frame.setVisible(true);
 });}
}