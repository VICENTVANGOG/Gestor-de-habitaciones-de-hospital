package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Habitacion;
import hospital.modelos.Paciente;
import hospital.vista.atomos.UiButton;
import hospital.vista.atomos.UiCard;
import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.HabitacionTile;
import hospital.vista.moleculas.HabitacionDetailDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/** Página de habitaciones: mapa por pisos y acceso al detalle de cada habitación. */
public class HabitacionesPage extends JPanel {
    private final HospitalController controller;
    private final JPanel floorsPanel = new JPanel();
    private final JLabel occupiedValue = metricValue();
    private final JLabel availableValue = metricValue();
    private final JLabel occupancyValue = metricValue();

    public HabitacionesPage(HospitalController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel page = new JPanel(new BorderLayout(0, 20));
        page.setOpaque(false);
        page.setBorder(new EmptyBorder(20, 26, 26, 26));

        JPanel heading = new JPanel(new BorderLayout(16, 0));
        heading.setOpaque(false);
        JPanel titleGroup = new JPanel();
        titleGroup.setOpaque(false);
        titleGroup.setLayout(new BoxLayout(titleGroup, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("GESTIÓN DE CAPACIDAD");
        eyebrow.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title = new JLabel("Habitaciones");
        title.setFont(new Font(UiColors.FONT, Font.BOLD, 27));
        title.setForeground(UiColors.INK);
        JLabel subtitle = new JLabel("Consulta la ocupación por piso y selecciona una habitación para ver su información.");
        subtitle.setFont(new Font(UiColors.FONT, Font.PLAIN, 11));
        subtitle.setForeground(UiColors.MUTED);
        titleGroup.add(eyebrow);
        titleGroup.add(Box.createVerticalStrut(6));
        titleGroup.add(title);
        titleGroup.add(Box.createVerticalStrut(5));
        titleGroup.add(subtitle);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        actions.setOpaque(false);
        UiButton assignButton = new UiButton("+ Asignar paciente");
        UiButton dischargeButton = new UiButton("Registrar salida", false);
        actions.add(assignButton);
        actions.add(dischargeButton);
        heading.add(titleGroup, BorderLayout.CENTER);
        heading.add(actions, BorderLayout.EAST);
        page.add(heading, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);
        JPanel metrics = new JPanel(new GridLayout(1, 3, 12, 0));
        metrics.setOpaque(false);
        metrics.add(metricCard("Habitaciones ocupadas", occupiedValue, UiColors.DANGER));
        metrics.add(metricCard("Habitaciones disponibles", availableValue, UiColors.TEAL));
        metrics.add(metricCard("Ocupación total", occupancyValue, UiColors.PRIMARY));

        UiCard mapCard = new UiCard(18, 18);
        mapCard.setLayout(new BorderLayout(0, 16));
        JPanel mapHeader = new JPanel(new BorderLayout());
        mapHeader.setOpaque(false);
        JLabel mapTitle = new JLabel("Distribución por pisos");
        mapTitle.setFont(new Font(UiColors.FONT, Font.BOLD, 14));
        mapTitle.setForeground(UiColors.INK);
        JLabel mapHint = new JLabel("Selecciona una tarjeta para consultar el detalle");
        mapHint.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        mapHint.setForeground(UiColors.MUTED);
        mapHeader.add(mapTitle, BorderLayout.WEST);
        mapHeader.add(mapHint, BorderLayout.EAST);

        JPanel legend = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        legend.setOpaque(false);
        legend.add(legendItem(UiColors.TEAL, "Disponible"));
        legend.add(legendItem(UiColors.DANGER, "Ocupada"));
        JPanel mapTop = new JPanel();
        mapTop.setOpaque(false);
        mapTop.setLayout(new BoxLayout(mapTop, BoxLayout.Y_AXIS));
        mapTop.add(mapHeader);
        mapTop.add(Box.createVerticalStrut(12));
        mapTop.add(legend);

        floorsPanel.setOpaque(false);
        floorsPanel.setLayout(new BoxLayout(floorsPanel, BoxLayout.Y_AXIS));
        mapCard.add(mapTop, BorderLayout.NORTH);
        mapCard.add(floorsPanel, BorderLayout.CENTER);
        content.add(metrics, BorderLayout.NORTH);
        content.add(mapCard, BorderLayout.CENTER);
        page.add(content, BorderLayout.CENTER);
        page.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                int width = page.getWidth();
                boolean narrow = width < 680;
                page.setBorder(new EmptyBorder(16, narrow ? 12 : 26, 22, narrow ? 12 : 26));
                mapHeader.setLayout(new BorderLayout(8, narrow ? 5 : 0));
                if (narrow) { mapHeader.add(mapTitle, BorderLayout.NORTH); mapHeader.add(mapHint, BorderLayout.SOUTH); }
                else { mapHeader.add(mapTitle, BorderLayout.WEST); mapHeader.add(mapHint, BorderLayout.EAST); }
                metrics.setLayout(new GridLayout(width < 520 ? 2 : 1, width < 520 ? 2 : 3, 10, 10));
                heading.setLayout(new BorderLayout(8, width < 620 ? 10 : 0));
                if (width < 620) { heading.add(titleGroup, BorderLayout.NORTH); heading.add(actions, BorderLayout.SOUTH); }
                else { heading.add(titleGroup, BorderLayout.CENTER); heading.add(actions, BorderLayout.EAST); }
                page.revalidate();
                refresh();
            }
        });
        JScrollPane pageScroll = new JScrollPane(page);
        pageScroll.setBorder(null);
        pageScroll.setOpaque(false);
        pageScroll.getViewport().setOpaque(false);
        pageScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pageScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(pageScroll, BorderLayout.CENTER);

