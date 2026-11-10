package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Habitacion;
import hospital.modelos.RegistroHistorial;
import hospital.vista.atomos.*;
import hospital.vista.moleculas.StatCard;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Dashboard dinámico: solo muestra información disponible en el modelo académico. */
public class DashboardPage extends JPanel {
    private final HospitalController controller;
    private final StatCard patients = new StatCard("PACIENTES REGISTRADOS", UiColors.PRIMARY);
    private final StatCard occupied = new StatCard("HABITACIONES OCUPADAS", UiColors.DANGER);
    private final StatCard available = new StatCard("HABITACIONES DISPONIBLES", UiColors.TEAL);
    private final StatCard occupancy = new StatCard("OCUPACIÓN ACTUAL", UiColors.INFO);
    private final JPanel floors = new JPanel();
    private final JPanel rooms = new JPanel(new GridLayout(5, 6, 7, 7));
    private final JPanel activity = new JPanel();
    private final JLabel occupancyText = new JLabel();
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd MMM · HH:mm");

    public DashboardPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setOpaque(false);
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setBorder(new EmptyBorder(16, 30, 28, 30));
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        JPanel intro = new JPanel();
        intro.setOpaque(false);
        intro.setLayout(new BoxLayout(intro, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("RESUMEN DEL DÍA  ·  GESTIÓN HOSPITALARIA");
        eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        eyebrow.setForeground(UiColors.PRIMARY);
        JLabel heading = new JLabel("Estado general del hospital");
        heading.setFont(new Font(UiColors.FONT, Font.BOLD, 27));
        heading.setForeground(UiColors.INK);
        JLabel subtitle = new JLabel("Consulta en tiempo real la ocupación y los movimientos de pacientes.");
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 12));
        subtitle.setForeground(UiColors.MUTED);
        intro.add(eyebrow); intro.add(Box.createVerticalStrut(8));
        intro.add(heading); intro.add(Box.createVerticalStrut(5)); intro.add(subtitle);
        page.add(intro); page.add(Box.createVerticalStrut(22));

        JPanel metrics = new JPanel(new GridLayout(1, 4, 13, 0));
        metrics.setOpaque(false);
        metrics.add(patients); metrics.add(occupied); metrics.add(available); metrics.add(occupancy);
        metrics.setMaximumSize(new Dimension(Integer.MAX_VALUE, 132));
        page.add(metrics); page.add(Box.createVerticalStrut(18));

        JPanel middle = new JPanel(new GridLayout(1, 2, 14, 0));
        middle.setOpaque(false);
        UiCard floorCard = new UiCard(16, 17);
        floorCard.setLayout(new BorderLayout(0, 14));
        floorCard.add(section("Ocupación por piso", "Distribución de camas ocupadas"), BorderLayout.NORTH);
        floors.setOpaque(false); floors.setLayout(new BoxLayout(floors, BoxLayout.Y_AXIS));
        floorCard.add(floors, BorderLayout.CENTER);
        UiCard mapCard = new UiCard(16, 17);
        mapCard.setLayout(new BorderLayout(0, 12));
        mapCard.add(section("Mapa de habitaciones", "Estado de las 30 habitaciones"), BorderLayout.NORTH);
        rooms.setOpaque(false);
        mapCard.add(rooms, BorderLayout.CENTER);
        middle.add(floorCard); middle.add(mapCard);
        middle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        page.add(middle); page.add(Box.createVerticalStrut(18));

