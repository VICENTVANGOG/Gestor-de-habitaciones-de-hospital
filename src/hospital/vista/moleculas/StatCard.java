package hospital.vista.moleculas;

import hospital.vista.atomos.UiColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Molécula de indicador con icono, cifra y descripción. */
public class StatCard extends JPanel {
    private final JLabel value = new JLabel("—");
    private final JLabel detail = new JLabel(" ");
    private final JLabel icon = new JLabel("●", SwingConstants.CENTER);
    public StatCard(String title, Color accent) {
        setOpaque(false);
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(15, 16, 14, 16));
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel heading = new JLabel(title);
        heading.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        heading.setForeground(UiColors.MUTED);
        icon.setOpaque(true);
        icon.setBackground(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 22));
        icon.setForeground(accent);
        icon.setFont(new Font(UiColors.FONT, Font.BOLD, 15));
        icon.setPreferredSize(new Dimension(34, 34));
        top.add(heading, BorderLayout.CENTER);
        top.add(icon, BorderLayout.EAST);
        value.setFont(new Font(UiColors.FONT, Font.BOLD, 29));
        value.setForeground(UiColors.INK);
        detail.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        detail.setForeground(UiColors.MUTED);
        add(top, BorderLayout.NORTH);
        add(value, BorderLayout.CENTER);
        add(detail, BorderLayout.SOUTH);
    }
    @Override protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(UiColors.WHITE);
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
        g.setColor(UiColors.BORDER);
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
        g.dispose();
        super.paintComponent(graphics);
    }
    public void setValue(String text, String description) {
        value.setText(text);
        detail.setText(description);
    }
}
