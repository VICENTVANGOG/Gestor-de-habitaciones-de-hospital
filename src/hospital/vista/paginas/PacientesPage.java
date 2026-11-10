package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Paciente;
import hospital.vista.atomos.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/** Página de pacientes con tabla, búsqueda y formulario de registro. */
public class PacientesPage extends JPanel {
    private final HospitalController controller;
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Identificación", "Nombre completo", "Edad", "Triage", "Motivo de ingreso"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    private final JTextField search = new JTextField();
    private final JLabel count = new JLabel("0 pacientes");
    public PacientesPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout()); setOpaque(false);
        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setOpaque(false); page.setBorder(new EmptyBorder(16, 30, 28, 30));
        JPanel heading = new JPanel(new BorderLayout()); heading.setOpaque(false);
        JPanel labels = new JPanel(); labels.setOpaque(false); labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("DIRECTORIO CLÍNICO"); eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 9)); eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title = new JLabel("Pacientes"); title.setFont(new Font(UiColors.FONT, Font.BOLD, 26)); title.setForeground(UiColors.INK);
        JLabel subtitle = new JLabel("Consulta y administra los registros de pacientes hospitalizados.");
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 11)); subtitle.setForeground(UiColors.MUTED);
        labels.add(eyebrow); labels.add(Box.createVerticalStrut(6)); labels.add(title); labels.add(Box.createVerticalStrut(4)); labels.add(subtitle);
        UiButton add = new UiButton("+  Registrar paciente");
        heading.add(labels, BorderLayout.CENTER); heading.add(add, BorderLayout.EAST);
        page.add(heading, BorderLayout.NORTH);

        UiCard card = new UiCard(16, 16); card.setLayout(new BorderLayout(0, 14));
        JPanel toolbar = new JPanel(new BorderLayout(12, 0)); toolbar.setOpaque(false);
        JLabel tableTitle = new JLabel("Listado de pacientes"); tableTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 14)); tableTitle.setForeground(UiColors.INK);
        count.setFont(new Font(UiColors.FONT, Font.PLAIN, 10)); count.setForeground(UiColors.MUTED);
        JPanel titleBox = new JPanel(); titleBox.setOpaque(false); titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(tableTitle); titleBox.add(Box.createVerticalStrut(4)); titleBox.add(count);
        search.setFont(new Font(UiColors.FONT, Font.PLAIN, 11)); search.setPreferredSize(new Dimension(240, 36));
        search.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UiColors.BORDER), new EmptyBorder(7, 10, 7, 10)));
        search.setToolTipText("Buscar por identificación, nombre o motivo");
        toolbar.add(titleBox, BorderLayout.WEST); toolbar.add(search, BorderLayout.EAST);
        table.setRowSorter(sorter); styleTable(table);
        JScrollPane scroll = new JScrollPane(table); scroll.setBorder(BorderFactory.createLineBorder(UiColors.BORDER));
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(toolbar, BorderLayout.NORTH); card.add(scroll, BorderLayout.CENTER);
        page.add(card, BorderLayout.CENTER);
        add(page, BorderLayout.CENTER);
        add.addActionListener(e -> register());
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filter() { sorter.setRowFilter(search.getText().trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(search.getText().trim()))); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });
        refresh();
    }
    private void register() {
        JTextField id = field(), name = field(), age = field(), triage = field(), reason = field();
        JPanel fields = new JPanel(new GridLayout(0, 2, 12, 12)); fields.setBackground(Color.WHITE);
        fields.add(labelField("Identificación", id)); fields.add(labelField("Nombre completo", name));
        fields.add(labelField("Edad", age)); fields.add(labelField("Nivel de triage (1–5)", triage));
        fields.add(labelField("Motivo de ingreso", reason));
        if (JOptionPane.showConfirmDialog(this, fields, "Registrar nuevo paciente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;
        try {
            controller.registrarPaciente(id.getText(), name.getText(), Integer.parseInt(age.getText().trim()),
                    Integer.parseInt(triage.getText().trim()), reason.getText());
            refresh();
            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo registrar", JOptionPane.WARNING_MESSAGE);
        }
    }
    private JTextField field() { JTextField f = new JTextField(16); f.setFont(new Font(UiColors.FONT, Font.PLAIN, 12)); return f; }
    private JPanel labelField(String text, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(0, 5)); p.setOpaque(false);
        JLabel l = new JLabel(text); l.setFont(new Font(UiColors.FONT, Font.BOLD, 10)); l.setForeground(UiColors.MUTED);
        p.add(l, BorderLayout.NORTH); p.add(field, BorderLayout.CENTER); return p;
    }
    public void refresh() {
        model.setRowCount(0);
        for (Paciente p : controller.pacientes())
            model.addRow(new Object[]{p.getIdentificacion(), p.getNombre(), p.getEdad(), p.getNivelTriage(), p.getMotivoIngreso()});
        count.setText(model.getRowCount() + (model.getRowCount() == 1 ? " paciente registrado" : " pacientes registrados"));
    }
    static void styleTable(JTable table) {
        table.setRowHeight(43); table.setFont(new Font(UiColors.FONT, Font.PLAIN, 11));
        table.setForeground(UiColors.INK); table.setSelectionBackground(UiColors.PRIMARY_SOFT);
        table.setSelectionForeground(UiColors.INK); table.setGridColor(new Color(239, 243, 249));
        table.setShowVerticalLines(false); table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        table.getTableHeader().setBackground(new Color(249, 251, 254));
        table.getTableHeader().setForeground(UiColors.MUTED);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UiColors.BORDER));
    }
}
