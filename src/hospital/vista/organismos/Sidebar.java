package hospital.vista.organismos;

import hospital.vista.atomos.UiColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

/** Organismo de navegación lateral, con jerarquía visual clara y accesible. */
public class Sidebar extends JPanel {
    public Sidebar(Consumer<String> navigation) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UiColors.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, UiColors.BORDER),
                new EmptyBorder(24, 16, 24, 16)));
        setPreferredSize(new Dimension(220, 0));

        JLabel brand = new JLabel("Hospitalize");
        brand.setForeground(UiColors.PRIMARY);
        brand.setFont(new Font(UiColors.FONT, Font.BOLD, 22));
        brand.setAlignmentX(LEFT_ALIGNMENT);
        add(brand);
        JLabel caption = new JLabel("PANEL CLÍNICO");
        caption.setForeground(UiColors.MUTED);
        caption.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        caption.setAlignmentX(LEFT_ALIGNMENT);
        add(Box.createVerticalStrut(5)); add(caption);
        add(Box.createVerticalStrut(30));
        addSection("ESPACIO DE TRABAJO");
        item("Resumen", "dashboard", navigation);
        item("Pacientes", "pacientes", navigation);
        item("Habitaciones", "habitaciones", navigation);
        item("Historial", "historial", navigation);
        add(Box.createVerticalGlue());
        JLabel footer = new JLabel("Gestor Hospitalario · Poli");
        footer.setForeground(UiColors.MUTED);
        footer.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        footer.setAlignmentX(LEFT_ALIGNMENT);
        add(footer);
    }
    private void addSection(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UiColors.MUTED);
        label.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label); add(Box.createVerticalStrut(12));
    }
    private void item(String label, String key, Consumer<String> navigation) {
        JButton button = new JButton(label);
        button.setFont(new Font(UiColors.FONT, Font.BOLD, 13));
        button.setForeground(UiColors.INK);
        button.setBackground(UiColors.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 12, 12, 8));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> navigation.accept(key));
        add(button); add(Box.createVerticalStrut(4));
    }
}
