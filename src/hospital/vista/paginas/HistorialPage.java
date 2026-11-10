package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.RegistroHistorial;
import hospital.vista.atomos.UiButton;
import hospital.vista.atomos.UiColors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/** Consulta cronológica de movimientos de ingreso y salida. */
public class HistorialPage extends JPanel {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final HospitalController controller;
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Fecha y hora", "Movimiento", "Habitación", "Paciente", "Identificación"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    public HistorialPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 16)); setBackground(UiColors.BACKGROUND);
        setBorder(new EmptyBorder(24, 26, 26, 26));
        JLabel title = new JLabel("Historial hospitalario");
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 25)); title.setForeground(UiColors.INK);
        UiButton refresh = new UiButton("Actualizar");
        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false);
        top.add(title, BorderLayout.WEST); top.add(refresh, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        JTable table = new JTable(model); PacientesPage.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);
        refresh.addActionListener(e -> refresh());
        refresh();
    }
    public void refresh() {
        model.setRowCount(0);
        for (RegistroHistorial r : controller.historial())
            model.addRow(new Object[]{r.getFechaHora().format(FORMAT), r.getAccion(),
                    r.getNumeroHabitacion(), r.getNombrePaciente(), r.getIdentificacionPaciente()});
    }
}
