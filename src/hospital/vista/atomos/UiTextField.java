package hospital.vista.atomos;

import javax.swing.*;
import java.awt.*;

/** Átomo visual: campo de texto reutilizable según Hospitalize UI Foundations. */
public class UiTextField extends JTextField {
    public UiTextField(int columns) {
        super(columns);
        setFont(new Font(UiColors.FONT, Font.PLAIN, 14));
        setForeground(UiColors.INK);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }
}
