package hospital.vista.moleculas;
import hospital.vista.atomos.UiTextField;import javax.swing.*;import java.awt.*;
/** Molécula: etiqueta y campo de entrada. */
public class FormField extends JPanel {
 private final UiTextField field;
 public FormField(String label){setOpaque(false);setLayout(new BorderLayout(0,5));add(new JLabel(label),BorderLayout.NORTH);field=new UiTextField(20);add(field,BorderLayout.CENTER);}
 public String value(){return field.getText().trim();}
}