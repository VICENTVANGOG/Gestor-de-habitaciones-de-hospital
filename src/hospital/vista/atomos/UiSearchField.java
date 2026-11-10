package hospital.vista.atomos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;

/** Buscador reutilizable con lupa vectorial y contenedor redondeado. */
public class UiSearchField extends JPanel {
    private final JTextField field=new JTextField();
    public UiSearchField(String placeholder,int width){
        setOpaque(false);setLayout(new BorderLayout(9,0));
        setBorder(new EmptyBorder(0,12,0,12));setPreferredSize(new Dimension(width,40));setMinimumSize(new Dimension(160,40));
        JLabel icon=new JLabel(new UiIcon("search",UiColors.MUTED,17));
        field.setFont(new Font(UiColors.FONT,Font.PLAIN,11));field.setForeground(UiColors.INK);
        field.setBorder(null);field.setOpaque(false);field.setToolTipText(placeholder);
        add(icon,BorderLayout.WEST);add(field,BorderLayout.CENTER);
    }
    @Override protected void paintComponent(Graphics graphics){
        Graphics2D g=(Graphics2D)graphics.create();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);g.fillRoundRect(0,0,getWidth()-1,getHeight()-1,13,13);
        g.setColor(UiColors.BORDER);g.drawRoundRect(0,0,getWidth()-1,getHeight()-1,13,13);g.dispose();super.paintComponent(graphics);
    }
    public String getText(){return field.getText();}
    public Document getDocument(){return field.getDocument();}
    public void setText(String text){field.setText(text);}
    public void addDocumentListener(DocumentListener listener){field.getDocument().addDocumentListener(listener);}
    public JTextField getTextField(){return field;}
}
