package hospital.vista.plantillas;

import hospital.vista.atomos.UiColors;
import hospital.vista.organismos.Sidebar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

/** Plantilla principal: navegación lateral, barra contextual y páginas MVC. */
public class MainTemplate extends JPanel {
    private final JPanel cards = new JPanel(new CardLayout());
    private final Sidebar sidebar;

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
        context.setBorder(new EmptyBorder(17, 30, 14, 10));
        JLabel clinic = new JLabel("Centro de atención hospitalaria");
        clinic.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        clinic.setForeground(new Color(56, 74, 102));
        JLabel subtitle = new JLabel("Panel de gestión clínica");
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 9));
        subtitle.setForeground(new Color(153, 166, 184));
        context.add(clinic);
        context.add(Box.createVerticalStrut(3));
        context.add(subtitle);
        header.add(context, BorderLayout.WEST);

        JLabel avatar = new JLabel("JM", SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(UiColors.PRIMARY_SOFT);
        avatar.setForeground(UiColors.PRIMARY);
        avatar.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        avatar.setPreferredSize(new Dimension(36, 36));
        JPanel profile = new JPanel(new FlowLayout(FlowLayout.RIGHT, 28, 18));
        profile.setOpaque(false);
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
    }

    public void setActiveSection(String key) {
        sidebar.setActive(key);
    }
}
