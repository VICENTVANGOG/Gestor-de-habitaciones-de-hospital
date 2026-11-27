package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/** Comprueba navegación y actualización de las tablas al cambiar el estado del modelo. */
class HospitalPageTest {

    @Test
    void navigationRefreshesPatientAndHistoryTables() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            HospitalController controller = new HospitalController();
            HospitalPage page = new HospitalPage(controller);

            JTable patientTable = findTable(page, "Identificación");
            JTable historyTable = findTable(page, "Fecha y hora");
            assertNotNull(patientTable, "Debe existir la tabla de pacientes.");
            assertNotNull(historyTable, "Debe existir la tabla de historial.");
            assertEquals(0, patientTable.getRowCount());
            assertEquals(0, historyTable.getRowCount());

            controller.registrarPaciente(
                    "TEST-1001", "Ana Pérez", 25, 2, "Dolor abdominal");
            page.showSection("pacientes");

            assertTrue(findPage(page, PacientesPage.class).isVisible());
            assertFalse(findPage(page, DashboardPage.class).isVisible());
            assertEquals(1, patientTable.getRowCount());
            assertEquals("TEST-1001", patientTable.getValueAt(0, 0));

            controller.asignarHabitacion("TEST-1001", 8);
            page.showSection("habitaciones");
            assertTrue(findPage(page, HabitacionesPage.class).isVisible());
            assertTrue(controller.habitaciones().get(7).isOcupada());

            page.showSection("historial");
            assertTrue(findPage(page, HistorialPage.class).isVisible());
            assertEquals(1, historyTable.getRowCount());
            assertEquals("INGRESO", historyTable.getValueAt(0, 1));

            controller.darSalida("TEST-1001");
            page.showSection("historial");
            assertEquals(2, historyTable.getRowCount());
            assertEquals("SALIDA", historyTable.getValueAt(0, 1));
            assertEquals("INGRESO", historyTable.getValueAt(1, 1));

            page.showSection("dashboard");
            assertTrue(findPage(page, DashboardPage.class).isVisible());
        });
    }

    private static JTable findTable(Container parent, String firstColumnName) {
        for (Component component : parent.getComponents()) {
            if (component instanceof JTable table
                    && table.getColumnCount() > 0
                    && firstColumnName.equals(table.getColumnName(0))) {
                return table;
            }
            if (component instanceof Container child) {
                JTable found = findTable(child, firstColumnName);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static <T extends Component> T findPage(Container parent, Class<T> type) {
        for (Component component : parent.getComponents()) {
            if (type.isInstance(component)) return type.cast(component);
            if (component instanceof Container child) {
                T found = findPage(child, type);
                if (found != null) return found;
            }
        }
        return null;
    }
}
