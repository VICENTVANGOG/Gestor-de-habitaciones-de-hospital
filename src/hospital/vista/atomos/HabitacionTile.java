package hospital.vista.atomos;

import hospital.modelos.Habitacion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Átomo interactivo que representa el estado de una habitación. */
public class HabitacionTile extends JButton {
    private final Habitacion habitacion;

    public HabitacionTile(Habitacion habitacion) {
        this.habitacion = habitacion;
        setLayout(new BorderLayout(0, 5));
        setBorder(new EmptyBorder(11, 5, 10, 5));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setToolTipText("Abrir detalle de habitación " + habitacion.getNumero());

        JLabel number = new JLabel(String.format("%02d", habitacion.getNumero()), SwingConstants.CENTER);
        number.setFont(new Font(UiColors.FONT, Font.BOLD, 20));
        number.setForeground(habitacion.isOcupada() ? UiColors.DANGER : new Color(22, 151, 126));
        JLabel status = new JLabel(habitacion.isOcupada() ? "OCUPADA" : "DISPONIBLE", SwingConstants.CENTER);
        status.setFont(new Font(UiColors.FONT, Font.BOLD, 8));
        status.setForeground(habitacion.isOcupada() ? UiColors.DANGER : new Color(22, 151, 126));
        String patientName = habitacion.isOcupada() && habitacion.getPaciente() != null
                ? shorten(habitacion.getPaciente().getNombre(), 15) : "Cama libre";
        JLabel patient = new JLabel(patientName, SwingConstants.CENTER);
        patient.setFont(new Font(UiColors.FONT, Font.PLAIN, 8));
        patient.setForeground(UiColors.MUTED);
        add(number, BorderLayout.NORTH);
        add(status, BorderLayout.CENTER);
        add(patient, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(92, 82));
    }

    private String shorten(String value, int max) {
        return value.length() <= max ? value : value.substring(0, max - 1) + "…";
    }

    @Override protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        boolean occupied = habitacion.isOcupada();
        Color fill = occupied ? UiColors.DANGER_SOFT : UiColors.TEAL_SOFT;
        Color border = occupied ? new Color(248, 214, 219) : new Color(202, 237, 228);
        if (getModel().isRollover()) {
            fill = occupied ? new Color(255, 229, 232) : new Color(218, 246, 239);
            border = occupied ? UiColors.DANGER : UiColors.TEAL;
        }
        g.setColor(fill);
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
        g.setColor(border);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
        g.dispose();
        super.paintComponent(graphics);
    }
}
