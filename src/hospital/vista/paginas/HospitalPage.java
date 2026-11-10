package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.vista.atomos.UiColors;
import javax.swing.*;
import java.awt.*;

/**
 * Contenedor de navegación entre páginas. La lógica de cada módulo vive
 * en su propia clase de vista; este componente solo coordina su presentación.
 */
public class HospitalPage extends JPanel {
    private final CardLayout layout = new CardLayout();
    private final JPanel pages = new JPanel(layout);
    private final DashboardPage dashboard;
    private final PacientesPage patients;
    private final HabitacionesPage rooms;
    private final HistorialPage history;

    public HospitalPage(HospitalController controller) {
        setLayout(new BorderLayout());
        setBackground(UiColors.BACKGROUND);
        dashboard = new DashboardPage(controller);
        patients = new PacientesPage(controller);
        rooms = new HabitacionesPage(controller);
        history = new HistorialPage(controller);
        pages.add(dashboard, "dashboard");
        pages.add(patients, "pacientes");
        pages.add(rooms, "habitaciones");
        pages.add(history, "historial");
        pages.setBackground(UiColors.BACKGROUND);
        add(pages, BorderLayout.CENTER);
        showSection("dashboard");
    }

    public void showSection(String key) {
        String section = switch (key) {
            case "pacientes", "habitaciones", "historial" -> key;
            default -> "dashboard";
        };
        layout.show(pages, section);
        switch (section) {
            case "pacientes" -> patients.refresh();
            case "habitaciones" -> rooms.refresh();
            case "historial" -> history.refresh();
            default -> dashboard.refresh();
        }
    }

    /** Actualiza todas las páginas después de una operación del modelo. */
    public void refreshAll() {
        dashboard.refresh();
        patients.refresh();
        rooms.refresh();
        history.refresh();
    }
}
