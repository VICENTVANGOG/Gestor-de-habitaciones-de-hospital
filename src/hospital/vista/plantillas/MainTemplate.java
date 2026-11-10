package hospital.vista.plantillas;
import hospital.vista.organismos.Sidebar;import javax.swing.*;import java.awt.*;import java.util.function.Consumer;
/** Plantilla Atomic Design: navegación + encabezado + contenido. */
public class MainTemplate extends JPanel {
 private final JPanel cards=new JPanel(new CardLayout());private final JLabel title=new JLabel("Resumen");
 public MainTemplate(Consumer<String> nav){setLayout(new BorderLayout());add(new Sidebar(nav),BorderLayout.WEST);JPanel right=new JPanel(new BorderLayout());title.setFont(new Font("Segoe UI",Font.BOLD,22));title.setBorder(BorderFactory.createEmptyBorder(20,24,20,20));right.add(title,BorderLayout.NORTH);right.add(cards,BorderLayout.CENTER);add(right,BorderLayout.CENTER);}
 public void addPage(String key,JPanel page){cards.add(page,key);}
 public void showPage(String key,String name){((CardLayout)cards.getLayout()).show(cards,key);title.setText(name);}
}