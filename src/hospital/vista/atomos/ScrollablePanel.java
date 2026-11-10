package hospital.vista.atomos;

import javax.swing.*;
import java.awt.*;

/** Panel que sigue el ancho del viewport y permite desplazamiento solo vertical. */
public class ScrollablePanel extends JPanel implements Scrollable {
    public ScrollablePanel() { super(); }
    public ScrollablePanel(LayoutManager layout) { super(layout); }
    @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
    @Override public int getScrollableUnitIncrement(Rectangle visibleRect,int orientation,int direction) { return 18; }
    @Override public int getScrollableBlockIncrement(Rectangle visibleRect,int orientation,int direction) { return Math.max(18,visibleRect.height-18); }
    @Override public boolean getScrollableTracksViewportWidth() { return true; }
    @Override public boolean getScrollableTracksViewportHeight() { return false; }
}