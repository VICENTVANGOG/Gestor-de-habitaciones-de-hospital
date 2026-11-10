package hospital;

import hospital.controlador.HospitalController;
import hospital.vista.plantillas.MainTemplate;
import hospital.vista.paginas.HospitalPage;
import javax.swing.*;
import java.awt.*;

/** Punto de entrada de la aplicación de escritorio. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.put("Panel.background", new Color(247, 250, 252));
                UIManager.put("OptionPane.messageFont", new Font("Inter", Font.PLAIN, 12));
            } catch (Exception ignored) {}
            HospitalController controller = new HospitalController();
            final HospitalPage[] page = new HospitalPage[1];
            final MainTemplate[] root = new MainTemplate[1];
            root[0] = new MainTemplate(key -> {
                String name = switch (key) {
                    case "pacientes" -> "Pacientes";
                    case "habitaciones" -> "Habitaciones";
                    case "historial" -> "Historial hospitalario";
                    default -> "Resumen del día";
                };
                root[0].showPage("main", name);
                root[0].setActiveSection(key);
                if (page[0] != null) page[0].showSection(key);
            });
            page[0] = new HospitalPage(controller);
            root[0].addPage("main", page[0]);
            root[0].showPage("main", "Resumen del día");
            root[0].setActiveSection("dashboard");
            JFrame frame = new JFrame("Hospitalize · Gestor Hospitalario");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(390, 640));
            frame.setSize(1360, 900);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(root[0]);
            frame.setVisible(true);
        });
    }
}
