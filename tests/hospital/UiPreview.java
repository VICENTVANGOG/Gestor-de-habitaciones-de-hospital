package hospital;

import hospital.controlador.HospitalController;
import hospital.vista.paginas.HospitalPage;
import hospital.vista.plantillas.MainTemplate;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/** Render de control para revisar todos los módulos en escritorio y móvil. */
public final class UiPreview {
    private UiPreview() {}
    public static void main(String[] args) throws Exception {
        try (InputStream in = UiPreview.class.getResourceAsStream("/fonts/InterVariable.ttf")) {
            if (in != null) GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, in));
        }
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        HospitalController controller = new HospitalController();
        for (int i = 1; i <= 12; i++) {
            controller.registrarPaciente("PREV-" + i, "Paciente Demo " + i, 20 + i, (i % 5) + 1, "Control clínico general");
            if (i <= 8) controller.asignarHabitacion("PREV-" + i, i);
        }
        final MainTemplate[] root = new MainTemplate[1];
        final HospitalPage[] page = new HospitalPage[1];
        root[0] = new MainTemplate(key -> show(root[0], page[0], key));
        page[0] = new HospitalPage(controller);
        root[0].addPage("main", page[0]);
        show(root[0], page[0], "dashboard");

        JFrame frame = new JFrame("Hospitalize UI Preview");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(root[0]);
        frame.setSize(1360, 900);
        frame.setLocation(0, 0);
        frame.setVisible(true);
        Robot robot = new Robot();
        Path output = Path.of("target", "ui-previews");
        Files.createDirectories(output);

        Thread.sleep(700);
        capture(robot, frame, output.resolve("dashboard-desktop.png"));
        show(root[0], page[0], "pacientes"); Thread.sleep(350);
        capture(robot, frame, output.resolve("patients-desktop.png"));
        show(root[0], page[0], "habitaciones"); Thread.sleep(500);
        capture(robot, frame, output.resolve("rooms-desktop.png"));
        show(root[0], page[0], "historial"); Thread.sleep(350);
        capture(robot, frame, output.resolve("history-desktop.png"));

        SwingUtilities.invokeAndWait(() -> frame.setSize(390, 844));
        Thread.sleep(700);
        show(root[0], page[0], "dashboard"); Thread.sleep(300);
        capture(robot, frame, output.resolve("dashboard-mobile.png"));
        show(root[0], page[0], "pacientes"); Thread.sleep(300);
        capture(robot, frame, output.resolve("patients-mobile.png"));
        show(root[0], page[0], "habitaciones"); Thread.sleep(450);
        capture(robot, frame, output.resolve("rooms-mobile.png"));
        show(root[0], page[0], "historial"); Thread.sleep(300);
        capture(robot, frame, output.resolve("history-mobile.png"));
        frame.dispose();
    }
    private static void show(MainTemplate root, HospitalPage page, String key) {
        String title = switch (key) {
            case "pacientes" -> "Pacientes";
            case "habitaciones" -> "Habitaciones";
            case "historial" -> "Historial hospitalario";
            default -> "Panel de gestión clínica";
        };
        root.showPage("main", title);
        root.setActiveSection(key);
        if (page != null) page.showSection(key);
    }
    private static void capture(Robot robot, JFrame frame, Path path) throws Exception {
        Point location = frame.getLocationOnScreen();
        Rectangle bounds = new Rectangle(location.x, location.y, frame.getWidth(), frame.getHeight());
        BufferedImage image = robot.createScreenCapture(bounds);
        ImageIO.write(image, "png", path.toFile());
    }
}