        UiCard activityCard = new UiCard(16, 16);
        activityCard.setLayout(new BorderLayout(0, 12));
        activityCard.add(section("Actividad reciente", "Últimos ingresos y salidas registrados"), BorderLayout.NORTH);
        activity.setOpaque(false); activity.setLayout(new BoxLayout(activity, BoxLayout.Y_AXIS));
        activityCard.add(activity, BorderLayout.CENTER);
        activityCard.setPreferredSize(new Dimension(900, 205));
        page.add(activityCard);
        page.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                int width = page.getWidth();
                metrics.setLayout(new GridLayout(width < 620 ? 2 : 1, width < 620 ? 2 : 4, 10, 10));
                middle.setLayout(new GridLayout(width < 820 ? 2 : 1, width < 820 ? 1 : 2, 12, 12));
                page.revalidate();
            }
        });

        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        add(scroll, BorderLayout.CENTER);
        refresh();
    }
    private JPanel section(String title, String subtitle) {
        JPanel p = new JPanel();
        p.setOpaque(false); p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel t = new JLabel(title); t.setFont(new Font(UiColors.FONT, Font.BOLD, 14)); t.setForeground(UiColors.INK);
        JLabel s = new JLabel(subtitle); s.setFont(new Font(UiColors.FONT, Font.PLAIN, 10)); s.setForeground(UiColors.MUTED);
        p.add(t); p.add(Box.createVerticalStrut(4)); p.add(s);
        return p;
    }
    public void refresh() {
        List<Habitacion> all = controller.habitaciones();
        int used = (int) all.stream().filter(Habitacion::isOcupada).count();
        int free = all.size() - used;
        int pct = all.isEmpty() ? 0 : (int)Math.round(used * 100.0 / all.size());
        patients.setValue(String.valueOf(controller.pacientes().size()), "Registros en el sistema");
        occupied.setValue(String.valueOf(used), "Camas actualmente en uso");
        available.setValue(String.valueOf(free), "Disponibles para asignación");
        occupancy.setValue(pct + "%", "Nivel de ocupación");
        floors.removeAll();
        for (int floor = 1; floor <= 5; floor++) {
            final int f = floor;
            long count = all.stream().filter(h -> h.getPiso() == f && h.isOcupada()).count();
            JPanel row = new JPanel(new BorderLayout(10, 0)); row.setOpaque(false);
            JLabel name = new JLabel("Piso " + floor);
            name.setFont(new Font(UiColors.FONT, Font.BOLD, 11)); name.setForeground(UiColors.INK);
            JLabel number = new JLabel(count + " / 6"); number.setFont(new Font(UiColors.FONT, Font.BOLD, 10)); number.setForeground(UiColors.MUTED);
            JProgressBar bar = new JProgressBar(0, 6); bar.setValue((int)count); bar.setStringPainted(false);
            bar.setForeground(UiColors.TEAL); bar.setBackground(UiColors.TEAL_SOFT); bar.setBorderPainted(false);
            JPanel center = new JPanel(new BorderLayout(0, 5)); center.setOpaque(false);
            center.add(bar, BorderLayout.CENTER);
            row.add(name, BorderLayout.WEST); row.add(center, BorderLayout.CENTER); row.add(number, BorderLayout.EAST);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
            floors.add(row); floors.add(Box.createVerticalStrut(13));
        }
        rooms.removeAll();
        for (Habitacion h : all) {
            boolean busy = h.isOcupada();
            JLabel tile = new JLabel("<html><center><b>" + h.getNumero() + "</b><br><font size='2'>" +
                    (busy ? "Ocupada" : "Libre") + "</font></center></html>", SwingConstants.CENTER);
            tile.setOpaque(true);
            tile.setBackground(busy ? UiColors.DANGER_SOFT : UiColors.TEAL_SOFT);
            tile.setForeground(busy ? UiColors.DANGER : new Color(24, 139, 115));
            tile.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
            tile.setBorder(BorderFactory.createLineBorder(busy ? new Color(249, 216, 220) : new Color(204, 239, 229)));
            rooms.add(tile);
        }
        activity.removeAll();
        List<RegistroHistorial> records = controller.historial();
        int start = Math.max(0, records.size() - 4);
        if (records.isEmpty()) {
            JLabel empty = new JLabel("Aún no hay movimientos registrados.");
            empty.setFont(new Font(UiColors.FONT, Font.PLAIN, 11)); empty.setForeground(UiColors.MUTED);
            activity.add(empty);
        } else {
            for (int i = records.size() - 1; i >= start; i--) {
                RegistroHistorial r = records.get(i);
                JPanel line = new JPanel(new BorderLayout(12, 0)); line.setOpaque(false);
                JLabel dot = new JLabel("●"); dot.setForeground("Ingreso".equalsIgnoreCase(r.getAccion()) ? UiColors.TEAL : UiColors.WARNING);
                JLabel description = new JLabel("<html><b>" + r.getNombrePaciente() + "</b> · " + r.getAccion() +
                        " · Habitación " + r.getNumeroHabitacion() + "</html>");
                description.setFont(new Font(UiColors.FONT, Font.PLAIN, 11)); description.setForeground(UiColors.INK);
                JLabel time = new JLabel(r.getFechaHora().format(DATE));
                time.setFont(new Font(UiColors.FONT, Font.PLAIN, 10)); time.setForeground(UiColors.MUTED);
                line.add(dot, BorderLayout.WEST); line.add(description, BorderLayout.CENTER); line.add(time, BorderLayout.EAST);
                line.setBorder(new EmptyBorder(7, 0, 7, 0));
                activity.add(line);
            }
        }
        floors.revalidate(); floors.repaint(); rooms.revalidate(); rooms.repaint(); activity.revalidate(); activity.repaint();
    }
}
