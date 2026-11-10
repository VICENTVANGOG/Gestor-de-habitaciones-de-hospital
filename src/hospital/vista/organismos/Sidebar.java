package hospital.vista.organismos;
import hospital.vista.atomos.UiButton;import javax.swing.*;import java.awt.*;import java.util.function.Consumer;
/** Organismo: menú de navegación lateral. */
public class Sidebar extends JPanel {
 public Sidebar(Consumer<String> nav){setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));setBackground(new Color(15,23,42));setBorder(BorderFactory.createEmptyBorder(22,14,22,14));JLabel brand=new JLabel("HOSPITAL");brand.setForeground(Color.WHITE);brand.setFont(new Font("Segoe UI",Font.BOLD,21));add(brand);add(Box.createVerticalStrut(28));item("Resumen","dashboard",nav);item("Pacientes","pacientes",nav);item("Habitaciones","habitaciones",nav);item("Historial","historial",nav);}
 private void item(String name,String key,Consumer<String> nav){UiButton b=new UiButton(name);b.setAlignmentX(LEFT_ALIGNMENT);b.setMaximumSize(new Dimension(200,42));b.setBackground(new Color(30,41,59));b.addActionListener(e->nav.accept(key));add(b);add(Box.createVerticalStrut(8));}
}