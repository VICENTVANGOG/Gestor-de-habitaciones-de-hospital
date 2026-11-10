package hospital.vista.moleculas;
import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/** Tarjeta métrica de Hospitalize con iconografía diferenciada por indicador. */
public class StatCard extends JPanel {
 private final JLabel value=new JLabel("—");
 private final JLabel detail=new JLabel(" ");
 private final JLabel icon;
 public StatCard(String title,Color accent) {
  setOpaque(false);setLayout(new BorderLayout(8,7));setBorder(new EmptyBorder(13,13,12,13));
  JPanel top=new JPanel(new BorderLayout(8,0));top.setOpaque(false);
  JLabel heading=new JLabel(title);heading.setFont(new Font(UiColors.FONT,Font.BOLD,9));heading.setForeground(UiColors.MUTED);
  icon=new JLabel(new UiIcon(symbol(title),accent,18),SwingConstants.CENTER);icon.setOpaque(true);
  icon.setBackground(new Color(accent.getRed(),accent.getGreen(),accent.getBlue(),24));icon.setForeground(accent);
  icon.setPreferredSize(new Dimension(34,34));
  top.add(heading,BorderLayout.CENTER);top.add(icon,BorderLayout.EAST);
  value.setFont(new Font(UiColors.FONT,Font.BOLD,26));value.setForeground(UiColors.INK);
  detail.setFont(new Font(UiColors.FONT,Font.PLAIN,9));detail.setForeground(UiColors.MUTED);
  add(top,BorderLayout.NORTH);add(value,BorderLayout.CENTER);add(detail,BorderLayout.SOUTH);
 }
 private String symbol(String title){
  if(title.contains("PACIENTES"))return "pacientes";
  if(title.contains("OCUPADAS"))return "bed-occupied";
  if(title.contains("DISPONIBLES"))return "bed-available";
  return "occupancy";
 }
 @Override protected void paintComponent(Graphics graphics){
  Graphics2D g=(Graphics2D)graphics.create();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
  g.setColor(new Color(29,43,73,8));g.fillRoundRect(1,3,getWidth()-2,getHeight()-3,18,18);
  g.setColor(Color.WHITE);g.fillRoundRect(0,0,getWidth()-2,getHeight()-4,18,18);
  g.setColor(UiColors.BORDER);g.drawRoundRect(0,0,getWidth()-2,getHeight()-4,18,18);g.dispose();super.paintComponent(graphics);
 }
 public void setValue(String text,String description){value.setText(text);detail.setText(description);}
}