package hospital.vista.organismos;

import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/** Navegación lateral con identidad oscura, jerarquía clara y perfil anclado al pie. */
public class Sidebar extends JPanel {
    private static final Color NAVY=new Color(19,31,54);
    private static final Color NAVY_LIGHT=new Color(29,45,73);
    private static final Color TEXT=new Color(190,202,222);
    private final Map<String,NavButton> items=new LinkedHashMap<>();

    public Sidebar(Consumer<String> navigation) {
        setLayout(new BorderLayout());
        setBackground(NAVY);
        setBorder(BorderFactory.createMatteBorder(0,0,0,1,new Color(31,48,76)));
        setPreferredSize(new Dimension(232,0));

        JPanel upper=new JPanel();
        upper.setOpaque(false);
        upper.setLayout(new BoxLayout(upper,BoxLayout.Y_AXIS));
        upper.setBorder(new EmptyBorder(25,18,0,18));

        JPanel brand=new JPanel(new BorderLayout(12,0));
        brand.setOpaque(false);
        brand.setMaximumSize(new Dimension(Integer.MAX_VALUE,54));
        BrandMark mark=new BrandMark();
        brand.add(mark,BorderLayout.WEST);
        JPanel copy=new JPanel();
        copy.setOpaque(false);
        copy.setLayout(new BoxLayout(copy,BoxLayout.Y_AXIS));
        JLabel name=new JLabel("Hospitalize");
        name.setFont(new Font(UiColors.FONT,Font.BOLD,17));
        name.setForeground(Color.WHITE);
        JLabel subtitle=new JLabel("GESTIÓN HOSPITALARIA");
        subtitle.setFont(new Font(UiColors.FONT,Font.BOLD,8));
        subtitle.setForeground(new Color(139,159,190));
        copy.add(name);copy.add(Box.createVerticalStrut(5));copy.add(subtitle);
        brand.add(copy,BorderLayout.CENTER);
        upper.add(brand);
        upper.add(Box.createVerticalStrut(30));

        JLabel section=new JLabel("ESPACIO DE TRABAJO");
        section.setFont(new Font(UiColors.FONT,Font.BOLD,9));
        section.setForeground(new Color(117,139,173));
        section.setBorder(new EmptyBorder(0,0,13,0));
        section.setAlignmentX(LEFT_ALIGNMENT);
        upper.add(section);

        addItem(upper,"Resumen","dashboard",navigation);
        addItem(upper,"Pacientes","pacientes",navigation);
        addItem(upper,"Habitaciones","habitaciones",navigation);
        addItem(upper,"Historial","historial",navigation);
        add(upper,BorderLayout.NORTH);

        JPanel footer=new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BoxLayout(footer,BoxLayout.Y_AXIS));
        footer.setBorder(new EmptyBorder(12,18,20,18));
        JSeparator line=new JSeparator();
        line.setForeground(new Color(48,65,94));
        line.setBackground(new Color(48,65,94));
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE,1));
        footer.add(line);footer.add(Box.createVerticalStrut(16));
        JPanel profile=new JPanel(new BorderLayout(11,0));
        profile.setOpaque(true);
        profile.setBackground(NAVY_LIGHT);
        profile.setBorder(new EmptyBorder(11,10,11,10));
        profile.setMaximumSize(new Dimension(Integer.MAX_VALUE,58));
        CircleAvatar avatar=new CircleAvatar("JM",36);
        JPanel userCopy=new JPanel();
        userCopy.setOpaque(false);
        userCopy.setLayout(new BoxLayout(userCopy,BoxLayout.Y_AXIS));
        JLabel user=new JLabel("Administrador");
        user.setFont(new Font(UiColors.FONT,Font.BOLD,10));
        user.setForeground(Color.WHITE);
        JLabel role=new JLabel("Sesión activa");
        role.setFont(new Font(UiColors.FONT,Font.PLAIN,9));
        role.setForeground(new Color(145,165,194));
        userCopy.add(user);userCopy.add(Box.createVerticalStrut(4));userCopy.add(role);
        profile.add(avatar,BorderLayout.WEST);profile.add(userCopy,BorderLayout.CENTER);
        footer.add(profile);
        add(footer,BorderLayout.SOUTH);
        setActive("dashboard");
    }

    private void addItem(JPanel parent,String label,String key,Consumer<String> navigation){
        NavButton button=new NavButton(label,key);
        button.addActionListener(e->navigation.accept(key));
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE,48));
        button.setPreferredSize(new Dimension(190,48));
        items.put(key,button);
        parent.add(button);
        parent.add(Box.createVerticalStrut(8));
    }

    public void setActive(String key){items.forEach((id,button)->button.setActive(id.equals(key)));}

    private static class NavButton extends JButton{
        private final String key;
        private boolean active;
        NavButton(String label,String key){
            super(label,new UiIcon(key,new Color(147,166,196),20));
            this.key=key;
            setHorizontalAlignment(SwingConstants.LEFT);
            setIconTextGap(15);
            setFont(new Font(UiColors.FONT,Font.BOLD,12));
            setForeground(TEXT);
            setFocusPainted(false);setBorderPainted(false);setContentAreaFilled(false);
            setBorder(new EmptyBorder(12,15,12,12));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        void setActive(boolean value){
            active=value;
            setForeground(value?Color.WHITE:TEXT);
            setIcon(new UiIcon(key,value?new Color(116,166,255):new Color(147,166,196),20));
            repaint();
        }
        @Override protected void paintComponent(Graphics graphics){
            Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            if(active){
                g.setColor(new Color(48,91,157,105));
                g.fillRoundRect(0,1,getWidth()-1,getHeight()-3,14,14);
                g.setColor(new Color(103,158,255));
                g.fillRoundRect(0,12,3,getHeight()-27,3,3);
            }else if(getModel().isRollover()){
                g.setColor(new Color(255,255,255,13));
                g.fillRoundRect(0,1,getWidth()-1,getHeight()-3,14,14);
            }
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private static class BrandMark extends JPanel{
        BrandMark(){setOpaque(false);setPreferredSize(new Dimension(46,46));}
        @Override protected void paintComponent(Graphics graphics){
            Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setPaint(new GradientPaint(0,0,new Color(36,204,178),getWidth(),getHeight(),new Color(65,125,246)));
            g.fillRoundRect(1,1,44,44,16,16);
            g.setColor(Color.WHITE);g.setStroke(new BasicStroke(2.6f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            g.drawLine(23,12,23,34);g.drawLine(12,23,34,23);g.dispose();
        }
    }

    public static class CircleAvatar extends JComponent{
        private final String initials;private final int diameter;
        public CircleAvatar(String initials,int diameter){
            this.initials=initials;this.diameter=diameter;setOpaque(false);
            setPreferredSize(new Dimension(diameter,diameter));setToolTipText("Administrador");
        }
        @Override protected void paintComponent(Graphics graphics){
            Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            int x=(getWidth()-diameter)/2,y=(getHeight()-diameter)/2;
            g.setColor(new Color(54,104,232));g.fillOval(x,y,diameter,diameter);
            g.setColor(Color.WHITE);g.setFont(new Font(UiColors.FONT,Font.BOLD,Math.max(9,diameter/3)));
            FontMetrics fm=g.getFontMetrics();
            g.drawString(initials,x+(diameter-fm.stringWidth(initials))/2,y+(diameter+fm.getAscent()-fm.getDescent())/2);
            g.dispose();
        }
    }
}