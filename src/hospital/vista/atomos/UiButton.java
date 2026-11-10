package hospital.vista.atomos;

import javax.swing.*;
import java.awt.*;

/** Botón reutilizable Hospitalize con estados de reposo y hover. */
public class UiButton extends JButton {
    private final boolean primary;
    public UiButton(String text) { this(text, true); }
    public UiButton(String text, boolean primary) {
        super(text);
        this.primary = primary;
        setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        setForeground(primary ? Color.WHITE : new Color(102, 115, 143));
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(11, 16, 11, 16));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
    }
    @Override protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color fill = primary ? (getModel().isRollover() ? UiColors.PRIMARY_DARK : UiColors.PRIMARY)
                : (getModel().isRollover() ? UiColors.PRIMARY_SOFT : UiColors.WHITE);
        g.setColor(fill);
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        if (!primary) {
            g.setColor(UiColors.BORDER);
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        }
        g.dispose();
        super.paintComponent(graphics);
    }
}
