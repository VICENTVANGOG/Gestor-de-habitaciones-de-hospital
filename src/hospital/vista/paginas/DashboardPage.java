package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Habitacion;
import hospital.modelos.Paciente;
import hospital.modelos.RegistroHistorial;
import hospital.vista.atomos.*;
import hospital.vista.moleculas.StatCard;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/** Dashboard principal reconstruido con la composición del prototipo Hospitalize. */
public class DashboardPage extends JPanel {
    private final HospitalController controller;
    private final StatCard patientsCard=new StatCard("PACIENTES REGISTRADOS",UiColors.PRIMARY);
    private final StatCard occupiedCard=new StatCard("HABITACIONES OCUPADAS",UiColors.TEAL);
    private final StatCard availableCard=new StatCard("HABITACIONES DISPONIBLES",UiColors.WARNING);
    private final StatCard occupancyCard=new StatCard("OCUPACIÓN DE CAMAS",UiColors.PURPLE);
    private final JPanel floorRows=new JPanel();
    private final JPanel priorityRows=new JPanel();
    private final JPanel activityRows=new JPanel();
    private final JPanel capacityRows=new JPanel();
    private final DonutGauge gauge=new DonutGauge();
    private final JLabel activityCount=new JLabel("0 movimientos");
    private static final DateTimeFormatter TIME=DateTimeFormatter.ofPattern("HH:mm");

