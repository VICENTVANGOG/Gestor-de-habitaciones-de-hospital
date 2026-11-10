package hospital.vista.atomos;

import javax.swing.*;
import java.awt.*;

/** Átomo interactivo para representar una habitación en el mapa hospitalario. */
public class HabitacionTile extends JButton {
    private final int numero;
    private final int piso;
    private final boolean ocupada;
    private final String paciente;

    public HabitacionTile(int numero, int piso, boolean ocupada, String paciente) {
        this.numero = numero;
        this.piso = piso;
        this.ocupada = ocupada;
        this.paciente = paciente == null ? "" : paciente;
        setPreferredSize(new Dimension(132, 112));
        setMinimumSize(new Dimension(110, 100));
        setMaximumSize(new Dimension(180, 130));
        setFont(new Font(UiColors.FONT, Font.PLAIN, 11));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setToolTipText("Abrir detalle de habitación " + numero);
    }

    @Override protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        boolean hover = getModel().isRollover();
        Color accent = ocupada ? UiColors.DANGER : UiColors.TEAL;
        Color soft = ocupada ? UiColors.DANGER_SOFT : UiColors.TEAL_SOFT;
        g.setColor(hover ? (ocupada ? new Color(255, 235, 237) : new Color(222, 247, 240)) : Color.WHITE);
        g.fillRoundRect(1, 1, getWidth()-3, getHeight()-3, 15, 15);
        g.setColor(hover ? accent : UiColors.BORDER);
        g.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 15, 15);

        g.setColor(soft);
        g.fillRoundRect(12, 12, 36, 30, 10, 10);
        g.setColor(accent);
        g.setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        g.drawString(String.format("%02d", numero), 22, 32);

        g.setColor(UiColors.INK);
        g.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        g.drawString("Habitación " + numero, 12, 61);

        g.setColor(UiColors.MUTED);
        g.setFont(new Font(UiColors.FONT, Font.PLAIN, 9));
        String detail = ocupada ? shorten(paciente, 19) : "Lista para ingreso";
        g.drawString(detail, 12, 77);
        g.drawString("Piso " + piso, 12, 94);

        g.setColor(accent);
        g.fillOval(getWidth()-25, 14, 7, 7);
        g.dispose();
        super.paintComponent(graphics);
    }

    private String shorten(String value, int max) {
        if (value.length() <= max) return value;
        return value.substring(0, Math.max(0, max-1)) + "…";
    }
}
