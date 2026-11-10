package hospital.vista.moleculas;

import hospital.modelos.Habitacion;
import hospital.modelos.RegistroHistorial;
import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Diálogo de detalle: información actual e historial exclusivo de una habitación. */
public class HabitacionDetailDialog extends JDialog {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy · HH:mm");
    public HabitacionDetailDialog(Window owner, Habitacion room, List<RegistroHistorial> records) {
        super(owner, "Detalle de habitación " + room.getNumero(), ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(570, 650);
        setMinimumSize(new Dimension(520, 570));
        setLocationRelativeTo(owner);

        JPanel root = new JPanel(new BorderLayout(0, 18));
        root.setBackground(UiColors.BACKGROUND);
        root.setBorder(new EmptyBorder(24, 26, 22, 26));
        setContentPane(root);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("INFORMACIÓN DE HABITACIÓN");
        eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title = new JLabel("Habitación " + room.getNumero());
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 25));
        title.setForeground(UiColors.INK);
        JLabel subtitle = new JLabel("Piso " + room.getPiso() + "  ·  Posición " + room.getPosicion());
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 11));
        subtitle.setForeground(UiColors.MUTED);
        heading.add(eyebrow); heading.add(Box.createVerticalStrut(6));
        heading.add(title); heading.add(Box.createVerticalStrut(4)); heading.add(subtitle);
        root.add(heading, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JPanel current = new JPanel(new BorderLayout(12, 0));
        current.setBackground(Color.WHITE);
        current.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER),
                new EmptyBorder(17, 18, 17, 18)));
        JPanel statusBlock = new JPanel();
        statusBlock.setOpaque(false);
        statusBlock.setLayout(new BoxLayout(statusBlock, BoxLayout.Y_AXIS));
        JLabel statusLabel = new JLabel(room.isOcupada() ? "OCUPADA" : "DISPONIBLE");
        statusLabel.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        statusLabel.setForeground(room.isOcupada() ? UiColors.DANGER : UiColors.TEAL);
        JLabel patientName = new JLabel(room.isOcupada() ? room.getPaciente().getNombre() : "Sin paciente asignado");
        patientName.setFont(new Font(UiColors.FONT, Font.BOLD, 15));
        patientName.setForeground(UiColors.INK);
        statusBlock.add(statusLabel); statusBlock.add(Box.createVerticalStrut(7)); statusBlock.add(patientName);
        current.add(statusBlock, BorderLayout.CENTER);
        if (room.isOcupada()) {
            JLabel id = new JLabel("CC  " + room.getPaciente().getIdentificacion());
            id.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
            id.setForeground(UiColors.MUTED);
            current.add(id, BorderLayout.SOUTH);
        }
        body.add(current);
        body.add(Box.createVerticalStrut(20));

        JPanel historyHeader = new JPanel(new BorderLayout());
        historyHeader.setOpaque(false);
        JLabel historyTitle = new JLabel("Historial de esta habitación");
        historyTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 14));
        historyTitle.setForeground(UiColors.INK);
        JLabel total = new JLabel(records.size() + " movimientos");
        total.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        total.setForeground(UiColors.MUTED);
        historyHeader.add(historyTitle, BorderLayout.WEST);
        historyHeader.add(total, BorderLayout.EAST);
        body.add(historyHeader);
        body.add(Box.createVerticalStrut(10));

        JPanel timeline = new JPanel();
        timeline.setBackground(Color.WHITE);
        timeline.setBorder(new EmptyBorder(14, 16, 14, 16));
        timeline.setLayout(new BoxLayout(timeline, BoxLayout.Y_AXIS));
        if (records.isEmpty()) {
            JLabel empty = new JLabel("<html><div style='text-align:center;padding:18px'>Todavía no hay movimientos registrados para esta habitación.</div></html>");
            empty.setFont(new Font(UiColors.FONT, Font.PLAIN, 11));
            empty.setForeground(UiColors.MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            timeline.add(empty);
        } else {
            for (int i = records.size()-1; i >= 0; i--) {
                RegistroHistorial record = records.get(i);
                JPanel item = new JPanel(new BorderLayout(12, 4));
                item.setOpaque(false);
                item.setBorder(new EmptyBorder(9, 0, 11, 0));
                JLabel dot = new JLabel("●");
                dot.setFont(new Font(UiColors.FONT, Font.BOLD, 15));
                dot.setForeground("INGRESO".equalsIgnoreCase(record.getAccion()) ? UiColors.TEAL : UiColors.INFO);
                item.add(dot, BorderLayout.WEST);
                JPanel info = new JPanel();
                info.setOpaque(false);
                info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
                JLabel action = new JLabel(record.getAccion().equalsIgnoreCase("INGRESO") ? "Ingreso registrado" : "Salida registrada");
                action.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
                action.setForeground(UiColors.INK);
                JLabel patient = new JLabel(record.getNombrePaciente() + "  ·  " + record.getIdentificacionPaciente());
                patient.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
                patient.setForeground(UiColors.MUTED);
                JLabel date = new JLabel(record.getFechaHora().format(FORMAT));
                date.setFont(new Font(UiColors.FONT, Font.PLAIN, 9));
                date.setForeground(UiColors.MUTED);
                info.add(action); info.add(Box.createVerticalStrut(4)); info.add(patient);
                info.add(Box.createVerticalStrut(3)); info.add(date);
                item.add(info, BorderLayout.CENTER);
                timeline.add(item);
                if (i > 0) {
                    JSeparator separator = new JSeparator();
                    separator.setForeground(UiColors.BORDER);
                    timeline.add(separator);
                }
            }
        }
        JScrollPane scroll = new JScrollPane(timeline);
        scroll.setBorder(BorderFactory.createLineBorder(UiColors.BORDER));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        body.add(scroll);
        root.add(body, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.setOpaque(false);
        UiButton close = new UiButton("Cerrar", false);
        close.addActionListener(e -> dispose());
        footer.add(close);
        root.add(footer, BorderLayout.SOUTH);
    }
}
