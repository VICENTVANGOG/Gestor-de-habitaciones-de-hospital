package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Habitacion;
import hospital.vista.atomos.UiColors;
import hospital.vista.moleculas.StatCard;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/** Dashboard clínico inspirado en Hospitalize; métricas derivadas del modelo real. */
public class DashboardPage extends JPanel {
    private final HospitalController controller;
    private final StatCard total = new StatCard("Habitaciones totales", UiColors.PRIMARY);
    private final StatCard occupied = new StatCard("Habitaciones ocupadas", UiColors.DANGER);
    private final StatCard available = new StatCard("Habitaciones disponibles", UiColors.TEAL);
    private final StatCard occupancy = new StatCard("Ocupación actual", UiColors.INFO);
    private final JLabel summary = new JLabel();
    private final JProgressBar progress = new JProgressBar(0, 100);
    private final JPanel floorRows = new JPanel();

    public DashboardPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 22));
        setBackground(UiColors.BACKGROUND);
        setBorder(new EmptyBorder(24, 26, 26, 26));

        JPanel intro = new JPanel();
        intro.setOpaque(false);
        intro.setLayout(new BoxLayout(intro, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("RESUMEN DEL DÍA");
        eyebrow.setForeground(UiColors.PRIMARY);
        eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        JLabel title = new JLabel("Resumen general");
        title.setForeground(UiColors.INK);
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 27));
        JLabel subtitle = new JLabel("Estado actualizado de pacientes y habitaciones del hospital.");
        subtitle.setForeground(UiColors.MUTED);
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 13));
        intro.add(eyebrow); intro.add(Box.createVerticalStrut(8));
        intro.add(title); intro.add(Box.createVerticalStrut(6)); intro.add(subtitle);
        add(intro, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel metrics = new JPanel(new GridLayout(1, 4, 14, 0));
        metrics.setOpaque(false);
        metrics.add(total); metrics.add(occupied); metrics.add(available); metrics.add(occupancy);
        content.add(metrics);
        content.add(Box.createVerticalStrut(18));

        JPanel status = cardPanel();
        status.setLayout(new BorderLayout(0, 14));
        JLabel statusTitle = sectionTitle("Estado de camas");
        status.add(statusTitle, BorderLayout.NORTH);
        JPanel statusBody = new JPanel(new BorderLayout(0, 8));
        statusBody.setOpaque(false);
        summary.setFont(new Font(UiColors.FONT, Font.PLAIN, 13));
        summary.setForeground(UiColors.MUTED);
        progress.setStringPainted(true);
        progress.setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        progress.setForeground(UiColors.PRIMARY);
        progress.setBackground(UiColors.BORDER);
        progress.setBorderPainted(false);
        statusBody.add(summary, BorderLayout.NORTH);
        statusBody.add(progress, BorderLayout.CENTER);
        status.add(statusBody, BorderLayout.CENTER);
        content.add(status);
        content.add(Box.createVerticalStrut(18));

        JPanel floors = cardPanel();
        floors.setLayout(new BorderLayout(0, 12));
        floors.add(sectionTitle("Ocupación por piso"), BorderLayout.NORTH);
        floorRows.setOpaque(false);
        floorRows.setLayout(new BoxLayout(floorRows, BoxLayout.Y_AXIS));
        floors.add(floorRows, BorderLayout.CENTER);
        content.add(floors);
        add(content, BorderLayout.CENTER);
        refresh();
    }

    private JPanel cardPanel() {
        JPanel p = new JPanel();
        p.setOpaque(true);
        p.setBackground(UiColors.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER), new EmptyBorder(18, 20, 18, 20)));
        return p;
    }
    private JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(UiColors.FONT, Font.BOLD, 16));
        l.setForeground(UiColors.INK);
        return l;
    }

    public void refresh() {
        List<Habitacion> rooms = controller.habitaciones();
        int used = (int) rooms.stream().filter(Habitacion::isOcupada).count();
        int free = rooms.size() - used;
        int percent = rooms.isEmpty() ? 0 : (int) Math.round(used * 100.0 / rooms.size());
        total.setValue(String.valueOf(rooms.size()), "Capacidad instalada");
        occupied.setValue(String.valueOf(used), "Camas en uso");
        available.setValue(String.valueOf(free), "Disponibles ahora");
        occupancy.setValue(percent + "%", "Porcentaje de ocupación");
        summary.setText(used + " de " + rooms.size() + " habitaciones ocupadas · " + free + " disponibles");
        progress.setValue(percent);
        progress.setString(percent + "% ocupado");
        floorRows.removeAll();
        for (int floor = 1; floor <= 5; floor++) {
            final int currentFloor = floor;
            long floorUsed = rooms.stream().filter(h -> h.getPiso() == currentFloor && h.isOcupada()).count();
            JPanel row = new JPanel(new BorderLayout(12, 0));
            row.setOpaque(false);
            JLabel label = new JLabel("Piso " + floor + "  ·  " + floorUsed + "/6");
            label.setPreferredSize(new Dimension(120, 28));
            label.setFont(new Font(UiColors.FONT, Font.PLAIN, 13));
            label.setForeground(UiColors.INK);
            JProgressBar bar = new JProgressBar(0, 6);
            bar.setValue((int) floorUsed);
            bar.setStringPainted(false);
            bar.setForeground(UiColors.TEAL);
            bar.setBackground(UiColors.BORDER);
            row.add(label, BorderLayout.WEST); row.add(bar, BorderLayout.CENTER);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
            floorRows.add(row);
            floorRows.add(Box.createVerticalStrut(8));
        }
        floorRows.revalidate(); floorRows.repaint();
    }
}
