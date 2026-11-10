package hospital.vista.organismos;

import hospital.vista.atomos.UiColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/** Navegación adaptable: barra completa en escritorio y rail de iconos en ventanas estrechas. */
public class Sidebar extends JPanel {
    private final Map<String, JButton> items = new LinkedHashMap<>();
    private final Map<String, String> glyphs = new LinkedHashMap<>();
    private final JLabel brandName;
    private final JPanel clinic;
    private final JPanel help;
    private final JPanel profile;
    private boolean compact;

    public Sidebar(Consumer<String> navigation) {
        setLayout(new BorderLayout());
        setBackground(UiColors.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UiColors.BORDER));
        setPreferredSize(new Dimension(244, 0));
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(22, 14, 14, 14));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brand.setOpaque(false);
        BrandMark mark = new BrandMark();
        brandName = new JLabel("<html><b>Hospitalize</b><br><font color='#8794AB' size='2'>Healthcare workspace</font></html>");
        brandName.setFont(new Font(UiColors.FONT, Font.PLAIN, 14));
        brandName.setForeground(UiColors.INK);
        brand.add(mark); brand.add(brandName);
        content.add(brand); content.add(Box.createVerticalStrut(24));

        clinic = new JPanel(new BorderLayout(9, 0));
        clinic.setBackground(UiColors.SURFACE);
        clinic.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UiColors.BORDER), new EmptyBorder(9, 9, 9, 9)));
        JLabel avatar = new JLabel("GH", SwingConstants.CENTER);
        avatar.setOpaque(true); avatar.setBackground(UiColors.PRIMARY_SOFT); avatar.setForeground(UiColors.PRIMARY);
        avatar.setFont(new Font(UiColors.FONT, Font.BOLD, 10)); avatar.setPreferredSize(new Dimension(32, 32));
        JLabel clinicName = new JLabel("<html><b>Gestor Hospitalario</b><br><font color='#8794AB'>Centro de atención</font></html>");
        clinicName.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        clinic.add(avatar, BorderLayout.WEST); clinic.add(clinicName, BorderLayout.CENTER);
        content.add(clinic); content.add(Box.createVerticalStrut(25));

        JLabel section = new JLabel("ESPACIO DE TRABAJO");
        section.setFont(new Font(UiColors.FONT, Font.BOLD, 9)); section.setForeground(UiColors.TEXT_MUTED);
        section.setBorder(new EmptyBorder(0, 8, 9, 0)); section.setAlignmentX(LEFT_ALIGNMENT);
        content.add(section);
        addItem(content, "Resumen", "dashboard", "⌂", navigation);
        addItem(content, "Pacientes", "pacientes", "♙", navigation);
        addItem(content, "Habitaciones", "habitaciones", "▦", navigation);
        addItem(content, "Historial", "historial", "◷", navigation);
        content.add(Box.createVerticalGlue());

        help = new JPanel();
        help.setOpaque(true); help.setBackground(UiColors.PRIMARY_SOFT);
        help.setBorder(new EmptyBorder(12, 12, 12, 12));
        help.setLayout(new BoxLayout(help, BoxLayout.Y_AXIS));
        JLabel helpTitle = new JLabel("¿Necesitas ayuda?");
        helpTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 10)); helpTitle.setForeground(UiColors.PRIMARY_DARK);
        JLabel helpText = new JLabel("<html>Consulta las funciones del<br>gestor hospitalario.</html>");
        helpText.setFont(new Font(UiColors.FONT, Font.PLAIN, 9)); helpText.setForeground(UiColors.MUTED);
        help.add(helpTitle); help.add(Box.createVerticalStrut(5)); help.add(helpText);
        content.add(help); content.add(Box.createVerticalStrut(14));

        profile = new JPanel(new BorderLayout(9, 0));
        profile.setOpaque(false); profile.setBorder(new EmptyBorder(10, 2, 0, 2));
        JLabel userAvatar = new JLabel("JM", SwingConstants.CENTER);
        userAvatar.setOpaque(true); userAvatar.setBackground(UiColors.PRIMARY_SOFT); userAvatar.setForeground(UiColors.PRIMARY);
        userAvatar.setFont(new Font(UiColors.FONT, Font.BOLD, 10)); userAvatar.setPreferredSize(new Dimension(34, 34));
        JLabel user = new JLabel("<html><b>Administrador</b><br><font color='#8794AB'>Gestión hospitalaria</font></html>");
        user.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        profile.add(userAvatar, BorderLayout.WEST); profile.add(user, BorderLayout.CENTER);
        content.add(profile); add(content, BorderLayout.CENTER);
        setActive("dashboard");
    }

    private void addItem(JPanel parent, String text, String key, String glyph, Consumer<String> navigation) {
        JButton button = new JButton("  " + glyph + "     " + text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        button.setForeground(UiColors.MUTED); button.setBackground(UiColors.WHITE);
        button.setFocusPainted(false); button.setBorderPainted(false); button.setOpaque(true);
        button.setBorder(new EmptyBorder(11, 10, 11, 8));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setAlignmentX(LEFT_ALIGNMENT); button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setToolTipText(text); button.addActionListener(e -> navigation.accept(key));
        items.put(key, button); glyphs.put(key, glyph); parent.add(button); parent.add(Box.createVerticalStrut(4));
    }

    public void setCompact(boolean value) {
        if (compact == value) return;
        compact = value;
        setPreferredSize(new Dimension(compact ? 72 : 244, 0));
        brandName.setVisible(!compact);
        clinic.setVisible(!compact);
        help.setVisible(!compact);
        profile.setVisible(!compact);
        items.forEach((key, button) -> {
            button.setText(compact ? glyphs.get(key) : "  " + glyphs.get(key) + "     " + switch(key) {
                case "dashboard" -> "Resumen"; case "pacientes" -> "Pacientes";
                case "habitaciones" -> "Habitaciones"; default -> "Historial";
            });
            button.setHorizontalAlignment(compact ? SwingConstants.CENTER : SwingConstants.LEFT);
            button.setBorder(new EmptyBorder(11, compact ? 4 : 10, 11, compact ? 4 : 8));
        });
        revalidate(); repaint();
    }

    public void setActive(String key) {
        items.forEach((id, button) -> {
            boolean active = id.equals(key);
            button.setBackground(active ? UiColors.PRIMARY_SOFT : UiColors.WHITE);
            button.setForeground(active ? UiColors.PRIMARY_DARK : UiColors.MUTED);
        });
    }

    private static class BrandMark extends JPanel {
        BrandMark() { setOpaque(false); setPreferredSize(new Dimension(40, 40)); }
        @Override protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setPaint(new GradientPaint(0, 0, UiColors.GRADIENT_START, getWidth(), getHeight(), UiColors.GRADIENT_END));
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 13, 13);
            g.setColor(Color.WHITE); g.setStroke(new BasicStroke(2.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int mid = getHeight()/2; int[] x={7,12,16,20,24,28,34}; int[] y={mid,mid,mid-6,mid+6,mid-4,mid,mid};
            for(int i=0;i<x.length-1;i++) g.drawLine(x[i],y[i],x[i+1],y[i+1]);
            g.dispose();
        }
    }
}
