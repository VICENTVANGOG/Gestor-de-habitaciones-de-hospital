package hospital.vista.atomos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Botón Hospitalize con relieve sutil, radios amplios y estados interactivos. */
public class UiButton extends JButton {
    private final boolean primary;
    public UiButton(String text) { this(text, true); }
    public UiButton(String text, boolean primary) {
        super(text); this.primary = primary;
        setFont(new Font(UiColors.FONT, Font.BOLD, 12));
        setForeground(primary ? Color.WHITE : UiColors.INK);
        setFocusPainted(false); setBorderPainted(false); setContentAreaFilled(false);
        setOpaque(false); setBorder(new EmptyBorder(11, 18, 11, 18));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMinimumSize(new Dimension(88, 40));
        setPreferredSize(new Dimension(Math.max(100, getPreferredSize().width + 24), 42));
    }
    @Override protected void paintComponent(Graphics graphics) {
        Graphics2D g=(Graphics2D)graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        boolean hover=getModel().isRollover(), pressed=getModel().isPressed();
        Color fill=primary?(pressed?UiColors.PRIMARY_DARK:hover?new Color(43,91,211):UiColors.PRIMARY)
            :(hover?UiColors.PRIMARY_SOFT:Color.WHITE);
        int arc=18;
        if(primary&&!pressed){g.setColor(new Color(52,104,232,35));g.fillRoundRect(1,3,getWidth()-3,getHeight()-4,arc,arc);}
        g.setColor(fill);g.fillRoundRect(0,0,getWidth()-2,getHeight()-4,arc,arc);
        if(!primary){g.setColor(hover?new Color(198,214,250):UiColors.BORDER);g.setStroke(new BasicStroke(1f));g.drawRoundRect(0,0,getWidth()-2,getHeight()-4,arc,arc);}
        g.dispose();
        super.paintComponent(graphics);
    }
}
