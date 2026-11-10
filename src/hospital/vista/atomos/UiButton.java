package hospital.vista.atomos;

import javax.swing.*;
import java.awt.*;

/** Átomo visual: botón reutilizable según el sistema visual Hospitalize. */
public class UiButton extends JButton {
    public UiButton(String text) {
        super(text);
        setFont(new Font(UiColors.FONT, Font.BOLD, 13));
        setForeground(Color.WHITE);
        setBackground(UiColors.PRIMARY);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(true);
    }
}