    public DashboardPage(HospitalController controller) {
        this.controller=controller;
        setLayout(new BorderLayout());setOpaque(false);

        JPanel page=new JPanel(new GridBagLayout());page.setOpaque(false);
        page.setBorder(new EmptyBorder(8,20,12,20));

        JPanel intro=new JPanel(new BorderLayout(16,8));intro.setOpaque(false);
        JPanel introText=new JPanel();introText.setOpaque(false);introText.setLayout(new BoxLayout(introText,BoxLayout.Y_AXIS));
        JLabel eyebrow=new JLabel("RESUMEN GENERAL");
        eyebrow.setFont(new Font(UiColors.FONT,Font.BOLD,9));eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title=new JLabel(greeting()+", equipo");
        title.setFont(new Font(UiColors.FONT,Font.BOLD,25));title.setForeground(UiColors.INK);
        JLabel subtitle=new JLabel("Todo lo importante de tu centro hospitalario, en un solo lugar.");
        subtitle.setFont(new Font(UiColors.FONT,Font.PLAIN,11));subtitle.setForeground(UiColors.MUTED);
        introText.add(eyebrow);introText.add(Box.createVerticalStrut(8));introText.add(title);
        introText.add(Box.createVerticalStrut(4));introText.add(subtitle);
        intro.add(introText,BorderLayout.CENTER);
        addDashboardRow(page,intro,0,0.10,6);

        AdaptiveGrid metrics=new AdaptiveGrid(4,2,2,12,12,120);
        metrics.add(patientsCard);metrics.add(occupiedCard);metrics.add(availableCard);metrics.add(occupancyCard);
        addDashboardRow(page,metrics,1,0.22,8);

        AdaptiveGrid middle=new AdaptiveGrid(2,2,1,14,14,220);
        UiCard floorsCard=card();
        floorsCard.setLayout(new BorderLayout(0,14));
        floorsCard.add(section("Ocupación por piso","Distribución de habitaciones ocupadas"),BorderLayout.NORTH);
        floorRows.setOpaque(false);floorRows.setLayout(new BoxLayout(floorRows,BoxLayout.Y_AXIS));
        floorsCard.add(floorRows,BorderLayout.CENTER);
        UiCard priorityCard=card();
        priorityCard.setLayout(new BorderLayout(0,12));
        priorityCard.add(section("Seguimiento prioritario","Pacientes con triage de mayor prioridad"),BorderLayout.NORTH);
        priorityRows.setOpaque(false);priorityRows.setLayout(new BoxLayout(priorityRows,BoxLayout.Y_AXIS));
        priorityCard.add(priorityRows,BorderLayout.CENTER);
        middle.add(floorsCard);middle.add(priorityCard);
        addDashboardRow(page,middle,2,0.31,8);

        AdaptiveGrid lower=new AdaptiveGrid(3,2,1,14,14,220);
        UiCard activityCard=card();activityCard.setLayout(new BorderLayout(0,12));
        JPanel activityHeader=new JPanel(new BorderLayout());activityHeader.setOpaque(false);
        activityHeader.add(section("Movimientos recientes","Últimos ingresos y salidas registrados"),BorderLayout.CENTER);
        activityCount.setFont(new Font(UiColors.FONT,Font.BOLD,9));activityCount.setForeground(UiColors.PRIMARY);
        activityHeader.add(activityCount,BorderLayout.EAST);
        activityCard.add(activityHeader,BorderLayout.NORTH);
        activityRows.setOpaque(false);activityRows.setLayout(new BoxLayout(activityRows,BoxLayout.Y_AXIS));
        activityCard.add(activityRows,BorderLayout.CENTER);

        UiCard gaugeCard=card();gaugeCard.setLayout(new BorderLayout(0,10));
        gaugeCard.add(section("Estado de camas","Disponibilidad en tiempo real"),BorderLayout.NORTH);
        JPanel gaugeWrap=new JPanel(new BorderLayout(8,0));gaugeWrap.setOpaque(false);
        gauge.setPreferredSize(new Dimension(128,128));gaugeWrap.add(gauge,BorderLayout.WEST);
        capacityRows.setOpaque(false);capacityRows.setLayout(new BoxLayout(capacityRows,BoxLayout.Y_AXIS));
        gaugeWrap.add(capacityRows,BorderLayout.CENTER);gaugeCard.add(gaugeWrap,BorderLayout.CENTER);

        UiCard summaryCard=card();summaryCard.setLayout(new BorderLayout(0,12));
        summaryCard.add(section("Capacidad hospitalaria","Resumen de las 30 habitaciones"),BorderLayout.NORTH);
        JPanel summary=new JPanel();summary.setOpaque(false);summary.setLayout(new BoxLayout(summary,BoxLayout.Y_AXIS));
        JLabel total=new JLabel("30 habitaciones");total.setFont(new Font(UiColors.FONT,Font.BOLD,22));total.setForeground(UiColors.INK);
        JLabel totalSub=new JLabel("Distribuidas en 5 pisos");totalSub.setFont(new Font(UiColors.FONT,Font.PLAIN,10));totalSub.setForeground(UiColors.MUTED);
        summary.add(Box.createVerticalStrut(8));summary.add(total);summary.add(Box.createVerticalStrut(4));summary.add(totalSub);
        summary.add(Box.createVerticalStrut(18));
        summary.add(legend(UiColors.TEAL,"Disponibles"));summary.add(Box.createVerticalStrut(10));
        summary.add(legend(UiColors.DANGER,"Ocupadas"));summary.add(Box.createVerticalGlue());
        summaryCard.add(summary,BorderLayout.CENTER);
        lower.add(activityCard);lower.add(gaugeCard);lower.add(summaryCard);
        addDashboardRow(page,lower,3,0.37,0);

        page.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override public void componentResized(java.awt.event.ComponentEvent e){
                int width=page.getWidth();
                page.setBorder(new EmptyBorder(8, width < 520 ? 10 : 20, 10, width < 520 ? 10 : 20));
                title.setFont(new Font(UiColors.FONT, Font.BOLD, width < 520 ? 20 : 25));
                subtitle.setText(width < 520 ? "<html>Todo lo importante de tu centro hospitalario,<br>en un solo lugar.</html>" : "Todo lo importante de tu centro hospitalario, en un solo lugar.");
                metrics.adapt(width);middle.adapt(width);lower.adapt(width);
            }
        });
        add(page,BorderLayout.CENTER);
        refresh();
    }

    private void addDashboardRow(JPanel parent,Component child,int row,double weightY,int bottomGap){
        GridBagConstraints c=new GridBagConstraints();
        c.gridx=0;c.gridy=row;c.weightx=1;c.weighty=weightY;c.fill=GridBagConstraints.BOTH;
        c.anchor=GridBagConstraints.CENTER;c.insets=new Insets(0,0,bottomGap,0);
        parent.add(child,c);
    }
    private String greeting(){
        int hour=LocalTime.now().getHour();
        if(hour<12)return "Buenos días";
        if(hour<19)return "Buenas tardes";
        return "Buenas noches";
    }
    private UiCard card(){return new UiCard(18,17);}
    private JPanel section(String title,String subtitle){
        JPanel p=new JPanel();p.setOpaque(false);p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        JLabel t=new JLabel(title);t.setFont(new Font(UiColors.FONT,Font.BOLD,13));t.setForeground(UiColors.INK);
        JLabel s=new JLabel(subtitle);s.setFont(new Font(UiColors.FONT,Font.PLAIN,9));s.setForeground(UiColors.MUTED);
        p.add(t);p.add(Box.createVerticalStrut(4));p.add(s);return p;
    }
    private JPanel legend(Color color,String label){
        JPanel p=new JPanel(new FlowLayout(FlowLayout.LEFT,7,0));p.setOpaque(false);
        JLabel dot=new JLabel("●");dot.setForeground(color);dot.setFont(new Font(UiColors.FONT,Font.BOLD,12));
        JLabel text=new JLabel(label);text.setForeground(UiColors.MUTED);text.setFont(new Font(UiColors.FONT,Font.PLAIN,10));
        p.add(dot);p.add(text);return p;
    }
    private void addFloorRow(int floor,long used){
        JPanel row=new JPanel(new BorderLayout(10,0));row.setOpaque(false);row.setBorder(new EmptyBorder(5,0,5,0));
        JLabel name=new JLabel("Piso "+floor);name.setFont(new Font(UiColors.FONT,Font.BOLD,10));name.setForeground(UiColors.INK);
        JLabel count=new JLabel(used+" / 6");count.setFont(new Font(UiColors.FONT,Font.BOLD,9));count.setForeground(UiColors.MUTED);
        ProgressTrack bar=new ProgressTrack((int)used,6,UiColors.TEAL);bar.setPreferredSize(new Dimension(40,9));
        row.add(name,BorderLayout.WEST);row.add(bar,BorderLayout.CENTER);row.add(count,BorderLayout.EAST);
        floorRows.add(row);
    }
    private void addPriorityRow(Paciente p,boolean assigned){
        JPanel row=new JPanel(new BorderLayout(9,0));row.setOpaque(false);row.setBorder(new EmptyBorder(7,0,7,0));
        JLabel initials=new JLabel(initials(p.getNombre()),SwingConstants.CENTER);initials.setOpaque(true);
        initials.setBackground(p.getNivelTriage()<=2?UiColors.DANGER_SOFT:UiColors.WARNING_SOFT);
        initials.setForeground(p.getNivelTriage()<=2?UiColors.DANGER:UiColors.WARNING);
        initials.setFont(new Font(UiColors.FONT,Font.BOLD,9));initials.setPreferredSize(new Dimension(32,32));
        JPanel detail=new JPanel();detail.setOpaque(false);detail.setLayout(new BoxLayout(detail,BoxLayout.Y_AXIS));
        JLabel name=new JLabel(p.getNombre());name.setFont(new Font(UiColors.FONT,Font.BOLD,10));name.setForeground(UiColors.INK);
        JLabel meta=new JLabel("Triage "+p.getNivelTriage()+"  ·  "+(assigned?"Habitación asignada":"Sin habitación"));
        meta.setFont(new Font(UiColors.FONT,Font.PLAIN,9));meta.setForeground(UiColors.MUTED);
        detail.add(name);detail.add(Box.createVerticalStrut(3));detail.add(meta);
        JLabel tag=new JLabel("T"+p.getNivelTriage());tag.setOpaque(true);
        tag.setBackground(p.getNivelTriage()<=2?UiColors.DANGER_SOFT:UiColors.WARNING_SOFT);
        tag.setForeground(p.getNivelTriage()<=2?UiColors.DANGER:UiColors.WARNING);
        tag.setFont(new Font(UiColors.FONT,Font.BOLD,9));tag.setBorder(new EmptyBorder(4,7,4,7));
        row.add(initials,BorderLayout.WEST);row.add(detail,BorderLayout.CENTER);row.add(tag,BorderLayout.EAST);
        priorityRows.add(row);
        priorityRows.add(new JSeparator());
    }
    private void addActivityRow(RegistroHistorial record){
        JPanel row=new JPanel(new BorderLayout(8,0));row.setOpaque(false);row.setBorder(new EmptyBorder(7,0,7,0));
        JLabel dot=new JLabel("●");dot.setForeground("INGRESO".equalsIgnoreCase(record.getAccion())?UiColors.TEAL:UiColors.WARNING);
        JPanel text=new JPanel();text.setOpaque(false);text.setLayout(new BoxLayout(text,BoxLayout.Y_AXIS));
        JLabel name=new JLabel(record.getNombrePaciente());name.setFont(new Font(UiColors.FONT,Font.BOLD,10));name.setForeground(UiColors.INK);
        JLabel action=new JLabel(record.getAccion()+" · Habitación "+record.getNumeroHabitacion());
        action.setFont(new Font(UiColors.FONT,Font.PLAIN,9));action.setForeground(UiColors.MUTED);
        text.add(name);text.add(Box.createVerticalStrut(3));text.add(action);
        JLabel time=new JLabel(record.getFechaHora().format(TIME));time.setFont(new Font(UiColors.FONT,Font.PLAIN,9));time.setForeground(UiColors.MUTED);
        row.add(dot,BorderLayout.WEST);row.add(text,BorderLayout.CENTER);row.add(time,BorderLayout.EAST);
        activityRows.add(row);activityRows.add(new JSeparator());
    }
    private String initials(String name){
        if(name==null||name.isBlank())return "—";
        String[] parts=name.trim().split("\\s+");
        return parts.length>1?(parts[0].substring(0,1)+parts[1].substring(0,1)).toUpperCase():
            parts[0].substring(0,Math.min(2,parts[0].length())).toUpperCase();
    }
    public void refresh(){
        List<Habitacion> rooms=controller.habitaciones();
        int used=(int)rooms.stream().filter(Habitacion::isOcupada).count();
        int free=rooms.size()-used;int pct=rooms.isEmpty()?0:(int)Math.round(used*100.0/rooms.size());
        patientsCard.setValue(String.valueOf(controller.pacientes().size()),"Pacientes registrados en el sistema");
        occupiedCard.setValue(String.valueOf(used),"Camas actualmente en uso");
        availableCard.setValue(String.valueOf(free),"Disponibles para asignación");
        occupancyCard.setValue(pct+"%","Porcentaje de ocupación");
        floorRows.removeAll();
        for(int floor=1;floor<=5;floor++){
            final int floorNumber=floor;
            long count=rooms.stream().filter(r->r.getPiso()==floorNumber&&r.isOcupada()).count();
            addFloorRow(floorNumber,count);
        }
        priorityRows.removeAll();
        Set<String> assigned=rooms.stream().filter(Habitacion::isOcupada).map(r->r.getPaciente().getIdentificacion()).collect(Collectors.toSet());
        List<Paciente> priorities=controller.pacientes().stream().filter(p->p.getNivelTriage()<=2).sorted(Comparator.comparingInt(Paciente::getNivelTriage)).limit(4).collect(Collectors.toList());
        if(priorities.isEmpty())addEmpty(priorityRows,"Aún no hay pacientes registrados.");
        else for(Paciente p:priorities)addPriorityRow(p,assigned.contains(p.getIdentificacion()));
        activityRows.removeAll();
        List<RegistroHistorial> history=controller.historial();
        activityCount.setText(history.size()+" movimientos");
        if(history.isEmpty())addEmpty(activityRows,"Aún no hay movimientos registrados.");
        else for(int i=history.size()-1;i>=Math.max(0,history.size()-4);i--)addActivityRow(history.get(i));
        gauge.setValue(pct);
        capacityRows.removeAll();
        JLabel occ=new JLabel(used+" ocupadas");occ.setFont(new Font(UiColors.FONT,Font.BOLD,10));occ.setForeground(UiColors.INK);
        JLabel avail=new JLabel(free+" disponibles");avail.setFont(new Font(UiColors.FONT,Font.BOLD,10));avail.setForeground(UiColors.INK);
        capacityRows.add(Box.createVerticalGlue());capacityRows.add(legend(UiColors.TEAL,"Disponibles"));capacityRows.add(Box.createVerticalStrut(5));capacityRows.add(avail);
        capacityRows.add(Box.createVerticalStrut(12));capacityRows.add(legend(UiColors.DANGER,"Ocupadas"));capacityRows.add(Box.createVerticalStrut(5));capacityRows.add(occ);capacityRows.add(Box.createVerticalGlue());
        floorRows.revalidate();priorityRows.revalidate();activityRows.revalidate();capacityRows.revalidate();
        repaint();
    }
    private void addEmpty(JPanel parent,String message){
        JLabel empty=new JLabel(message);empty.setFont(new Font(UiColors.FONT,Font.PLAIN,10));empty.setForeground(UiColors.MUTED);
        empty.setBorder(new EmptyBorder(10,0,10,0));parent.add(empty);
    }

    private static class AdaptiveGrid extends JPanel {
        private final int desktop,tablet,mobile,gap,cellHeight;
        AdaptiveGrid(int desktop,int tablet,int mobile,int hgap,int vgap,int cellHeight){
            this.desktop=desktop;this.tablet=tablet;this.mobile=mobile;this.gap=hgap;this.cellHeight=cellHeight;
            setOpaque(false);setLayout(new GridLayout(1,desktop,hgap,vgap));setMinimumSize(new Dimension(0,0));
            addComponentListener(new java.awt.event.ComponentAdapter(){
                @Override public void componentResized(java.awt.event.ComponentEvent e){adapt(getWidth());}
            });
        }
        void adapt(int width){
            int cols=width<520?mobile:width<980?tablet:desktop;
            if(!(getLayout() instanceof GridLayout gl)||gl.getColumns()!=cols){
                setLayout(new GridLayout(0,cols,gap,gap));
                int rows=Math.max(1,(getComponentCount()+cols-1)/cols);
                int height=rows*cellHeight+Math.max(0,rows-1)*gap;
                setPreferredSize(new Dimension(0,height));setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));setMinimumSize(new Dimension(0,0));
                revalidate();
            }
        }
    }
    private static class ProgressTrack extends JComponent {
        private final int value,max;private final Color color;
        ProgressTrack(int value,int max,Color color){this.value=value;this.max=Math.max(1,max);this.color=color;setOpaque(false);}
        @Override protected void paintComponent(Graphics graphics){
            Graphics2D g=(Graphics2D)graphics.create();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            int h=Math.max(6,getHeight());g.setColor(new Color(235,240,247));g.fillRoundRect(0,(getHeight()-h)/2,getWidth(),h,h,h);
            int w=(int)(getWidth()*Math.max(0,Math.min(1,value/(double)max)));
            g.setColor(color);g.fillRoundRect(0,(getHeight()-h)/2,w,h,h,h);g.dispose();
        }
    }
    private static class DonutGauge extends JComponent {
        private int value;
        void setValue(int value){this.value=Math.max(0,Math.min(100,value));repaint();}
        @Override protected void paintComponent(Graphics graphics){
            super.paintComponent(graphics);Graphics2D g=(Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            int size=Math.min(getWidth(),getHeight())-18,x=(getWidth()-size)/2,y=(getHeight()-size)/2;
            g.setStroke(new BasicStroke(10,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            g.setColor(new Color(235,240,247));g.drawArc(x,y,size,size,90, -360);
            g.setColor(UiColors.TEAL);g.drawArc(x,y,size,size,90,-(int)(360*value/100.0));
            g.setFont(new Font(UiColors.FONT,Font.BOLD,20));g.setColor(UiColors.INK);
            String text=value+"%";FontMetrics fm=g.getFontMetrics();g.drawString(text,(getWidth()-fm.stringWidth(text))/2,(getHeight()+fm.getAscent()-fm.getDescent())/2);
            g.dispose();
        }
    }
}