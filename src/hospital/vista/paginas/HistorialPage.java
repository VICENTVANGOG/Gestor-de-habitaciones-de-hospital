package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.RegistroHistorial;
import hospital.vista.atomos.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Trazabilidad de ingresos y salidas con presentación adaptable. */
public class HistorialPage extends JPanel {
    private static final DateTimeFormatter FORMAT=DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm");
    private final HospitalController controller;
    private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Fecha y hora","Movimiento","Habitación","Paciente","Identificación"},0){
        @Override public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable table=new JTable(model);
    private final JTextField search=new JTextField();
    private final JLabel count=new JLabel();
    private final JPanel page=new JPanel(new BorderLayout(0,18));
    private final JPanel heading=new JPanel(new BorderLayout(10,0));
    private final JPanel labels=new JPanel();
    private final JPanel toolbar=new JPanel(new BorderLayout(10,0));
    private final JPanel mobileList=new JPanel();
    private final JPanel listing=new JPanel(new CardLayout());
    private final JScrollPane tableScroll;
    private final JScrollPane mobileScroll;

    public HistorialPage(HospitalController controller){
        this.controller=controller;setLayout(new BorderLayout());setOpaque(false);
        page.setOpaque(false);page.setBorder(new EmptyBorder(20,26,26,26));
        labels.setOpaque(false);labels.setLayout(new BoxLayout(labels,BoxLayout.Y_AXIS));
        JLabel eyebrow=new JLabel("TRAZABILIDAD");eyebrow.setFont(new Font(UiColors.FONT,Font.BOLD,9));eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title=new JLabel("Historial hospitalario");title.setFont(new Font(UiColors.FONT,Font.BOLD,25));title.setForeground(UiColors.INK);
        JLabel subtitle=new JLabel("Registro cronológico de ingresos y salidas de pacientes.");
        subtitle.setFont(new Font(UiColors.FONT,Font.PLAIN,11));subtitle.setForeground(UiColors.MUTED);
        labels.add(eyebrow);labels.add(Box.createVerticalStrut(7));labels.add(title);labels.add(Box.createVerticalStrut(4));labels.add(subtitle);
        UiButton refresh=new UiButton("Actualizar",false);heading.setOpaque(false);
        heading.add(labels,BorderLayout.CENTER);heading.add(refresh,BorderLayout.EAST);page.add(heading,BorderLayout.NORTH);

        UiCard card=new UiCard(18,18);card.setLayout(new BorderLayout(0,14));toolbar.setOpaque(false);
        JLabel tableTitle=new JLabel("Movimientos registrados");tableTitle.setFont(new Font(UiColors.FONT,Font.BOLD,13));tableTitle.setForeground(UiColors.INK);
        count.setFont(new Font(UiColors.FONT,Font.PLAIN,9));count.setForeground(UiColors.MUTED);
        JPanel titleBox=new JPanel();titleBox.setOpaque(false);titleBox.setLayout(new BoxLayout(titleBox,BoxLayout.Y_AXIS));
        titleBox.add(tableTitle);titleBox.add(Box.createVerticalStrut(4));titleBox.add(count);
        search.setFont(new Font(UiColors.FONT,Font.PLAIN,11));search.setPreferredSize(new Dimension(220,36));
        search.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UiColors.BORDER),new EmptyBorder(7,10,7,10)));
        toolbar.add(titleBox,BorderLayout.WEST);toolbar.add(search,BorderLayout.EAST);

        PacientesPage.styleTable(table);tableScroll=new JScrollPane(table);tableScroll.setBorder(BorderFactory.createLineBorder(UiColors.BORDER));
        tableScroll.getViewport().setBackground(Color.WHITE);
        mobileList.setOpaque(false);mobileList.setLayout(new BoxLayout(mobileList,BoxLayout.Y_AXIS));
        mobileScroll=new JScrollPane(mobileList);mobileScroll.setBorder(null);mobileScroll.setOpaque(false);mobileScroll.getViewport().setOpaque(false);
        listing.add(tableScroll,"table");listing.add(mobileScroll,"mobile");
        card.add(toolbar,BorderLayout.NORTH);card.add(listing,BorderLayout.CENTER);page.add(card,BorderLayout.CENTER);
        add(new JScrollPane(page),BorderLayout.CENTER);
        refresh.addActionListener(e->refresh());
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            private void filter(){refreshMobile();}
            public void insertUpdate(javax.swing.event.DocumentEvent e){filter();}
            public void removeUpdate(javax.swing.event.DocumentEvent e){filter();}
            public void changedUpdate(javax.swing.event.DocumentEvent e){filter();}
        });
        page.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override public void componentResized(java.awt.event.ComponentEvent e){
                boolean narrow=page.getWidth()<720;
                page.setBorder(new EmptyBorder(16,narrow?12:26,22,narrow?12:26));
                heading.setLayout(new BorderLayout(8,narrow?10:0));
                if(narrow){heading.add(labels,BorderLayout.NORTH);heading.add(refresh,BorderLayout.SOUTH);}
                else{heading.add(labels,BorderLayout.CENTER);heading.add(refresh,BorderLayout.EAST);}
                toolbar.setLayout(new BorderLayout(8,narrow?10:0));
                if(narrow){toolbar.add(titleBox,BorderLayout.NORTH);toolbar.add(search,BorderLayout.SOUTH);search.setPreferredSize(new Dimension(0,38));}
                else{toolbar.add(titleBox,BorderLayout.WEST);toolbar.add(search,BorderLayout.EAST);search.setPreferredSize(new Dimension(220,36));}
                ((CardLayout)listing.getLayout()).show(listing,narrow?"mobile":"table");page.revalidate();
            }
        });
        refresh();
    }
    public void refresh(){
        model.setRowCount(0);
        for(RegistroHistorial r:controller.historial())model.addRow(new Object[]{
            r.getFechaHora().format(FORMAT),r.getAccion(),"Habitación "+r.getNumeroHabitacion(),r.getNombrePaciente(),r.getIdentificacionPaciente()});
        count.setText(model.getRowCount()+" movimientos registrados");refreshMobile();
    }
    private void refreshMobile(){
        mobileList.removeAll();String q=search.getText().trim().toLowerCase();
        List<RegistroHistorial> records=controller.historial();int shown=0;
        for(int i=records.size()-1;i>=0;i--){
            RegistroHistorial r=records.get(i);
            String searchable=(r.getNombrePaciente()+" "+r.getIdentificacionPaciente()+" "+r.getAccion()+" "+r.getNumeroHabitacion()).toLowerCase();
            if(!q.isEmpty()&&!searchable.contains(q))continue;
            UiCard item=new UiCard(14,12);item.setLayout(new BorderLayout(10,0));
            JPanel text=new JPanel();text.setOpaque(false);text.setLayout(new BoxLayout(text,BoxLayout.Y_AXIS));
            JLabel name=new JLabel(r.getNombrePaciente());name.setFont(new Font(UiColors.FONT,Font.BOLD,11));name.setForeground(UiColors.INK);
            JLabel meta=new JLabel(r.getIdentificacionPaciente()+"  ·  Habitación "+r.getNumeroHabitacion());
            meta.setFont(new Font(UiColors.FONT,Font.PLAIN,9));meta.setForeground(UiColors.MUTED);
            JLabel date=new JLabel(r.getFechaHora().format(FORMAT));date.setFont(new Font(UiColors.FONT,Font.PLAIN,9));date.setForeground(UiColors.MUTED);
            text.add(name);text.add(Box.createVerticalStrut(4));text.add(meta);text.add(Box.createVerticalStrut(4));text.add(date);
            JLabel action=new JLabel(r.getAccion());action.setOpaque(true);
            boolean ingreso="INGRESO".equalsIgnoreCase(r.getAccion());
            action.setBackground(ingreso?UiColors.TEAL_SOFT:UiColors.WARNING_SOFT);
            action.setForeground(ingreso?UiColors.TEAL:UiColors.WARNING);
            action.setFont(new Font(UiColors.FONT,Font.BOLD,9));action.setBorder(new EmptyBorder(5,7,5,7));
            item.add(text,BorderLayout.CENTER);item.add(action,BorderLayout.EAST);
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE,82));mobileList.add(item);mobileList.add(Box.createVerticalStrut(8));shown++;
        }
        if(shown==0){JLabel empty=new JLabel(q.isEmpty()?"Aún no hay movimientos registrados.":"No se encontraron movimientos.");
            empty.setFont(new Font(UiColors.FONT,Font.PLAIN,10));empty.setForeground(UiColors.MUTED);empty.setBorder(new EmptyBorder(16,0,16,0));mobileList.add(empty);}
        mobileList.revalidate();mobileList.repaint();
    }
}