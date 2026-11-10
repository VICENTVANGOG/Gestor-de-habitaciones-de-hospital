package hospital.vista.plantillas;

import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiIcon;
import hospital.vista.organismos.Sidebar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/** Shell adaptable con cabecera compacta, reloj y navegación coherente. */
public class MainTemplate extends JPanel {
    private final JPanel cards=new JPanel(new CardLayout());
    private final Sidebar sidebar;
    private final JLabel pageTitle=new JLabel("Panel de gestión clínica");
    private final JLabel clockLabel=new JLabel();
    private final JPanel mobileNav=new JPanel(new GridLayout(1,4,4,0));
    private final Map<String,JButton> mobileItems=new LinkedHashMap<>();
    private static final DateTimeFormatter CLOCK=DateTimeFormatter.ofPattern("HH:mm");

    public MainTemplate(Consumer<String> navigation) {
        setLayout(new BorderLayout());setBackground(UiColors.BACKGROUND);
        sidebar=new Sidebar(navigation);add(sidebar,BorderLayout.WEST);
        JPanel right=new JPanel(new BorderLayout());right.setBackground(UiColors.BACKGROUND);

        JPanel header=new JPanel(new BorderLayout());header.setPreferredSize(new Dimension(0,68));
        header.setBackground(Color.WHITE);header.setBorder(BorderFactory.createMatteBorder(0,0,1,0,UiColors.BORDER));
        JPanel context=new JPanel(new BorderLayout(12,0));context.setOpaque(false);
        context.setBorder(new EmptyBorder(12,25,10,8));
        JPanel emblem=new JPanel(){@Override protected void paintComponent(Graphics graphics){
            Graphics2D g=(Graphics2D)graphics.create();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(UiColors.PRIMARY_SOFT);g.fillOval(1,1,34,34);g.setColor(UiColors.PRIMARY);
            g.setStroke(new BasicStroke(2f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));g.drawLine(18,9,18,27);g.drawLine(9,18,27,18);g.dispose();}};
        emblem.setOpaque(false);emblem.setPreferredSize(new Dimension(36,36));
        JPanel contextText=new JPanel();contextText.setOpaque(false);contextText.setLayout(new BoxLayout(contextText,BoxLayout.Y_AXIS));
        JLabel clinic=new JLabel("HOSPITALIZE  /  GESTIÓN CLÍNICA");
        clinic.setFont(new Font(UiColors.FONT,Font.BOLD,8));clinic.setForeground(UiColors.MUTED);
        pageTitle.setFont(new Font(UiColors.FONT,Font.BOLD,15));pageTitle.setForeground(UiColors.INK);
        contextText.add(clinic);contextText.add(Box.createVerticalStrut(4));contextText.add(pageTitle);
        context.add(emblem,BorderLayout.WEST);context.add(contextText,BorderLayout.CENTER);
        header.add(context,BorderLayout.WEST);

        JPanel profile=new JPanel(new FlowLayout(FlowLayout.RIGHT,14,14));profile.setOpaque(false);
        JPanel timeChip=new JPanel(new FlowLayout(FlowLayout.CENTER,8,7));timeChip.setOpaque(true);
        timeChip.setBackground(UiColors.SURFACE);timeChip.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UiColors.BORDER),new EmptyBorder(0,9,0,9)));
        JLabel clockIcon=new JLabel(new UiIcon("clock",UiColors.PRIMARY,15));
        clockLabel.setFont(new Font(UiColors.FONT,Font.BOLD,11));clockLabel.setForeground(UiColors.INK);
        JLabel timeCaption=new JLabel("HORA LOCAL");timeCaption.setFont(new Font(UiColors.FONT,Font.BOLD,8));timeCaption.setForeground(UiColors.MUTED);
        timeChip.add(clockIcon);timeChip.add(clockLabel);timeChip.add(timeCaption);profile.add(timeChip);
        Sidebar.CircleAvatar avatar=new Sidebar.CircleAvatar("JM",36);avatar.setToolTipText("Administrador");profile.add(avatar);
        header.add(profile,BorderLayout.EAST);updateClock();
        new Timer(15000,e->updateClock()).start();

        cards.setOpaque(false);right.add(header,BorderLayout.NORTH);right.add(cards,BorderLayout.CENTER);
        mobileNav.setBackground(Color.WHITE);mobileNav.setBorder(BorderFactory.createMatteBorder(1,0,0,0,UiColors.BORDER));
        addMobileItem("Inicio","dashboard",navigation);addMobileItem("Pacientes","pacientes",navigation);
        addMobileItem("Camas","habitaciones",navigation);addMobileItem("Historial","historial",navigation);
        mobileNav.setPreferredSize(new Dimension(0,62));right.add(mobileNav,BorderLayout.SOUTH);
        add(right,BorderLayout.CENTER);
        addComponentListener(new ComponentAdapter(){
            @Override public void componentResized(ComponentEvent e){int width=getWidth();boolean mobile=width<760;
                sidebar.setVisible(!mobile);mobileNav.setVisible(mobile);
                context.setBorder(new EmptyBorder(12,width<520?13:25,10,8));
                timeChip.setVisible(width>=430);right.revalidate();}
        });
    }
    private void updateClock(){clockLabel.setText(LocalTime.now().format(CLOCK));}
    private void addMobileItem(String label,String key,Consumer<String> navigation){
        JButton b=new MobileNavButton(label,key);b.setFont(new Font(UiColors.FONT,Font.BOLD,9));
        b.setForeground(UiColors.MUTED);b.setBackground(Color.WHITE);
        b.setHorizontalTextPosition(SwingConstants.CENTER);b.setVerticalTextPosition(SwingConstants.BOTTOM);b.setIconTextGap(4);
        b.setBorder(new EmptyBorder(6,2,5,2));b.setFocusPainted(false);b.setBorderPainted(false);b.setContentAreaFilled(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));b.addActionListener(e->navigation.accept(key));
        mobileItems.put(key,b);mobileNav.add(b);
    }
    private static class MobileNavButton extends JButton {
        private final String key;MobileNavButton(String label,String key){super(label,new UiIcon(key,UiColors.MUTED,19));this.key=key;
            setHorizontalTextPosition(SwingConstants.CENTER);setVerticalTextPosition(SwingConstants.BOTTOM);setIconTextGap(4);}
        void setActive(boolean value){setForeground(value?UiColors.PRIMARY:UiColors.MUTED);setIcon(new UiIcon(key,value?UiColors.PRIMARY:UiColors.MUTED,19));repaint();}
        @Override protected void paintComponent(Graphics graphics){Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(getForeground().equals(UiColors.PRIMARY)?UiColors.PRIMARY_SOFT:getModel().isRollover()?new Color(246,248,253):Color.WHITE);
            g.fillRoundRect(2,1,getWidth()-4,getHeight()-2,14,14);g.dispose();super.paintComponent(graphics);}
    }
    public void addPage(String key,JPanel page){cards.add(page,key);}
    public void showPage(String key,String name){((CardLayout)cards.getLayout()).show(cards,key);pageTitle.setText(name);}
    public void setActiveSection(String key){sidebar.setActive(key);mobileItems.forEach((id,b)->((MobileNavButton)b).setActive(id.equals(key)));}
}
