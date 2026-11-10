package hospital.vista.moleculas;

import hospital.vista.atomos.UiColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Molécula visual reutilizable para indicadores del dashboard. */
public class StatCard extends JPanel {
    private final JLabel value = new JLabel("—");
    private final JLabel detail = new JLabel(" ");

    public StatCard(String title, Color accent) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UiColors.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER),
                new EmptyBorder(18, 20, 18, 20)));
        JLabel heading = new JLabel(title);
        heading.setFont(new Font(UiColors.FONT, Font.BOLD, 13));
        heading.setForeground(UiColors.MUTED);
        value.setFont(new Font(UiColors.FONT, Font.BOLD, 30));
        value.setForeground(accent);
        detail.setFont(new Font(UiColors.FONT, Font.PLAIN, 12));
        detail.setForeground(UiColors.MUTED);
        add(heading); add(Box.createVerticalStrut(12)); add(value);
        add(Box.createVerticalStrut(5)); add(detail);
    }
    public void setValue(String text, String description) {
        value.setText(text);
        detail.setText(description);
    }
}