        assignButton.addActionListener(e -> assignPatient());
        dischargeButton.addActionListener(e -> dischargePatient());
        refresh();
    }

    private static JLabel metricValue() {
        JLabel value = new JLabel("0");
        value.setFont(new Font(UiColors.FONT, Font.BOLD, 23));
        value.setForeground(UiColors.INK);
        return value;
    }

    private JPanel metricCard(String title, JLabel value, Color accent) {
        UiCard card = new UiCard(15, 15);
        card.setLayout(new BorderLayout(0, 7));
        JLabel label = new JLabel(title);
        label.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        label.setForeground(UiColors.MUTED);
        JPanel marker = new JPanel();
        marker.setBackground(accent);
        marker.setPreferredSize(new Dimension(4, 30));
        card.add(label, BorderLayout.NORTH);
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.add(marker, BorderLayout.WEST);
        row.add(value, BorderLayout.CENTER);
        card.add(row, BorderLayout.CENTER);
        return card;
    }

    private JPanel legendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        item.setOpaque(false);
        JLabel dot = new JLabel("●");
        dot.setForeground(color);
        dot.setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        JLabel label = new JLabel(text);
        label.setForeground(UiColors.MUTED);
        label.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        item.add(dot);
        item.add(label);
        return item;
    }

    private void assignPatient() {
        List<Paciente> candidates = controller.pacientes().stream()
                .filter(patient -> controller.habitaciones().stream().noneMatch(room ->
                        room.isOcupada() && room.getPaciente().getIdentificacion().equals(patient.getIdentificacion())))
                .collect(Collectors.toList());
        List<Habitacion> freeRooms = controller.habitaciones().stream()
                .filter(room -> !room.isOcupada()).collect(Collectors.toList());

        if (candidates.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pacientes sin habitación asignada.", "Asignación", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (freeRooms.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay habitaciones disponibles.", "Capacidad completa", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JComboBox<Paciente> patientBox = new JComboBox<>(candidates.toArray(new Paciente[0]));
        patientBox.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focus) {
                super.getListCellRendererComponent(list, value, index, selected, focus);
                if (value instanceof Paciente p) setText(p.getNombre() + "  ·  " + p.getIdentificacion());
                return this;
            }
        });
        JComboBox<Habitacion> roomBox = new JComboBox<>(freeRooms.toArray(new Habitacion[0]));
        roomBox.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focus) {
                super.getListCellRendererComponent(list, value, index, selected, focus);
                if (value instanceof Habitacion room) setText("Habitación " + room.getNumero() + "  ·  Piso " + room.getPiso());
                return this;
            }
        });

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 8));
        form.setBorder(new EmptyBorder(8, 4, 4, 4));
        form.add(fieldGroup("Paciente", patientBox));
        form.add(fieldGroup("Habitación disponible", roomBox));
        if (JOptionPane.showConfirmDialog(this, form, "Asignar habitación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;

        Paciente patient = (Paciente) patientBox.getSelectedItem();
        Habitacion room = (Habitacion) roomBox.getSelectedItem();
        if (patient == null || room == null) return;
        try {
            controller.asignarHabitacion(patient.getIdentificacion(), room.getNumero());
            refresh();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo asignar", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void dischargePatient() {
        List<Habitacion> occupied = controller.habitaciones().stream()
                .filter(Habitacion::isOcupada).collect(Collectors.toList());
        if (occupied.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pacientes con hospitalización activa.", "Registrar salida", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JComboBox<Habitacion> roomBox = new JComboBox<>(occupied.toArray(new Habitacion[0]));
        roomBox.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focus) {
                super.getListCellRendererComponent(list, value, index, selected, focus);
                if (value instanceof Habitacion room && room.getPaciente() != null)
                    setText("Hab. " + room.getNumero() + "  ·  " + room.getPaciente().getNombre() + " (" + room.getPaciente().getIdentificacion() + ")");
                return this;
            }
        });
        if (JOptionPane.showConfirmDialog(this, roomBox, "Selecciona la hospitalización", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        Habitacion selected = (Habitacion) roomBox.getSelectedItem();
        if (selected == null || selected.getPaciente() == null) return;
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Registrar la salida de " + selected.getPaciente().getNombre() + "?",
                "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            controller.darSalida(selected.getPaciente().getIdentificacion());
            refresh();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo registrar la salida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JPanel fieldGroup(String title, JComponent field) {
        JPanel group = new JPanel(new BorderLayout(0, 5));
        group.setOpaque(false);
        JLabel label = new JLabel(title);
        label.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
        label.setForeground(UiColors.MUTED);
        group.add(label, BorderLayout.NORTH);
        group.add(field, BorderLayout.CENTER);
        return group;
    }

    private void openRoom(Habitacion room) {
        new HabitacionDetailDialog(SwingUtilities.getWindowAncestor(this), room, controller.historial()).setVisible(true);
    }

    public void refresh() {
        List<Habitacion> rooms = controller.habitaciones();
        int occupied = (int) rooms.stream().filter(Habitacion::isOcupada).count();
        int available = rooms.size() - occupied;
        occupiedValue.setText(String.valueOf(occupied));
        availableValue.setText(String.valueOf(available));
        occupancyValue.setText(Math.round(occupied * 100.0 / Math.max(1, rooms.size())) + "%");

        floorsPanel.removeAll();
        for (int floor = 1; floor <= 5; floor++) {
            final int floorNumber = floor;
            JPanel floorRow = new JPanel(new BorderLayout(14, 0));
            floorRow.setOpaque(false);
            floorRow.setBorder(new EmptyBorder(7, 0, 7, 0));
            JPanel floorLabel = new JPanel();
            floorLabel.setOpaque(false);
            floorLabel.setPreferredSize(new Dimension(64, 72));
            floorLabel.setLayout(new BoxLayout(floorLabel, BoxLayout.Y_AXIS));
            JLabel floorName = new JLabel("PISO " + floor);
            floorName.setFont(new Font(UiColors.FONT, Font.BOLD, 10));
            floorName.setForeground(UiColors.PRIMARY);
            JLabel floorRange = new JLabel(String.format("%02d–%02d", (floor - 1) * 6 + 1, floor * 6));
            floorRange.setFont(new Font(UiColors.FONT, Font.PLAIN, 9));
            floorRange.setForeground(UiColors.MUTED);
            floorLabel.add(Box.createVerticalGlue());
            floorLabel.add(floorName);
            floorLabel.add(Box.createVerticalStrut(4));
            floorLabel.add(floorRange);
            floorLabel.add(Box.createVerticalGlue());

            int availableWidth = Math.max(190, floorsPanel.getWidth() - 90);
            int columns = Math.max(2, Math.min(6, availableWidth / 112));
            JPanel roomGrid = new JPanel(new GridLayout(0, columns, 9, 9));
            roomGrid.setOpaque(false);
            for (Habitacion room : rooms) {
                if (room.getPiso() != floorNumber) continue;
                HabitacionTile tile = new HabitacionTile(room);
                tile.addActionListener(e -> openRoom(room));
                roomGrid.add(tile);
            }
            floorRow.add(floorLabel, BorderLayout.WEST);
            floorRow.add(roomGrid, BorderLayout.CENTER);
            floorsPanel.add(floorRow);
            if (floor < 5) {
                JSeparator separator = new JSeparator();
                separator.setForeground(UiColors.BORDER);
                floorsPanel.add(separator);
            }
        }
        floorsPanel.revalidate();
        floorsPanel.repaint();
    }
}
