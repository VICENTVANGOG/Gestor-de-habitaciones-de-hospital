package hospital.vista.atomos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Átomo de superficie: tarjeta blanca, radio suave y borde ligero. */
public class UiCard extends JPanel {
    private final int radius;
    public UiCard() { this(16, 18); }
    public UiCard(int radius, int padding) {
        this.radius = radius;
        setOpaque(false);
        setBorder(new EmptyBorder(padding, padding, padding, padding));
    }
    @Override protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(UiColors.WHITE);
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g.setColor(UiColors.BORDER);
        g.setStroke(new BasicStroke(1f));
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g.dispose();
        super.paintComponent(graphics);
    }
}
