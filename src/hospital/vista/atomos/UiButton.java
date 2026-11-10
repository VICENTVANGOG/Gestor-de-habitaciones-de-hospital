package hospital.vista.atomos;
import javax.swing.*;import java.awt.*;
/** Átomo visual: botón reutilizable. */
public class UiButton extends JButton {
 public UiButton(String text){super(text);setFont(new Font("Segoe UI",Font.BOLD,13));setForeground(Color.WHITE);setBackground(new Color(37,99,235));setFocusPainted(false);setBorder(BorderFactory.createEmptyBorder(10,15,10,15));setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
}