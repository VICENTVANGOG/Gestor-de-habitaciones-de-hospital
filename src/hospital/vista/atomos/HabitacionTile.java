package hospital.vista.atomos;

import hospital.modelos.Habitacion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Tarjeta interactiva de cama, con estados visuales y texto accesible. */
public class HabitacionTile extends JButton {
    private final Habitacion habitacion;
    public HabitacionTile(Habitacion habitacion) {
        this.habitacion=habitacion;
        setLayout(new BorderLayout(0,6));
        setBorder(new EmptyBorder(11,9,10,9));
        setFocusPainted(false);setContentAreaFilled(false);setBorderPainted(false);setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setToolTipText("Abrir habitación "+habitacion.getNumero());

        JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);
        JLabel number=new JLabel(String.format("%02d",habitacion.getNumero()));
        number.setFont(new Font(UiColors.FONT,Font.BOLD,21));
        number.setForeground(habitacion.isOcupada()?UiColors.DANGER:UiColors.INK);
        JLabel bed=new JLabel("▰");bed.setFont(new Font(UiColors.FONT,Font.BOLD,12));
        bed.setForeground(habitacion.isOcupada()?UiColors.DANGER:UiColors.TEAL);
        top.add(number,BorderLayout.WEST);top.add(bed,BorderLayout.EAST);

        JLabel status=new JLabel(habitacion.isOcupada()?"● OCUPADA":"● DISPONIBLE");
        status.setFont(new Font(UiColors.FONT,Font.BOLD,8));
        status.setForeground(habitacion.isOcupada()?UiColors.DANGER:UiColors.TEAL);
        String patientName=habitacion.isOcupada()&&habitacion.getPaciente()!=null
            ?shorten(habitacion.getPaciente().getNombre(),18):"Cama libre";
        JLabel patient=new JLabel(patientName);
        patient.setFont(new Font(UiColors.FONT,Font.PLAIN,9));patient.setForeground(UiColors.MUTED);
        add(top,BorderLayout.NORTH);
        JPanel bottom=new JPanel();bottom.setOpaque(false);bottom.setLayout(new BoxLayout(bottom,BoxLayout.Y_AXIS));
        bottom.add(status);bottom.add(Box.createVerticalStrut(4));bottom.add(patient);
        add(bottom,BorderLayout.CENTER);
        setPreferredSize(new Dimension(116,88));
    }
    private String shorten(String value,int max){
        if(value==null)return "Paciente asignado";
        return value.length()<=max?value:value.substring(0,max-1)+"…";
    }
    @Override protected void paintComponent(Graphics graphics){
        Graphics2D g=(Graphics2D)graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        boolean occupied=habitacion.isOcupada();
        Color fill=occupied?UiColors.DANGER_SOFT:UiColors.TEAL_SOFT;
        Color stroke=occupied?new Color(248,214,219):new Color(202,237,228);
        if(getModel().isRollover()){
            fill=occupied?new Color(255,229,232):new Color(218,246,239);
            stroke=occupied?UiColors.DANGER:UiColors.TEAL;
        }
        g.setColor(new Color(29,43,73,9));g.fillRoundRect(1,3,getWidth()-2,getHeight()-3,15,15);
        g.setColor(fill);g.fillRoundRect(0,0,getWidth()-2,getHeight()-4,15,15);
        g.setColor(stroke);g.setStroke(new BasicStroke(1f));g.drawRoundRect(0,0,getWidth()-2,getHeight()-4,15,15);
        g.dispose();super.paintComponent(graphics);
    }
}