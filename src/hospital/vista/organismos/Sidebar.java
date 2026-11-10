package hospital.vista.organismos;

import hospital.vista.atomos.UiColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/** Organismo de navegación inspirado en la barra lateral de Hospitalize. */
public class Sidebar extends JPanel {
    private final Map<String, JButton> items = new LinkedHashMap<>();
    public Sidebar(Consumer<String> navigation) {
        setLayout(new BorderLayout());
        setBackground(UiColors.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UiColors.BORDER));
        setPreferredSize(new Dimension(244, 0));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(24, 16, 16, 16));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 11, 0));
        brand.setOpaque(false);
        JLabel mark = new JLabel("✚", SwingConstants.CENTER);
        mark.setOpaque(true); mark.setForeground(Color.WHITE);
        mark.setBackground(UiColors.PRIMARY);
        mark.setFont(new Font(UiColors.FONT, Font.BOLD, 21));
        mark.setPreferredSize(new Dimension(42, 42));
        JLabel name = new JLabel("<html><b>Hospitalize</b><br><font color='#8794AB' size='2'>Healthcare, con claridad</font></html>");
        name.setFont(new Font(UiColors.FONT, Font.PLAIN, 14));
        name.setForeground(UiColors.INK);
        brand.add(mark); brand.add(name);
        content.add(brand);
        content.add(Box.createVerticalStrut(25));

        JPanel clinic = new JPanel(new BorderLayout(10, 0));
        clinic.setBackground(UiColors.SURFACE);
        clinic.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER),
                new EmptyBorder(10, 11, 10, 11)));
        JLabel avatar = new JLabel("GH", SwingConstants.CENTER);
        avatar.setOpaque(true); avatar.setBackground(UiColors.PRIMARY_SOFT);
        avatar.setForeground(UiColors.PRIMARY); avatar.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        avatar.setPreferredSize(new Dimension(34, 34));
        JLabel clinicName = new JLabel("<html><b>Gestor Hospitalario</b><br><font color='#96A1B5'>Centro de atención</font></html>");
        clinicName.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        clinic.add(avatar, BorderLayout.WEST); clinic.add(clinicName, BorderLayout.CENTER);
        content.add(clinic);
        content.add(Box.createVerticalStrut(28));

        JLabel section = new JLabel("ESPACIO DE TRABAJO");
        section.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        section.setForeground(new Color(163, 173, 191));
        section.setBorder(new EmptyBorder(0, 8, 10, 0));
        section.setAlignmentX(LEFT_ALIGNMENT);
        content.add(section);
        addItem(content, "Resumen", "dashboard", "⌂", navigation);
        addItem(content, "Pacientes", "pacientes", "♙", navigation);
        addItem(content, "Habitaciones", "habitaciones", "▦", navigation);
        addItem(content, "Historial", "historial", "◷", navigation);
        content.add(Box.createVerticalGlue());

        JPanel help = new JPanel();
        help.setOpaque(true); help.setBackground(new Color(245, 248, 255));
        help.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 237, 255)),
                new EmptyBorder(12, 12, 12, 12)));
        help.setLayout(new BoxLayout(help, BoxLayout.Y_AXIS));
        JLabel helpTitle = new JLabel("¿Necesitas ayuda?");
        helpTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        helpTitle.setForeground(new Color(59, 89, 156));
        JLabel helpText = new JLabel("<html>Consulta las funciones del<br>gestor hospitalario.</html>");
        helpText.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        helpText.setForeground(UiColors.MUTED);
        help.add(helpTitle); help.add(Box.createVerticalStrut(6)); help.add(helpText);
        content.add(help);
        content.add(Box.createVerticalStrut(16));
        JPanel profile = new JPanel(new BorderLayout(10, 0));
        profile.setOpaque(false);
        profile.setBorder(new EmptyBorder(12, 2, 0, 2));
        JLabel userAvatar = new JLabel("JM", SwingConstants.CENTER);
        userAvatar.setOpaque(true); userAvatar.setBackground(new Color(232, 240, 255));
        userAvatar.setForeground(UiColors.PRIMARY); userAvatar.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        userAvatar.setPreferredSize(new Dimension(36, 36));
        JLabel user = new JLabel("<html><b>Administrador</b><br><font color='#99A6B8'>Gestión hospitalaria</font></html>");
        user.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        profile.add(userAvatar, BorderLayout.WEST); profile.add(user, BorderLayout.CENTER);
        content.add(profile);
        add(content, BorderLayout.CENTER);
        setActive("dashboard");
    }
    private void addItem(JPanel parent, String text, String key, String glyph, Consumer<String> navigation) {
        JButton button = new JButton("  " + glyph + "     " + text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        button.setForeground(UiColors.MUTED);
        button.setBackground(UiColors.WHITE);
        button.setFocusPainted(false); button.setBorderPainted(false);
        button.setOpaque(true); button.setBorder(new EmptyBorder(12, 12, 12, 8));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> navigation.accept(key));
        items.put(key, button);
        parent.add(button); parent.add(Box.createVerticalStrut(4));
    }
    public void setActive(String key) {
        items.forEach((id, button) -> {
            boolean active = id.equals(key);
            button.setBackground(active ? UiColors.PRIMARY_SOFT : UiColors.WHITE);
            button.setForeground(active ? UiColors.PRIMARY_DARK : UiColors.MUTED);
        });
    }
}
