package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Habitacion;
import hospital.vista.atomos.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Página independiente para asignación, liberación y consulta de habitaciones. */
public class HabitacionesPage extends JPanel {
    private final HospitalController controller;
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Habitación", "Piso", "Posición", "Estado", "Paciente"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    public HabitacionesPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 16)); setBackground(UiColors.BACKGROUND);
        setBorder(new EmptyBorder(24, 26, 26, 26));
        JLabel title = new JLabel("Habitaciones");
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 25)); title.setForeground(UiColors.INK);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0)); actions.setOpaque(false);
        UiButton assign = new UiButton("Asignar habitación");
        UiButton discharge = new UiButton("Dar salida");
        UiButton matrix = new UiButton("Ver matriz");
        UiButton refresh = new UiButton("Actualizar");
        actions.add(assign); actions.add(discharge); actions.add(matrix); actions.add(refresh);
        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false);
        top.add(title, BorderLayout.WEST); top.add(actions, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        JTable table = new JTable(model); PacientesPage.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);
        assign.addActionListener(e -> assign());
        discharge.addActionListener(e -> discharge());
        matrix.addActionListener(e -> JOptionPane.showMessageDialog(this, controller.matriz(),
                "Matriz dispersa 5 × 6", JOptionPane.INFORMATION_MESSAGE));
        refresh.addActionListener(e -> refresh());
        refresh();
    }
    private void assign() {
        JTextField id = new JTextField(); JTextField room = new JTextField();
        Object[] fields = {"Identificación del paciente", id, "Habitación (1 a 30)", room};
        if (JOptionPane.showConfirmDialog(this, fields, "Asignar habitación",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            controller.asignarHabitacion(id.getText().trim(), Integer.parseInt(room.getText().trim()));
            refresh();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo asignar",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    private void discharge() {
        String id = JOptionPane.showInputDialog(this, "Identificación del paciente");
        if (id == null || id.trim().isEmpty()) return;
        try { controller.darSalida(id.trim()); refresh(); }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo registrar salida",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    public void refresh() {
        model.setRowCount(0);
        for (Habitacion h : controller.habitaciones())
            model.addRow(new Object[]{h.getNumero(), h.getPiso(), h.getPosicion(),
                    h.isOcupada() ? "Ocupada" : "Disponible",
                    h.getPaciente() == null ? "—" : h.getPaciente().getNombre()});
    }
}
