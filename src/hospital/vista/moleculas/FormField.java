package hospital.vista.moleculas;

import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiTextField;
import javax.swing.*;
import java.awt.*;

/** Molécula reutilizable: etiqueta clínica y campo de entrada. */
public class FormField extends JPanel {
    private final UiTextField field;
    public FormField(String label) {
        setOpaque(false);
        setLayout(new BorderLayout(0, 6));
        JLabel caption = new JLabel(label);
        caption.setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        caption.setForeground(UiColors.INK);
        add(caption, BorderLayout.NORTH);
        field = new UiTextField(20);
        add(field, BorderLayout.CENTER);
    }
    public String value() { return field.getText().trim(); }
    public void setValue(String value) { field.setText(value == null ? "" : value); }
}
