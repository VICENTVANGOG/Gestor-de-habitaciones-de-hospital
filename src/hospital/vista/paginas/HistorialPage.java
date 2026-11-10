package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.RegistroHistorial;
import hospital.vista.atomos.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/** Página de historial con una tabla limpia y orden cronológico. */
public class HistorialPage extends JPanel {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm");
    private final HospitalController controller;
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Fecha y hora", "Movimiento", "Habitación", "Paciente", "Identificación"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JLabel count = new JLabel();
    public HistorialPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout()); setOpaque(false);
        JPanel page = new JPanel(new BorderLayout(0, 18)); page.setOpaque(false);
        page.setBorder(new EmptyBorder(16, 30, 28, 30));
        JPanel heading = new JPanel(new BorderLayout()); heading.setOpaque(false);
        JPanel labels = new JPanel(); labels.setOpaque(false); labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("TRAZABILIDAD"); eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 9)); eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title = new JLabel("Historial hospitalario"); title.setFont(new Font(UiColors.FONT, Font.BOLD, 26)); title.setForeground(UiColors.INK);
        JLabel subtitle = new JLabel("Registro cronológico de ingresos y salidas de pacientes.");
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 11)); subtitle.setForeground(UiColors.MUTED);
        labels.add(eyebrow); labels.add(Box.createVerticalStrut(6)); labels.add(title); labels.add(Box.createVerticalStrut(4)); labels.add(subtitle);
        UiButton refresh = new UiButton("Actualizar", false);
        heading.add(labels, BorderLayout.CENTER); heading.add(refresh, BorderLayout.EAST);
        page.add(heading, BorderLayout.NORTH);

        UiCard card = new UiCard(16, 16); card.setLayout(new BorderLayout(0, 14));
        JPanel toolbar = new JPanel(new BorderLayout()); toolbar.setOpaque(false);
        JLabel tableTitle = new JLabel("Movimientos registrados"); tableTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 14)); tableTitle.setForeground(UiColors.INK);
        count.setFont(new Font(UiColors.FONT, Font.PLAIN, 10)); count.setForeground(UiColors.MUTED);
        JPanel titleBox = new JPanel(); titleBox.setOpaque(false); titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(tableTitle); titleBox.add(Box.createVerticalStrut(4)); titleBox.add(count);
        toolbar.add(titleBox, BorderLayout.WEST);
        JTable table = new JTable(model); PacientesPage.styleTable(table);
        JScrollPane scroll = new JScrollPane(table); scroll.setBorder(BorderFactory.createLineBorder(UiColors.BORDER));
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(toolbar, BorderLayout.NORTH); card.add(scroll, BorderLayout.CENTER);
        page.add(card, BorderLayout.CENTER);
        add(page, BorderLayout.CENTER);
        refresh.addActionListener(e -> refresh());
        refresh();
    }
    public void refresh() {
        model.setRowCount(0);
        for (RegistroHistorial r : controller.historial())
            model.addRow(new Object[]{r.getFechaHora().format(FORMAT), r.getAccion(),
                    "Habitación " + r.getNumeroHabitacion(), r.getNombrePaciente(), r.getIdentificacionPaciente()});
        count.setText(model.getRowCount() + " movimientos registrados");
    }
}
