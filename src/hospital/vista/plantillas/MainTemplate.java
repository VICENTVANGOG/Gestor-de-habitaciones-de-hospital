package hospital.vista.plantillas;

import hospital.vista.atomos.UiColors;
import hospital.vista.organismos.Sidebar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

/** Plantilla principal: sidebar, topbar y contenedor de páginas MVC. */
public class MainTemplate extends JPanel {
    private final JPanel cards = new JPanel(new CardLayout());
    private final JLabel title = new JLabel("Resumen del día");
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
        header.setBackground(new Color(255, 255, 255));
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
        context.add(clinic); context.add(Box.createVerticalStrut(3)); context.add(subtitle);
        header.add(context, BorderLayout.WEST);

        JPanel tools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 16));
        tools.setOpaque(false);
        JTextField search = new JTextField("Buscar en el módulo…");
        search.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        search.setForeground(UiColors.MUTED);
        search.setBackground(UiColors.SURFACE);
        search.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER),
                new EmptyBorder(8, 12, 8, 12)));
        search.setPreferredSize(new Dimension(230, 38));
        JLabel bell = new JLabel("♧", SwingConstants.CENTER);
        bell.setOpaque(true); bell.setBackground(UiColors.WHITE);
        bell.setForeground(UiColors.MUTED); bell.setBorder(BorderFactory.createLineBorder(UiColors.BORDER));
        bell.setPreferredSize(new Dimension(38, 38));
        JLabel avatar = new JLabel("JM", SwingConstants.CENTER);
        avatar.setOpaque(true); avatar.setBackground(UiColors.PRIMARY_SOFT);
        avatar.setForeground(UiColors.PRIMARY); avatar.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        avatar.setPreferredSize(new Dimension(36, 36));
        tools.add(search); tools.add(bell); tools.add(avatar);
        header.add(tools, BorderLayout.EAST);
        right.add(header, BorderLayout.NORTH);

        JPanel pageHeader = new JPanel(new BorderLayout());
        pageHeader.setOpaque(false);
        pageHeader.setBorder(new EmptyBorder(14, 30, 0, 30));
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 13));
        title.setForeground(UiColors.INK);
        pageHeader.add(title, BorderLayout.WEST);
        cards.setBackground(UiColors.BACKGROUND);
        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        body.add(pageHeader, BorderLayout.NORTH);
        body.add(cards, BorderLayout.CENTER);
        right.add(body, BorderLayout.CENTER);
        add(right, BorderLayout.CENTER);
    }
    public void addPage(String key, JPanel page) { cards.add(page, key); }
    public void showPage(String key, String name) {
        ((CardLayout) cards.getLayout()).show(cards, key);
        title.setText(name);
    }
    public void setActiveSection(String key) { sidebar.setActive(key); }
}
