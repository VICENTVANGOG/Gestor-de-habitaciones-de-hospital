package hospital.vista.atomos;

import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.Icon;

/** Familia de iconos vectoriales lineales, consistente en todo Hospitalize. */
public final class UiIcon implements Icon {
    private final String type; private final Color color; private final int size;
    public UiIcon(String type,Color color){this(type,color,18);}
    public UiIcon(String type,Color color,int size){this.type=type;this.color=color;this.size=size;}
    @Override public int getIconWidth(){return size;}
    @Override public int getIconHeight(){return size;}
    @Override public void paintIcon(Component c,Graphics graphics,int x,int y){
        Graphics2D g=(Graphics2D)graphics.create();
        g.translate(x,y);g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);g.setStroke(new BasicStroke(1.65f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));g.scale(size/20.0,size/20.0);
        switch(type){
            case "dashboard" -> {Path2D p=new Path2D.Double();p.moveTo(2,9);p.lineTo(10,3);p.lineTo(18,9);g.draw(p);g.drawRoundRect(5,8,10,9,2,2);g.drawLine(9,17,9,12);g.drawLine(11,17,11,12);}
            case "pacientes" -> {g.drawOval(7,3,6,6);g.drawArc(4,10,12,9,0,180);g.drawArc(2,12,17,7,0,180);}
            case "habitaciones" -> {g.drawRoundRect(2,6,15,9,2,2);g.drawLine(2,11,18,11);g.drawRoundRect(4,7,5,3,1,1);g.drawLine(4,15,4,18);g.drawLine(16,15,16,18);}
            case "historial" -> {g.drawOval(2,2,15,15);g.drawLine(10,6,10,10);g.drawLine(10,10,14,12);g.drawLine(2,5,5,5);}
            case "search" -> {g.drawOval(2,2,11,11);g.drawLine(12,12,18,18);}
            case "clock" -> {g.drawOval(2,2,16,16);g.drawLine(10,5,10,10);g.drawLine(10,10,14,12);}
            case "refresh" -> {g.drawArc(3,3,14,14,35,285);g.drawLine(15,3,18,4);g.drawLine(18,4,18,8);}
            case "plus" -> {g.drawRoundRect(2,2,15,15,4,4);g.drawLine(10,6,10,14);g.drawLine(6,10,14,10);}
            case "close" -> {g.drawLine(5,5,15,15);g.drawLine(15,5,5,15);}
            case "check" -> {g.drawLine(3,10,8,15);g.drawLine(8,15,17,5);}
            case "bed" -> {g.drawRoundRect(2,7,15,8,2,2);g.drawLine(2,11,18,11);g.drawOval(4,7,4,4);g.drawLine(3,15,3,18);g.drawLine(17,15,17,18);}
            default -> g.drawOval(3,3,14,14);
        }
        g.dispose();
    }
}
