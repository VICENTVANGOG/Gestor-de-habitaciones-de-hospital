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
import java.util.ArrayList;

/** Trazabilidad de ingresos y salidas con presentación adaptable. */
public class HistorialPage extends JPanel {
    private static final DateTimeFormatter FORMAT=DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm");
    private final HospitalController controller;
    private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Fecha y hora","Movimiento","Habitación","Paciente","Identificación"},0){
        @Override public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable table=new JTable(model);
    private static final int PAGE_SIZE=8;
    private final List<RegistroHistorial> filteredRecords=new ArrayList<>();
    private int currentPage=0;
    private final JPanel pagination=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
    private final JLabel pageInfo=new JLabel();
    private final JButton previousPage=new JButton("Anterior"),nextPage=new JButton("Siguiente");
    private final UiSearchField search=new UiSearchField("Buscar por paciente, documento o movimiento…",240);
    private final JLabel count=new JLabel();
    private final JPanel page=new JPanel(new BorderLayout(0,18));
    private final JPanel heading=new JPanel(new BorderLayout(10,0));
    private final JPanel labels=new JPanel();
    private final JPanel toolbar=new JPanel(new BorderLayout(10,0));
    private final JPanel mobileList=new JPanel();
    private final JPanel listing=new JPanel(new BorderLayout());
    private final JScrollPane tableScroll;

