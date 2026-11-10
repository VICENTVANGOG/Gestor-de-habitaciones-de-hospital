package hospital.vista.atomos;

import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.Icon;

/** Iconos vectoriales lineales para navegación Hospitalize. */
public final class UiIcon implements Icon {
    private final String type;
    private final Color color;
    private final int size;
    public UiIcon(String type,Color color){this(type,color,18);}
    public UiIcon(String type,Color color,int size){this.type=type;this.color=color;this.size=size;}
    @Override public int getIconWidth(){return size;}
    @Override public int getIconHeight(){return size;}
    @Override public void paintIcon(Component c,Graphics graphics,int x,int y){
        Graphics2D g=(Graphics2D)graphics.create();
        g.translate(x,y);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);g.setStroke(new BasicStroke(1.55f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        double scale=size/20.0;g.scale(scale,scale);
        switch(type){
            case "dashboard" -> {
                Path2D roof=new Path2D.Double();roof.moveTo(2.5,9);roof.lineTo(10,2.5);roof.lineTo(17.5,9);
                g.draw(roof);g.drawRoundRect(5,8,10,9,2,2);g.drawLine(9,17,9,12);g.drawLine(11,17,11,12);
            }
            case "pacientes" -> {
                g.drawOval(7,3,6,6);g.drawArc(4,9,13,10,0,180);
            }
            case "habitaciones" -> {
                g.drawRoundRect(3,6,15,9,2,2);g.drawLine(2.5,11,17.5,11);
                g.drawRoundRect(4,7,5,3,1,1);g.drawLine(4,14.5,4,17);g.drawLine(16,14.5,16,17);
            }
            default -> {
                g.drawOval(3,3,15,15);g.drawLine(10,5.5,10,10);g.drawLine(10,10,13.5,12);
            }
        }
        g.dispose();
    }
}