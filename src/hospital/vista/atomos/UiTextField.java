package hospital.vista.atomos;
import javax.swing.*;import java.awt.*;
/** Átomo visual: campo de texto reutilizable. */
public class UiTextField extends JTextField {
 public UiTextField(int columns){super(columns);setFont(new Font("Segoe UI",Font.PLAIN,14));setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(203,213,225)),BorderFactory.createEmptyBorder(8,10,8,10)));}
}