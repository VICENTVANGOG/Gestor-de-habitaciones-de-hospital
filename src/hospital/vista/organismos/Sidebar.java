package hospital.vista.organismos;

import hospital.vista.atomos.UiColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/** Navegación lateral de Hospitalize, alineada con el prototipo clínico. */
public class Sidebar extends JPanel {
    private final Map<String,JButton> items=new LinkedHashMap<>();
    public Sidebar(Consumer<String> navigation) {
        setLayout(new BorderLayout());setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0,0,0,1,UiColors.BORDER));
        setPreferredSize(new Dimension(216,0));
        JPanel content=new JPanel();content.setOpaque(false);content.setBorder(new EmptyBorder(22,14,14,14));
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        JPanel brand=new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));brand.setOpaque(false);
        JLabel mark=new JLabel("✚",SwingConstants.CENTER);mark.setOpaque(true);mark.setBackground(UiColors.PRIMARY);
        mark.setForeground(Color.WHITE);mark.setFont(new Font(UiColors.FONT,Font.BOLD,19));mark.setPreferredSize(new Dimension(38,38));
        JLabel brandText=new JLabel("<html><b>Hospitalize</b><br><font color='#8794AB' size='2'>Healthcare, con claridad</font></html>");
        brandText.setFont(new Font(UiColors.FONT,Font.PLAIN,14));brandText.setForeground(UiColors.INK);
        brand.add(mark);brand.add(brandText);content.add(brand);content.add(Box.createVerticalStrut(24));

        JPanel clinic=new JPanel(new BorderLayout(9,0));clinic.setBackground(UiColors.SURFACE);
        clinic.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UiColors.BORDER),new EmptyBorder(9,9,9,9)));
        JLabel clinicMark=new JLabel("GH",SwingConstants.CENTER);clinicMark.setOpaque(true);clinicMark.setBackground(UiColors.PRIMARY_SOFT);
        clinicMark.setForeground(UiColors.PRIMARY);clinicMark.setFont(new Font(UiColors.FONT,Font.BOLD,10));clinicMark.setPreferredSize(new Dimension(32,32));
        JLabel clinicName=new JLabel("<html><b>Centro hospitalario</b><br><font color='#8794AB'>Gestión clínica</font></html>");
        clinicName.setFont(new Font(UiColors.FONT,Font.PLAIN,10));
        clinic.add(clinicMark,BorderLayout.WEST);clinic.add(clinicName,BorderLayout.CENTER);
        content.add(clinic);content.add(Box.createVerticalStrut(26));

        JLabel section=new JLabel("ESPACIO DE TRABAJO");section.setFont(new Font(UiColors.FONT,Font.BOLD,9));
        section.setForeground(UiColors.TEXT_MUTED);section.setBorder(new EmptyBorder(0,8,10,0));section.setAlignmentX(LEFT_ALIGNMENT);
        content.add(section);
        addItem(content,"Resumen","dashboard","⌂",navigation);
        addItem(content,"Pacientes","pacientes","♙",navigation);
        addItem(content,"Habitaciones","habitaciones","▦",navigation);
        addItem(content,"Historial","historial","◷",navigation);
        content.add(Box.createVerticalGlue());

        JPanel help=new JPanel();help.setOpaque(true);help.setBackground(new Color(239,248,250));
        help.setBorder(new EmptyBorder(12,12,12,12));help.setLayout(new BoxLayout(help,BoxLayout.Y_AXIS));
        JLabel helpTitle=new JLabel("¿Necesitas ayuda?");helpTitle.setFont(new Font(UiColors.FONT,Font.BOLD,10));helpTitle.setForeground(UiColors.PRIMARY_DARK);
        JLabel helpBody=new JLabel("<html>Consulta las funciones del<br>gestor hospitalario.</html>");helpBody.setFont(new Font(UiColors.FONT,Font.PLAIN,9));helpBody.setForeground(UiColors.MUTED);
        help.add(helpTitle);help.add(Box.createVerticalStrut(5));help.add(helpBody);content.add(help);content.add(Box.createVerticalStrut(14));

        JPanel profile=new JPanel(new BorderLayout(9,0));profile.setOpaque(false);profile.setBorder(new EmptyBorder(10,2,0,2));
        JLabel avatar=new JLabel("JM",SwingConstants.CENTER);avatar.setOpaque(true);avatar.setBackground(UiColors.PRIMARY_SOFT);
        avatar.setForeground(UiColors.PRIMARY);avatar.setFont(new Font(UiColors.FONT,Font.BOLD,10));avatar.setPreferredSize(new Dimension(34,34));
        JLabel user=new JLabel("<html><b>Administrador</b><br><font color='#8794AB'>Gestión hospitalaria</font></html>");
        user.setFont(new Font(UiColors.FONT,Font.PLAIN,10));profile.add(avatar,BorderLayout.WEST);profile.add(user,BorderLayout.CENTER);
        content.add(profile);add(content,BorderLayout.CENTER);setActive("dashboard");
    }
    private void addItem(JPanel parent,String label,String key,String glyph,Consumer<String> navigation) {
        JPanel wrap=new JPanel(new BorderLayout());wrap.setOpaque(false);wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));wrap.setAlignmentX(LEFT_ALIGNMENT);
        JButton button=new JButton("  "+glyph+"     "+label);button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font(UiColors.FONT,Font.BOLD,11));button.setForeground(UiColors.MUTED);button.setBackground(Color.WHITE);
        button.setFocusPainted(false);button.setBorderPainted(false);button.setOpaque(true);button.setBorder(new EmptyBorder(10,12,10,8));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));button.addActionListener(e->navigation.accept(key));
        items.put(key,button);wrap.add(button,BorderLayout.CENTER);parent.add(wrap);parent.add(Box.createVerticalStrut(4));
    }
    public void setCompact(boolean compact) { /* La plantilla sustituye la barra por navegación inferior en móvil. */ }
    public void setActive(String key) {
        items.forEach((id,button)->{
            boolean active=id.equals(key);
            button.setBackground(active?UiColors.PRIMARY_SOFT:Color.WHITE);
            button.setForeground(active?UiColors.PRIMARY:UiColors.MUTED);
            button.setBorder(BorderFactory.createCompoundBorder(
                active?BorderFactory.createMatteBorder(0,3,0,0,UiColors.PRIMARY):BorderFactory.createEmptyBorder(0,3,0,0),
                new EmptyBorder(10,9,10,8)));
        });
    }
}