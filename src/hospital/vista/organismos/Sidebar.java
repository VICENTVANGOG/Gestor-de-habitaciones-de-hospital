package hospital.vista.organismos;

import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/** Navegación lateral con superficies suaves y selección tipo píldora. */
public class Sidebar extends JPanel {
    private final Map<String,NavButton> items=new LinkedHashMap<>();
    public Sidebar(Consumer<String> navigation) {
        setLayout(new BorderLayout());setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0,0,0,1,UiColors.BORDER));setPreferredSize(new Dimension(224,0));
        JPanel content=new JPanel();content.setOpaque(false);content.setBorder(new EmptyBorder(22,14,14,14));content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JPanel brand=new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));brand.setOpaque(false);
        brand.add(new BrandMark());JLabel brandText=new JLabel("<html><b>Hospitalize</b><br><font color='#8794AB' size='2'>Healthcare, con claridad</font></html>");
        brandText.setFont(new Font(UiColors.FONT,Font.PLAIN,14));brandText.setForeground(UiColors.INK);brand.add(brandText);
        content.add(brand);content.add(Box.createVerticalStrut(24));
        JPanel clinic=new JPanel(new BorderLayout(9,0));clinic.setBackground(UiColors.SURFACE);
        clinic.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UiColors.BORDER),new EmptyBorder(10,10,10,10)));
        JLabel clinicMark=new JLabel("GH",SwingConstants.CENTER);clinicMark.setOpaque(true);clinicMark.setBackground(UiColors.PRIMARY_SOFT);clinicMark.setForeground(UiColors.PRIMARY);
        clinicMark.setFont(new Font(UiColors.FONT,Font.BOLD,10));clinicMark.setPreferredSize(new Dimension(34,34));
        JLabel clinicName=new JLabel("<html><b>Centro hospitalario</b><br><font color='#8794AB'>Gestión clínica</font></html>");clinicName.setFont(new Font(UiColors.FONT,Font.PLAIN,10));
        clinic.add(clinicMark,BorderLayout.WEST);clinic.add(clinicName,BorderLayout.CENTER);content.add(clinic);content.add(Box.createVerticalStrut(27));
        JLabel section=new JLabel("ESPACIO DE TRABAJO");section.setFont(new Font(UiColors.FONT,Font.BOLD,9));section.setForeground(UiColors.TEXT_MUTED);
        section.setBorder(new EmptyBorder(0,10,11,0));section.setAlignmentX(LEFT_ALIGNMENT);content.add(section);
        addItem(content,"Resumen","dashboard",navigation);addItem(content,"Pacientes","pacientes",navigation);
        addItem(content,"Habitaciones","habitaciones",navigation);addItem(content,"Historial","historial",navigation);
        content.add(Box.createVerticalGlue());
        JPanel help=new JPanel();help.setOpaque(true);help.setBackground(new Color(239,248,250));help.setBorder(new EmptyBorder(13,13,13,13));help.setLayout(new BoxLayout(help,BoxLayout.Y_AXIS));
        JLabel helpTitle=new JLabel("¿Necesitas ayuda?");helpTitle.setFont(new Font(UiColors.FONT,Font.BOLD,10));helpTitle.setForeground(UiColors.PRIMARY_DARK);
        JLabel helpBody=new JLabel("<html>Consulta las funciones del<br>gestor hospitalario.</html>");helpBody.setFont(new Font(UiColors.FONT,Font.PLAIN,9));helpBody.setForeground(UiColors.MUTED);
        help.add(helpTitle);help.add(Box.createVerticalStrut(5));help.add(helpBody);content.add(help);content.add(Box.createVerticalStrut(14));
        JPanel profile=new JPanel(new BorderLayout(9,0));profile.setOpaque(false);profile.setBorder(new EmptyBorder(10,2,0,2));
        JLabel avatar=new JLabel("JM",SwingConstants.CENTER);avatar.setOpaque(true);avatar.setBackground(UiColors.PRIMARY_SOFT);avatar.setForeground(UiColors.PRIMARY);
        avatar.setFont(new Font(UiColors.FONT,Font.BOLD,10));avatar.setPreferredSize(new Dimension(36,36));
        JLabel user=new JLabel("<html><b>Administrador</b><br><font color='#8794AB'>Gestión hospitalaria</font></html>");user.setFont(new Font(UiColors.FONT,Font.PLAIN,10));
        profile.add(avatar,BorderLayout.WEST);profile.add(user,BorderLayout.CENTER);content.add(profile);add(content,BorderLayout.CENTER);setActive("dashboard");
    }
    private void addItem(JPanel parent,String label,String key,Consumer<String> navigation) {
        NavButton button=new NavButton(label,key);button.addActionListener(e->navigation.accept(key));
        button.setAlignmentX(LEFT_ALIGNMENT);button.setMaximumSize(new Dimension(Integer.MAX_VALUE,46));
        items.put(key,button);parent.add(button);parent.add(Box.createVerticalStrut(5));
    }
    public void setCompact(boolean compact) {}
    private static class NavButton extends JButton {
        private final String key;private boolean active;
        NavButton(String label,String key){super(label,new UiIcon(key,UiColors.MUTED,19));this.key=key;setHorizontalAlignment(SwingConstants.LEFT);setIconTextGap(14);
            setFont(new Font(UiColors.FONT,Font.BOLD,11));setForeground(UiColors.MUTED);setFocusPainted(false);setBorderPainted(false);setContentAreaFilled(false);
            setBorder(new EmptyBorder(12,14,12,12));setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
        void setActive(boolean value){active=value;setForeground(value?UiColors.PRIMARY:UiColors.MUTED);setIcon(new UiIcon(key,value?UiColors.PRIMARY:UiColors.MUTED,19));repaint();}
        @Override protected void paintComponent(Graphics graphics){Graphics2D g=(Graphics2D)graphics.create();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            Color bg=active?UiColors.PRIMARY_SOFT:getModel().isRollover()?new Color(246,248,253):Color.WHITE;
            g.setColor(bg);g.fillRoundRect(0,1,getWidth()-1,getHeight()-3,15,15);
            if(active){g.setColor(UiColors.PRIMARY);g.fillRoundRect(0,10,3,getHeight()-21,3,3);}g.dispose();super.paintComponent(graphics);}
    }
    private static class BrandMark extends JPanel {
        BrandMark(){setOpaque(false);setPreferredSize(new Dimension(38,38));}
        @Override protected void paintComponent(Graphics graphics){super.paintComponent(graphics);Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g.setPaint(new GradientPaint(0,0,UiColors.TEAL,getWidth(),getHeight(),UiColors.PRIMARY));
            g.fillRoundRect(0,0,38,38,14,14);g.setColor(Color.WHITE);g.setStroke(new BasicStroke(2.1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            int[] x={6,10,14,18,22,26,32},y={19,19,13,25,15,19,19};for(int i=0;i<x.length-1;i++)g.drawLine(x[i],y[i],x[i+1],y[i+1]);g.dispose();}
    }
    public void setActive(String key){items.forEach((id,button)->button.setActive(id.equals(key)));}
}