    public HistorialPage(HospitalController controller){
        this.controller=controller;setLayout(new BorderLayout());setOpaque(false);
        page.setOpaque(false);page.setBorder(new EmptyBorder(20,26,26,26));
        labels.setOpaque(false);labels.setLayout(new BoxLayout(labels,BoxLayout.Y_AXIS));
        JLabel eyebrow=new JLabel("TRAZABILIDAD");eyebrow.setFont(new Font(UiColors.FONT,Font.BOLD,9));eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title=new JLabel("Historial hospitalario");title.setFont(new Font(UiColors.FONT,Font.BOLD,25));title.setForeground(UiColors.INK);
        JLabel subtitle=new JLabel("<html>Registro cronológico de ingresos y salidas<br>de pacientes.</html>");
        subtitle.setFont(new Font(UiColors.FONT,Font.PLAIN,11));subtitle.setForeground(UiColors.MUTED);
        labels.add(eyebrow);labels.add(Box.createVerticalStrut(7));labels.add(title);labels.add(Box.createVerticalStrut(4));labels.add(subtitle);
        heading.setOpaque(false);
        heading.add(labels,BorderLayout.CENTER);page.add(heading,BorderLayout.NORTH);

        UiCard card=new UiCard(18,18);card.setLayout(new BorderLayout(0,14));toolbar.setOpaque(false);
        JLabel tableTitle=new JLabel("Movimientos registrados");tableTitle.setFont(new Font(UiColors.FONT,Font.BOLD,13));tableTitle.setForeground(UiColors.INK);
        count.setFont(new Font(UiColors.FONT,Font.PLAIN,9));count.setForeground(UiColors.MUTED);
        JPanel titleBox=new JPanel();titleBox.setOpaque(false);titleBox.setLayout(new BoxLayout(titleBox,BoxLayout.Y_AXIS));
        titleBox.add(tableTitle);titleBox.add(Box.createVerticalStrut(4));titleBox.add(count);
        search.setPreferredSize(new Dimension(240,40));
        toolbar.add(titleBox,BorderLayout.WEST);toolbar.add(search,BorderLayout.EAST);

        PacientesPage.styleTable(table);
        table.getColumnModel().getColumn(1).setCellRenderer(new MovementRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(145);
        table.getColumnModel().getColumn(1).setPreferredWidth(105);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(190);
        table.getColumnModel().getColumn(4).setPreferredWidth(145);
        tableScroll=new JScrollPane(table);tableScroll.setBorder(null);
        tableScroll.getViewport().setBackground(Color.WHITE);
        tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        table.setPreferredScrollableViewportSize(new Dimension(760,PAGE_SIZE*52));
        configurePagination();
        mobileList.setOpaque(false);mobileList.setLayout(new BoxLayout(mobileList,BoxLayout.Y_AXIS));
        listing.add(tableScroll,BorderLayout.CENTER);
        card.add(toolbar,BorderLayout.NORTH);card.add(listing,BorderLayout.CENTER);card.add(pagination,BorderLayout.SOUTH);page.add(card,BorderLayout.CENTER);
        add(page,BorderLayout.CENTER);
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            private void filter(){currentPage=0;rebuildFilteredRecords();}
            public void insertUpdate(javax.swing.event.DocumentEvent e){filter();}
            public void removeUpdate(javax.swing.event.DocumentEvent e){filter();}
            public void changedUpdate(javax.swing.event.DocumentEvent e){filter();}
        });
        page.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override public void componentResized(java.awt.event.ComponentEvent e){
                boolean narrow=page.getWidth()<720;
                page.setBorder(new EmptyBorder(16,narrow?12:26,22,narrow?12:26));
                heading.setLayout(new BorderLayout(8,narrow?10:0));
                if(narrow){heading.add(labels,BorderLayout.NORTH);}
                else{heading.add(labels,BorderLayout.CENTER);}
                toolbar.setLayout(new BorderLayout(8,narrow?10:0));
                if(narrow){toolbar.add(titleBox,BorderLayout.NORTH);toolbar.add(search,BorderLayout.SOUTH);search.setPreferredSize(new Dimension(0,40));}
                else{toolbar.add(titleBox,BorderLayout.WEST);toolbar.add(search,BorderLayout.EAST);search.setPreferredSize(new Dimension(240,40));}
                listing.removeAll();listing.add(narrow?mobileList:tableScroll,BorderLayout.CENTER);listing.revalidate();page.revalidate();currentPage=0;rebuildFilteredRecords();
            }
        });
        refresh();
    }
    private void configurePagination(){
        pagination.setOpaque(false);pageInfo.setFont(new Font(UiColors.FONT,Font.PLAIN,10));pageInfo.setForeground(UiColors.MUTED);
        for(JButton b:new JButton[]{previousPage,nextPage}){b.setFont(new Font(UiColors.FONT,Font.BOLD,10));b.setFocusPainted(false);b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UiColors.BORDER),new EmptyBorder(6,11,6,11)));b.setBackground(Color.WHITE);b.setForeground(UiColors.INK);}
        previousPage.addActionListener(e->{if(currentPage>0){currentPage--;rebuildFilteredRecords();}});
        nextPage.addActionListener(e->{int pages=Math.max(1,(filteredRecords.size()+pageSize()-1)/pageSize());if(currentPage<pages-1){currentPage++;rebuildFilteredRecords();}});
        pagination.setBorder(new EmptyBorder(12,0,0,0));pagination.add(pageInfo);pagination.add(previousPage);pagination.add(nextPage);
    }
    private void updatePagination(){int pages=Math.max(1,(filteredRecords.size()+PAGE_SIZE-1)/PAGE_SIZE);pageInfo.setText("Página "+(currentPage+1)+" de "+pages+"  ·  "+filteredRecords.size()+" registros");previousPage.setEnabled(currentPage>0);nextPage.setEnabled(currentPage<pages-1);previousPage.setVisible(pages>1);nextPage.setVisible(pages>1);pageInfo.setVisible(pages>1);pagination.revalidate();pagination.repaint();}

    private static class MovementRenderer extends javax.swing.table.DefaultTableCellRenderer {
        MovementRenderer(){setHorizontalAlignment(SwingConstants.CENTER);}
        @Override public Component getTableCellRendererComponent(JTable table,Object value,boolean selected,boolean focus,int row,int col){
            super.getTableCellRendererComponent(table,value,selected,focus,row,col);
            boolean entry="INGRESO".equalsIgnoreCase(String.valueOf(value));
            setText(entry?"INGRESO":"SALIDA");setOpaque(true);setFont(new Font(UiColors.FONT,Font.BOLD,9));
            setHorizontalAlignment(SwingConstants.CENTER);setForeground(entry?UiColors.TEAL:UiColors.WARNING);
            setBackground(selected?UiColors.PRIMARY_SOFT:entry?UiColors.TEAL_SOFT:UiColors.WARNING_SOFT);
            setBorder(new EmptyBorder(5,7,5,7));return this;
        }
    }
    public void refresh(){
        currentPage=0;rebuildFilteredRecords();
    }
    private int pageSize(){return page.getWidth()<720?4:PAGE_SIZE;}

    private void rebuildFilteredRecords(){
        String q=search.getText().trim().toLowerCase();filteredRecords.clear();List<RegistroHistorial> records=controller.historial();
        for(int i=records.size()-1;i>=0;i--){RegistroHistorial r=records.get(i);String searchable=(r.getNombrePaciente()+" "+r.getIdentificacionPaciente()+" "+r.getAccion()+" "+r.getNumeroHabitacion()).toLowerCase();if(q.isEmpty()||searchable.contains(q))filteredRecords.add(r);}
        int pages=Math.max(1,(filteredRecords.size()+PAGE_SIZE-1)/PAGE_SIZE);currentPage=Math.min(currentPage,pages-1);count.setText(filteredRecords.size()+" movimientos encontrados");
        model.setRowCount(0);int from=currentPage*pageSize(),to=Math.min(from+pageSize(),filteredRecords.size());
        for(int i=from;i<to;i++){RegistroHistorial r=filteredRecords.get(i);model.addRow(new Object[]{r.getFechaHora().format(FORMAT),r.getAccion(),"Habitación "+r.getNumeroHabitacion(),r.getNombrePaciente(),r.getIdentificacionPaciente()});}
        refreshMobile();updatePagination();
    }
    private void refreshMobile(){
        mobileList.removeAll();int shown=0;int from=currentPage*PAGE_SIZE,to=Math.min(from+PAGE_SIZE,filteredRecords.size());
        for(int i=from;i<to;i++){
            RegistroHistorial r=filteredRecords.get(i);
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
        if(shown==0){JLabel empty=new JLabel(search.getText().trim().isEmpty()?"Aún no hay movimientos registrados.":"No se encontraron movimientos.");
            empty.setFont(new Font(UiColors.FONT,Font.PLAIN,10));empty.setForeground(UiColors.MUTED);empty.setBorder(new EmptyBorder(16,0,16,0));mobileList.add(empty);}
        mobileList.revalidate();mobileList.repaint();
    }
}