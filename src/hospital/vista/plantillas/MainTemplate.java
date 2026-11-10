package hospital.vista.plantillas;

import hospital.vista.atomos.UiColors;
import hospital.vista.organismos.Sidebar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

/** Plantilla Atomic Design: navegación, encabezado y área de contenido. */
public class MainTemplate extends JPanel {
    private final JPanel cards = new JPanel(new CardLayout());
    private final JLabel title = new JLabel("Resumen general");

    public MainTemplate(Consumer<String> navigation) {
        setLayout(new BorderLayout());
        setBackground(UiColors.BACKGROUND);
        add(new Sidebar(navigation), BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(UiColors.BACKGROUND);
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UiColors.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UiColors.BORDER));
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 17));
        title.setForeground(UiColors.INK);
        title.setBorder(new EmptyBorder(19, 26, 19, 20));
        header.add(title, BorderLayout.CENTER);
        right.add(header, BorderLayout.NORTH);
        cards.setBackground(UiColors.BACKGROUND);
        right.add(cards, BorderLayout.CENTER);
        add(right, BorderLayout.CENTER);
    }
    public void addPage(String key, JPanel page) { cards.add(page, key); }
    public void showPage(String key, String name) {
        ((CardLayout) cards.getLayout()).show(cards, key);
        title.setText(name);
    }
}
