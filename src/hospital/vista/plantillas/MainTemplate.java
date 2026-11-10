package hospital.vista.plantillas;

import hospital.vista.atomos.UiColors;
import hospital.vista.organismos.Sidebar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Consumer;

/** Plantilla responsive: navegación compacta y cabecera adaptable. */
public class MainTemplate extends JPanel {
    private final JPanel cards = new JPanel(new CardLayout());
    private final Sidebar sidebar;
    private final JLabel pageTitle = new JLabel("Resumen del día");
    private final JLabel role = new JLabel("Administrador");

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
        context.setBorder(new EmptyBorder(13, 24, 11, 10));
        JLabel clinic = new JLabel("CENTRO DE ATENCIÓN HOSPITALARIA");
        clinic.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        clinic.setForeground(UiColors.MUTED);
        pageTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 14));
        pageTitle.setForeground(UiColors.INK);
        context.add(clinic);
        context.add(Box.createVerticalStrut(4));
        context.add(pageTitle);
        header.add(context, BorderLayout.WEST);

        JPanel profile = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 17));
        profile.setOpaque(false);
        role.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        role.setForeground(UiColors.MUTED);
        JLabel avatar = new JLabel("JM", SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(UiColors.PRIMARY_SOFT);
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

        addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                int width = getWidth();
                sidebar.setCompact(width < 980);
                role.setVisible(width >= 560);
                context.setBorder(new EmptyBorder(13, width < 560 ? 14 : 24, 11, 8));
                profile.setBorder(new EmptyBorder(0, 0, 0, width < 560 ? 8 : 18));
            }
        });
    }

    public void addPage(String key, JPanel page) { cards.add(page, key); }
    public void showPage(String key, String name) {
        ((CardLayout) cards.getLayout()).show(cards, key);
        pageTitle.setText(name);
    }
    public void setActiveSection(String key) { sidebar.setActive(key); }
}
