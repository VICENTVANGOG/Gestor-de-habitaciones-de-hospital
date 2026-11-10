package hospital.vista.organismos;

import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/** Navegación lateral minimalista: marca, menú y perfil compacto. */
public class Sidebar extends JPanel {
    private final Map<String,NavButton> items=new LinkedHashMap<>();
    public Sidebar(Consumer<String> navigation) {
        setLayout(new BorderLayout());setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0,0,0,1,UiColors.BORDER));
        setPreferredSize(new Dimension(216,0));
        JPanel content=new JPanel();content.setOpaque(false);
        content.setBorder(new EmptyBorder(25,12,17,12));
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        JPanel brand=new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));brand.setOpaque(false);
        brand.setAlignmentX(LEFT_ALIGNMENT);brand.add(new BrandMark());
        JPanel brandCopy=new JPanel();brandCopy.setOpaque(false);brandCopy.setLayout(new BoxLayout(brandCopy,BoxLayout.Y_AXIS));
        JLabel brandName=new JLabel("Hospitalize");brandName.setFont(new Font(UiColors.FONT,Font.BOLD,16));brandName.setForeground(UiColors.INK);
        JLabel brandSub=new JLabel("PANEL DE GESTIÓN");brandSub.setFont(new Font(UiColors.FONT,Font.BOLD,8));brandSub.setForeground(UiColors.MUTED);
        brandCopy.add(brandName);brandCopy.add(Box.createVerticalStrut(3));brandCopy.add(brandSub);brand.add(brandCopy);
        content.add(brand);content.add(Box.createVerticalStrut(34));

        JLabel section=new JLabel("MENÚ PRINCIPAL");section.setFont(new Font(UiColors.FONT,Font.BOLD,9));
        section.setForeground(UiColors.TEXT_MUTED);section.setBorder(new EmptyBorder(0,9,12,0));
        section.setAlignmentX(LEFT_ALIGNMENT);content.add(section);
        addItem(content,"Resumen","dashboard",navigation);
        addItem(content,"Pacientes","pacientes",navigation);
        addItem(content,"Habitaciones","habitaciones",navigation);
        addItem(content,"Historial","historial",navigation);

        content.add(Box.createVerticalGlue());
        JSeparator separator=new JSeparator();separator.setForeground(UiColors.BORDER);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE,1));content.add(separator);
        content.add(Box.createVerticalStrut(14));
        JPanel profile=new JPanel(new BorderLayout(10,0));profile.setOpaque(false);
        profile.setBorder(new EmptyBorder(0,7,0,4));profile.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));
        CircleAvatar avatar=new CircleAvatar("JM",36);
        JPanel userCopy=new JPanel();userCopy.setOpaque(false);userCopy.setLayout(new BoxLayout(userCopy,BoxLayout.Y_AXIS));
        JLabel user=new JLabel("Administrador");user.setFont(new Font(UiColors.FONT,Font.BOLD,10));user.setForeground(UiColors.INK);
        JLabel role=new JLabel("Cuenta activa");role.setFont(new Font(UiColors.FONT,Font.PLAIN,9));role.setForeground(UiColors.MUTED);
        userCopy.add(user);userCopy.add(Box.createVerticalStrut(3));userCopy.add(role);
        profile.add(avatar,BorderLayout.WEST);profile.add(userCopy,BorderLayout.CENTER);content.add(profile);
        add(content,BorderLayout.CENTER);setActive("dashboard");
    }
    private void addItem(JPanel parent,String label,String key,Consumer<String> navigation) {
        NavButton button=new NavButton(label,key);button.addActionListener(e->navigation.accept(key));
        button.setAlignmentX(LEFT_ALIGNMENT);button.setMaximumSize(new Dimension(Integer.MAX_VALUE,45));
        items.put(key,button);parent.add(button);parent.add(Box.createVerticalStrut(6));
    }
    public void setCompact(boolean compact) {}
    private static class NavButton extends JButton {
        private final String key;private boolean active;
        NavButton(String label,String key){super(label,new UiIcon(key,UiColors.MUTED,19));this.key=key;
            setHorizontalAlignment(SwingConstants.LEFT);setIconTextGap(13);
            setFont(new Font(UiColors.FONT,Font.BOLD,11));setForeground(UiColors.MUTED);
            setFocusPainted(false);setBorderPainted(false);setContentAreaFilled(false);
            setBorder(new EmptyBorder(12,13,12,10));setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
        void setActive(boolean value){active=value;setForeground(value?UiColors.PRIMARY:UiColors.MUTED);
            setIcon(new UiIcon(key,value?UiColors.PRIMARY:UiColors.MUTED,19));repaint();}
        @Override protected void paintComponent(Graphics graphics){Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            Color bg=active?new Color(238,243,255):getModel().isRollover()?new Color(247,249,253):Color.WHITE;
            g.setColor(bg);g.fillRoundRect(0,1,getWidth()-1,getHeight()-3,14,14);
            if(active){g.setColor(UiColors.PRIMARY);g.fillRoundRect(0,11,3,getHeight()-23,3,3);}
            g.dispose();super.paintComponent(graphics);}
    }
    private static class BrandMark extends JPanel {
        BrandMark(){setOpaque(false);setPreferredSize(new Dimension(42,42));}
        @Override protected void paintComponent(Graphics graphics){Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setPaint(new GradientPaint(0,0,UiColors.TEAL,getWidth(),getHeight(),UiColors.PRIMARY));
            g.fillOval(1,1,40,40);g.setColor(Color.WHITE);g.setStroke(new BasicStroke(2.3f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            g.drawLine(21,11,21,31);g.drawLine(11,21,31,21);g.dispose();}
    }
    public static class CircleAvatar extends JComponent {
        private final String initials;private final int diameter;
        public CircleAvatar(String initials,int diameter){this.initials=initials;this.diameter=diameter;setOpaque(false);setPreferredSize(new Dimension(diameter,diameter));setToolTipText("Administrador");}
        @Override protected void paintComponent(Graphics graphics){Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            int x=(getWidth()-diameter)/2,y=(getHeight()-diameter)/2;
            g.setColor(UiColors.PRIMARY_SOFT);g.fillOval(x,y,diameter,diameter);
            g.setColor(UiColors.PRIMARY);g.setFont(new Font(UiColors.FONT,Font.BOLD,Math.max(9,diameter/3)));
            FontMetrics fm=g.getFontMetrics();g.drawString(initials,x+(diameter-fm.stringWidth(initials))/2,y+(diameter+fm.getAscent()-fm.getDescent())/2);g.dispose();}
    }
    public void setActive(String key){items.forEach((id,button)->button.setActive(id.equals(key)));}
}
