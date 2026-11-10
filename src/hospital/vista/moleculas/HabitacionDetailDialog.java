package hospital.vista.moleculas;

import hospital.modelos.Habitacion;
import hospital.modelos.RegistroHistorial;
import hospital.modelos.Paciente;
import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiCard;
import hospital.vista.atomos.UiButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** Diálogo adaptable con datos actuales y trazabilidad histórica de la cama. */
public class HabitacionDetailDialog extends JDialog {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd MMM yyyy · HH:mm");

    public HabitacionDetailDialog(Window owner, Habitacion room, List<RegistroHistorial> allHistory) {
        super(owner, "Detalle de habitación " + room.getNumero(), ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        int ownerWidth = owner == null ? 600 : owner.getWidth();
        int ownerHeight = owner == null ? 700 : owner.getHeight();
        setSize(Math.max(320, Math.min(600, ownerWidth - 28)), Math.max(420, Math.min(700, ownerHeight - 36)));
        setMinimumSize(new Dimension(320, 400));
        setLocationRelativeTo(owner);

        JPanel root = new JPanel(new BorderLayout(0, 14)) {
            @Override protected void paintComponent(Graphics graphics) {
                Graphics2D g = (Graphics2D) graphics.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(new Color(29, 43, 73, 24));
                g.fillRoundRect(3, 5, getWidth() - 6, getHeight() - 8, 26, 26);
                g.setColor(UiColors.BACKGROUND);
                g.fillRoundRect(1, 1, getWidth() - 6, getHeight() - 8, 26, 26);
                g.dispose();
            }
        };
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(18, 18, 14, 18));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("INFORMACIÓN DE HABITACIÓN");
        eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title = new JLabel("Habitación " + room.getNumero());
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 23));
        title.setForeground(UiColors.INK);
        JLabel subtitle = new JLabel("Piso " + room.getPiso() + "  ·  Posición " + room.getPosicion());
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        subtitle.setForeground(UiColors.MUTED);
        heading.add(eyebrow);
        heading.add(Box.createVerticalStrut(5));
        heading.add(title);
        heading.add(Box.createVerticalStrut(3));
        heading.add(subtitle);

        UiCard current = new UiCard(16, 15);
        current.setLayout(new BorderLayout(0, 10));
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
            available.setForeground(UiColors.TEAL);
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

        UiCard historyCard = new UiCard(16, 15);
        historyCard.setLayout(new BorderLayout(0, 10));
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
            JLabel empty = new JLabel("Todavía no hay ingresos ni salidas registrados.");
            empty.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
            empty.setForeground(UiColors.MUTED);
            timeline.add(empty);
        } else {
            for (RegistroHistorial entry : roomHistory) timeline.add(historyEntry(entry));
        }
        historyCard.add(historyHeading, BorderLayout.NORTH);
        historyCard.add(timeline, BorderLayout.CENTER);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        current.setAlignmentX(Component.LEFT_ALIGNMENT);
        historyCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(current);
        body.add(Box.createVerticalStrut(12));
        body.add(historyCard);

        JScrollPane bodyScroll = new JScrollPane(body);
        bodyScroll.setBorder(null);
        bodyScroll.setOpaque(false);
        bodyScroll.getViewport().setOpaque(false);
        bodyScroll.getVerticalScrollBar().setUnitIncrement(16);

        UiButton close = new UiButton("Cerrar", false);
        close.addActionListener(e -> dispose());
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.setOpaque(false);
        footer.add(close);

        root.add(heading, BorderLayout.NORTH);
        root.add(bodyScroll, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        setContentPane(root);
    }

    private void addInfo(JPanel parent, String label, String value) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(3, 0, 3, 0));
        JLabel key = new JLabel(label);
        key.setPreferredSize(new Dimension(112, 18));
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