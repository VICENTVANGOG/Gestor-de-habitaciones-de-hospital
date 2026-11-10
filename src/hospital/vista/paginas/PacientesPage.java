package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Paciente;
import hospital.vista.atomos.*;
import hospital.vista.moleculas.FormField;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Página de pacientes: vista separada del resto de módulos. */
public class PacientesPage extends JPanel {
    private final HospitalController controller;
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Identificación", "Nombre", "Edad", "Triage", "Motivo de ingreso"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    public PacientesPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 16)); setBackground(UiColors.BACKGROUND);
        setBorder(new EmptyBorder(24, 26, 26, 26));
        JLabel title = new JLabel("Pacientes");
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 25)); title.setForeground(UiColors.INK);
        UiButton add = new UiButton("Registrar paciente");
        JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false);
        top.add(title, BorderLayout.WEST); top.add(add, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        JTable table = new JTable(model); styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add.addActionListener(e -> register());
        refresh();
    }
    private void register() {
        FormField id = new FormField("Identificación");
        FormField name = new FormField("Nombre completo");
        FormField age = new FormField("Edad");
        FormField triage = new FormField("Nivel de triage (1 a 5)");
        FormField reason = new FormField("Motivo de ingreso");
        JPanel fields = new JPanel(new GridLayout(0, 2, 12, 10));
        fields.add(id); fields.add(name); fields.add(age); fields.add(triage); fields.add(reason);
        fields.setPreferredSize(new Dimension(480, 250));
        if (JOptionPane.showConfirmDialog(this, fields, "Nuevo paciente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;
        try {
            controller.registrarPaciente(id.value(), name.value(), Integer.parseInt(age.value()),
                    Integer.parseInt(triage.value()), reason.value());
            refresh();
            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo registrar",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    public void refresh() {
        model.setRowCount(0);
        for (Paciente p : controller.pacientes())
            model.addRow(new Object[]{p.getIdentificacion(), p.getNombre(), p.getEdad(),
                    p.getNivelTriage(), p.getMotivoIngreso()});
    }
    static void styleTable(JTable table) {
        table.setRowHeight(34);
        table.setFont(new Font(UiColors.FONT, Font.PLAIN, 13));
        table.setForeground(UiColors.INK);
        table.setGridColor(UiColors.BORDER);
        table.getTableHeader().setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(239, 243, 250));
        table.getTableHeader().setForeground(UiColors.INK);
        table.setSelectionBackground(new Color(226, 235, 255));
    }
}
