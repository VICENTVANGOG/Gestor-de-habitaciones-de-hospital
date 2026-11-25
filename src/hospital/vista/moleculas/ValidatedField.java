package hospital.vista.moleculas;

import hospital.vista.atomos.UiColors;
import hospital.vista.atomos.UiTextField;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.function.Function;

/**
 * Molécula de formulario con validación: bloquea los caracteres no permitidos
 * mientras se escribe y muestra el mensaje de error debajo del campo.
 */
public class ValidatedField extends JPanel {
    private final UiTextField field = new UiTextField(16);
    private final JLabel error = new JLabel(" ");
    private final Function<String, String> finalRule;

    public ValidatedField(String label, String hint,
                          Function<String, String> typingRule,
                          Function<String, String> finalRule) {
        this.finalRule = finalRule;
        setOpaque(false);
        setLayout(new BorderLayout(0, 6));

        JLabel caption = new JLabel(label);
        caption.setFont(new Font(UiColors.FONT, Font.BOLD, 9));
        caption.setForeground(UiColors.INK);
        caption.setLabelFor(field);

        field.setFont(new Font(UiColors.FONT, Font.PLAIN, 12));
        field.setPreferredSize(new Dimension(210, 40));
        field.setToolTipText(hint);
        field.getAccessibleContext().setAccessibleName(label);
        field.getAccessibleContext().setAccessibleDescription(hint);
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new RuleFilter(typingRule));

        error.setFont(new Font(UiColors.FONT, Font.PLAIN, 10));
        error.setForeground(UiColors.DANGER);

        add(caption, BorderLayout.NORTH);
        add(field, BorderLayout.CENTER);
        add(error, BorderLayout.SOUTH);
    }

    public String value() { return field.getText().trim(); }

    public JTextField input() { return field; }

    public String errorMessage() {
        String text = error.getText();
        return text == null || text.isBlank() ? null : text;
    }

    /** Aplica la regla final (campos obligatorios, mínimos) y muestra el error si existe. */
    public boolean validateField() {
        String message = finalRule.apply(field.getText());
        if (message == null) clearError(); else showError(message);
        return message == null;
    }

    public void focus() { field.requestFocusInWindow(); field.selectAll(); }

    private void showError(String message) {
        error.setText(message);
        error.setToolTipText(message);
        field.getAccessibleContext().setAccessibleDescription(message);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.DANGER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    private void clearError() {
        error.setText(" ");
        error.setToolTipText(null);
        field.getAccessibleContext().setAccessibleDescription(field.getToolTipText());
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiColors.BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    private class RuleFilter extends DocumentFilter {
        private final Function<String, String> rule;

        RuleFilter(Function<String, String> rule) { this.rule = rule; }

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
                throws BadLocationException {
            replace(fb, offset, 0, text, attrs);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String proposed = current.substring(0, offset)
                    + (text == null ? "" : text)
                    + current.substring(offset + length);
            String message = rule.apply(proposed);
            if (message != null) {
                showError(message);
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            clearError();
            super.replace(fb, offset, length, text, attrs);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            clearError();
            super.remove(fb, offset, length);
        }
    }
}
