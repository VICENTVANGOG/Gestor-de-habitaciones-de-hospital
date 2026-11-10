package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Habitacion;
import hospital.vista.atomos.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Mapa visual de las 30 habitaciones con asignación y salida. */
public class HabitacionesPage extends JPanel {
    private final HospitalController controller;
    private final JPanel grid = new JPanel(new GridLayout(5, 6, 10, 10));
    private final JLabel summary = new JLabel();
    public HabitacionesPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout()); setOpaque(false);
        JPanel page = new JPanel(new BorderLayout(0, 18)); page.setOpaque(false);
        page.setBorder(new EmptyBorder(16, 30, 28, 30));
        JPanel heading = new JPanel(new BorderLayout()); heading.setOpaque(false);
        JPanel labels = new JPanel(); labels.setOpaque(false); labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("DISTRIBUCIÓN HOSPITALARIA"); eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 9)); eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title = new JLabel("Habitaciones"); title.setFont(new Font(UiColors.FONT, Font.BOLD, 26)); title.setForeground(UiColors.INK);
        JLabel subtitle = new JLabel("Visualiza la disponibilidad y administra la asignación de camas.");
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 11)); subtitle.setForeground(UiColors.MUTED);
        labels.add(eyebrow); labels.add(Box.createVerticalStrut(6)); labels.add(title); labels.add(Box.createVerticalStrut(4)); labels.add(subtitle);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4)); actions.setOpaque(false);
        UiButton assign = new UiButton("+  Asignar paciente");
        UiButton discharge = new UiButton("Dar salida", false);
        UiButton matrix = new UiButton("Ver matriz", false);
        actions.add(assign); actions.add(discharge); actions.add(matrix);
        heading.add(labels, BorderLayout.CENTER); heading.add(actions, BorderLayout.EAST);
        page.add(heading, BorderLayout.NORTH);

        UiCard map = new UiCard(16, 18); map.setLayout(new BorderLayout(0, 16));
        JPanel mapHead = new JPanel(new BorderLayout()); mapHead.setOpaque(false);
        JLabel mapTitle = new JLabel("Mapa de camas"); mapTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 14)); mapTitle.setForeground(UiColors.INK);
        summary.setFont(new Font(UiColors.FONT, Font.PLAIN, 10)); summary.setForeground(UiColors.MUTED);
        mapHead.add(mapTitle, BorderLayout.WEST); mapHead.add(summary, BorderLayout.EAST);
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0)); legend.setOpaque(false);
        legend.add(legendItem(UiColors.TEAL, "Disponible")); legend.add(legendItem(UiColors.DANGER, "Ocupada"));
        JPanel top = new JPanel(); top.setOpaque(false); top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(mapHead); top.add(Box.createVerticalStrut(10)); top.add(legend);
        map.add(top, BorderLayout.NORTH);
        grid.setOpaque(false); map.add(grid, BorderLayout.CENTER);
        page.add(map, BorderLayout.CENTER);
        add(page, BorderLayout.CENTER);
        assign.addActionListener(e -> assign());
        discharge.addActionListener(e -> discharge());
        matrix.addActionListener(e -> JOptionPane.showMessageDialog(this, controller.matriz(), "Matriz dispersa 5 × 6", JOptionPane.INFORMATION_MESSAGE));
        refresh();
    }
    private JPanel legendItem(Color color, String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0)); p.setOpaque(false);
        JLabel dot = new JLabel("●"); dot.setForeground(color); dot.setFont(new Font(UiColors.FONT, Font.BOLD, 13));
        JLabel label = new JLabel(text); label.setFont(new Font(UiColors.FONT, Font.PLAIN, 10)); label.setForeground(UiColors.MUTED);
        p.add(dot); p.add(label); return p;
    }
    private void assign() {
        JTextField id = new JTextField(); JTextField room = new JTextField();
        Object[] fields = {"Identificación del paciente", id, "Número de habitación (1–30)", room};
        if (JOptionPane.showConfirmDialog(this, fields, "Asignar habitación", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try { controller.asignarHabitacion(id.getText().trim(), Integer.parseInt(room.getText().trim())); refresh(); }
        catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo asignar", JOptionPane.WARNING_MESSAGE); }
    }
    private void discharge() {
        String id = JOptionPane.showInputDialog(this, "Identificación del paciente:");
        if (id == null || id.trim().isEmpty()) return;
        try { controller.darSalida(id.trim()); refresh(); }
        catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo registrar la salida", JOptionPane.WARNING_MESSAGE); }
    }
    public void refresh() {
        grid.removeAll();
        int used = 0;
        for (Habitacion h : controller.habitaciones()) {
            boolean busy = h.isOcupada(); if (busy) used++;
            JButton tile = new JButton("<html><center><b>HAB. " + h.getNumero() + "</b><br><font size='2'>" +
                    (busy ? h.getPaciente().getNombre() : "Disponible") + "</font><br><font size='2'>Piso " + h.getPiso() + "</font></center></html>");
            tile.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
            tile.setForeground(busy ? UiColors.DANGER : new Color(28, 137, 113));
            tile.setBackground(busy ? UiColors.DANGER_SOFT : UiColors.TEAL_SOFT);
            tile.setOpaque(true); tile.setFocusPainted(false);
            tile.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(busy ? new Color(248, 214, 219) : new Color(204, 238, 229)),
                    new EmptyBorder(10, 4, 10, 4)));
            tile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            tile.setToolTipText(h.toString());
            grid.add(tile);
        }
        summary.setText(used + " ocupadas  ·  " + (controller.habitaciones().size() - used) + " disponibles");
        grid.revalidate(); grid.repaint();
    }
}
