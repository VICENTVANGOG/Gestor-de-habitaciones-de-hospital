package hospital.vista.moleculas;

import hospital.modelos.Habitacion;
import hospital.modelos.RegistroHistorial;
import hospital.modelos.Paciente;
import hospital.vista.atomos.UiColors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** Diálogo de detalle: paciente actual y trazabilidad histórica de una habitación. */
public class HabitacionDetailDialog extends JDialog {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd MMM yyyy · HH:mm");

    public HabitacionDetailDialog(Window owner, Habitacion room, List<RegistroHistorial> allHistory) {
        super(owner, "Detalle de habitación " + room.getNumero(), ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(560, 570);
        setMinimumSize(new Dimension(500, 480));
        setLocationRelativeTo(owner);

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(UiColors.BACKGROUND);
        root.setBorder(new EmptyBorder(22, 24, 20, 24));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("INFORMACIÓN DE HABITACIÓN");
        eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title = new JLabel("Habitación " + room.getNumero());
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 24));
        title.setForeground(UiColors.INK);
        JLabel subtitle = new JLabel("Piso " + room.getPiso() + "  ·  Posición " + room.getPosicion());
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 11));
        subtitle.setForeground(UiColors.MUTED);
        heading.add(eyebrow);
        heading.add(Box.createVerticalStrut(5));
        heading.add(title);
        heading.add(Box.createVerticalStrut(3));
        heading.add(subtitle);

        JPanel current = new JPanel(new BorderLayout(0, 10));
        current.setBackground(Color.WHITE);
        current.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER),
                new EmptyBorder(15, 16, 15, 16)));
        JLabel currentTitle = new JLabel("Paciente asignado actualmente");
        currentTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        currentTitle.setForeground(UiColors.INK);
        current.add(currentTitle, BorderLayout.NORTH);
        Paciente patient = room.getPaciente();
        JPanel patientInfo = new JPanel();
        patientInfo.setOpaque(false);
        patientInfo.setLayout(new BoxLayout(patientInfo, BoxLayout.Y_AXIS));
        if (patient == null) {
            JLabel available = new JLabel("Esta habitación se encuentra disponible.");
            available.setFont(new Font(UiColors.FONT, Font.PLAIN, 11));
            available.setForeground(new Color(22, 151, 126));
            patientInfo.add(available);
        } else {
            addInfo(patientInfo, "Nombre", patient.getNombre());
            addInfo(patientInfo, "Identificación", patient.getIdentificacion());
            addInfo(patientInfo, "Edad", patient.getEdad() + " años");
            addInfo(patientInfo, "Triage", "Nivel " + patient.getNivelTriage());
            addInfo(patientInfo, "Motivo de ingreso", patient.getMotivoIngreso());
        }
        current.add(patientInfo, BorderLayout.CENTER);

        List<RegistroHistorial> roomHistory = allHistory.stream()
                .filter(entry -> entry.getNumeroHabitacion() == room.getNumero())
                .sorted(Comparator.comparing(RegistroHistorial::getFechaHora).reversed())
                .collect(Collectors.toList());

        JPanel historyCard = new JPanel(new BorderLayout(0, 10));
        historyCard.setBackground(Color.WHITE);
        historyCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER),
                new EmptyBorder(15, 16, 14, 16)));
        JPanel historyHeading = new JPanel(new BorderLayout());
        historyHeading.setOpaque(false);
        JLabel historyTitle = new JLabel("Historial de esta habitación");
        historyTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        historyTitle.setForeground(UiColors.INK);
        JLabel total = new JLabel(roomHistory.size() + " movimientos");
        total.setFont(new Font(UiColors.FONT, Font.PLAIN, 9));
        total.setForeground(UiColors.MUTED);
        historyHeading.add(historyTitle, BorderLayout.WEST);
        historyHeading.add(total, BorderLayout.EAST);

        JPanel timeline = new JPanel();
        timeline.setOpaque(false);
        timeline.setLayout(new BoxLayout(timeline, BoxLayout.Y_AXIS));
        if (roomHistory.isEmpty()) {
            JLabel empty = new JLabel("Todavía no hay ingresos ni salidas registrados para esta habitación.");
            empty.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
            empty.setForeground(UiColors.MUTED);
            timeline.add(empty);
        } else {
            for (RegistroHistorial entry : roomHistory) timeline.add(historyEntry(entry));
        }
        JScrollPane scroll = new JScrollPane(timeline);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        historyCard.add(historyHeading, BorderLayout.NORTH);
        historyCard.add(scroll, BorderLayout.CENTER);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(current);
        body.add(Box.createVerticalStrut(12));
        body.add(historyCard);

        JButton close = new JButton("Cerrar");
        close.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        close.setForeground(Color.WHITE);
        close.setBackground(UiColors.PRIMARY);
        close.setFocusPainted(false);
        close.setBorder(new EmptyBorder(10, 18, 10, 18));
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.setOpaque(false);
        footer.add(close);
        close.addActionListener(e -> dispose());

        root.add(heading, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        setContentPane(root);
    }

    private void addInfo(JPanel parent, String label, String value) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(3, 0, 3, 0));
        JLabel key = new JLabel(label);
        key.setPreferredSize(new Dimension(125, 18));
        key.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        key.setForeground(UiColors.MUTED);
        JLabel text = new JLabel(value);
        text.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        text.setForeground(UiColors.INK);
        row.add(key, BorderLayout.WEST);
        row.add(text, BorderLayout.CENTER);
        parent.add(row);
    }

    private JPanel historyEntry(RegistroHistorial entry) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(7, 0, 7, 0));
        JLabel marker = new JLabel("●");
        marker.setFont(new Font(UiColors.FONT, Font.BOLD, 11));
        marker.setForeground("INGRESO".equalsIgnoreCase(entry.getAccion()) ? UiColors.TEAL : UiColors.WARNING);
        JPanel detail = new JPanel();
        detail.setOpaque(false);
        detail.setLayout(new BoxLayout(detail, BoxLayout.Y_AXIS));
        JLabel action = new JLabel(entry.getAccion() + "  ·  " + entry.getNombrePaciente());
        action.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        action.setForeground(UiColors.INK);
        JLabel meta = new JLabel(entry.getIdentificacionPaciente() + "  ·  " + entry.getFechaHora().format(DATE));
        meta.setFont(new Font(UiColors.FONT, Font.PLAIN, 9));
        meta.setForeground(UiColors.MUTED);
        detail.add(action);
        detail.add(Box.createVerticalStrut(3));
        detail.add(meta);
        row.add(marker, BorderLayout.WEST);
        row.add(detail, BorderLayout.CENTER);
        return row;
    }
}
