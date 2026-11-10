package hospital.vista.plantillas;

import hospital.vista.atomos.UiColors;
import hospital.vista.organismos.Sidebar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

/** Plantilla de aplicación: sidebar persistente, encabezado contextual y contenido. */
public class MainTemplate extends JPanel {
    private final JPanel cards = new JPanel(new CardLayout());
    private final Sidebar sidebar;
    private final JLabel pageTitle = new JLabel("Resumen del día");

    public MainTemplate(Consumer<String> navigation) {
        setLayout(new BorderLayout());
        setBackground(UiColors.BACKGROUND);
        sidebar = new Sidebar(navigation);
        add(sidebar, BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(UiColors.BACKGROUND);
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 72));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UiColors.BORDER));

        JPanel context = new JPanel();
        context.setOpaque(false);
        context.setLayout(new BoxLayout(context, BoxLayout.Y_AXIS));
        context.setBorder(new EmptyBorder(13, 30, 11, 10));
        JLabel clinic = new JLabel("Centro de atención hospitalaria");
        clinic.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        clinic.setForeground(UiColors.MUTED);
        pageTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 13));
        pageTitle.setForeground(UiColors.INK);
        context.add(clinic);
        context.add(Box.createVerticalStrut(4));
        context.add(pageTitle);
        header.add(context, BorderLayout.WEST);

        JPanel profile = new JPanel(new FlowLayout(FlowLayout.RIGHT, 22, 17));
        profile.setOpaque(false);
        JLabel role = new JLabel("Administrador");
        role.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        role.setForeground(UiColors.MUTED);
        JLabel avatar = new JLabel("JM", SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(224, 231, 255));
        avatar.setForeground(UiColors.PRIMARY);
        avatar.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        avatar.setPreferredSize(new Dimension(36, 36));
        profile.add(role);
        profile.add(avatar);
        header.add(profile, BorderLayout.EAST);

        right.add(header, BorderLayout.NORTH);
        cards.setBackground(UiColors.BACKGROUND);
        right.add(cards, BorderLayout.CENTER);
        add(right, BorderLayout.CENTER);
    }

    public void addPage(String key, JPanel page) {
        cards.add(page, key);
    }

    public void showPage(String key, String name) {
        ((CardLayout) cards.getLayout()).show(cards, key);
        pageTitle.setText(name);
    }

    public void setActiveSection(String key) {
        sidebar.setActive(key);
    }
}