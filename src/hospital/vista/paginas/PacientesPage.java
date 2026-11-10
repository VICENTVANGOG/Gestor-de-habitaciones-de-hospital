package hospital.vista.paginas;

import hospital.controlador.HospitalController;
import hospital.modelos.Paciente;
import hospital.vista.atomos.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/** Directorio clínico adaptable: tabla de escritorio y fichas legibles en móvil. */
public class PacientesPage extends JPanel {
    private final HospitalController controller;
    private final DefaultTableModel model=new DefaultTableModel(
        new Object[]{"Identificación","Nombre completo","Edad","Triage","Motivo de ingreso"},0){
        @Override public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable table=new JTable(model);
    private static final int PAGE_SIZE=8;
    private final List<Paciente> filteredPatients=new ArrayList<>();
    private int currentPage=0;
    private final JPanel pagination=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
    private final JLabel pageInfo=new JLabel();
    private final JButton previousPage=new JButton("Anterior"),nextPage=new JButton("Siguiente");
    private final UiSearchField search=new UiSearchField("Buscar por nombre, identificación o motivo…",250);
    private final JLabel count=new JLabel("0 pacientes");
    private final JPanel page=new JPanel(new BorderLayout(0,18));
    private final JPanel heading=new JPanel(new BorderLayout(12,0));
    private final JPanel labels=new JPanel();
    private final JPanel toolbar=new JPanel(new BorderLayout(12,0));
    private final JPanel cardsList=new JPanel();
    private final JPanel listing=new JPanel(new BorderLayout());
    private final JScrollPane tableScroll;

    public PacientesPage(HospitalController controller){
        this.controller=controller;setLayout(new BorderLayout());setOpaque(false);
        page.setOpaque(false);page.setBorder(new EmptyBorder(20,26,26,26));
        heading.setOpaque(false);
        labels.setOpaque(false);labels.setLayout(new BoxLayout(labels,BoxLayout.Y_AXIS));
        JLabel eyebrow=new JLabel("DIRECTORIO CLÍNICO");eyebrow.setFont(new Font(UiColors.FONT,Font.BOLD,9));eyebrow.setForeground(UiColors.PRIMARY);
        JLabel title=new JLabel("Pacientes");title.setFont(new Font(UiColors.FONT,Font.BOLD,25));title.setForeground(UiColors.INK);
        JLabel subtitle=new JLabel("<html>Consulta y administra los registros de pacientes<br>hospitalizados.</html>");
        subtitle.setFont(new Font(UiColors.FONT,Font.PLAIN,11));subtitle.setForeground(UiColors.MUTED);
        labels.add(eyebrow);labels.add(Box.createVerticalStrut(7));labels.add(title);labels.add(Box.createVerticalStrut(4));labels.add(subtitle);
        UiButton add=new UiButton("Registrar paciente");
        add.setIcon(new UiIcon("plus",Color.WHITE,12));add.setIconTextGap(4);add.setFont(new Font(UiColors.FONT,Font.BOLD,9));
        add.setPreferredSize(new Dimension(136,32));add.setMinimumSize(new Dimension(136,32));add.setMaximumSize(new Dimension(136,32));add.setBorder(new EmptyBorder(6,8,6,8));
        heading.add(labels,BorderLayout.CENTER);heading.add(add,BorderLayout.EAST);page.add(heading,BorderLayout.NORTH);

        UiCard card=new UiCard(18,18);card.setLayout(new BorderLayout(0,14));
        toolbar.setOpaque(false);
        JLabel tableTitle=new JLabel("Listado de pacientes");tableTitle.setFont(new Font(UiColors.FONT,Font.BOLD,13));tableTitle.setForeground(UiColors.INK);
        count.setFont(new Font(UiColors.FONT,Font.PLAIN,9));count.setForeground(UiColors.MUTED);
        JPanel titleBox=new JPanel();titleBox.setOpaque(false);titleBox.setLayout(new BoxLayout(titleBox,BoxLayout.Y_AXIS));
        titleBox.add(tableTitle);titleBox.add(Box.createVerticalStrut(4));titleBox.add(count);
        search.setPreferredSize(new Dimension(250,40));
        toolbar.add(titleBox,BorderLayout.WEST);toolbar.add(search,BorderLayout.EAST);

        styleTable(table);
        table.getColumnModel().getColumn(3).setCellRenderer(new TriageRenderer());
        tableScroll=new JScrollPane(table);tableScroll.setBorder(null);
        tableScroll.getViewport().setBackground(Color.WHITE);
        tableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        tableScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        table.setPreferredScrollableViewportSize(new Dimension(760,PAGE_SIZE*52));
        configurePagination();
        cardsList.setOpaque(false);cardsList.setLayout(new BoxLayout(cardsList,BoxLayout.Y_AXIS));
        listing.add(tableScroll,BorderLayout.CENTER);
        card.add(toolbar,BorderLayout.NORTH);card.add(listing,BorderLayout.CENTER);card.add(pagination,BorderLayout.SOUTH);page.add(card,BorderLayout.CENTER);
        add(page,BorderLayout.CENTER);
        add.getAccessibleContext().setAccessibleName("Registrar paciente");
        add.addActionListener(e->register());
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            private void filter(){
                String q=search.getText().trim();
                currentPage=0;
                rebuildFilteredPatients();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e){filter();}
            public void removeUpdate(javax.swing.event.DocumentEvent e){filter();}
            public void changedUpdate(javax.swing.event.DocumentEvent e){filter();}
        });
        page.addComponentListener(new java.awt.event.ComponentAdapter(){
            @Override public void componentResized(java.awt.event.ComponentEvent e){
                boolean narrow=page.getWidth()<720;
                page.setBorder(new EmptyBorder(16,narrow?12:26,22,narrow?12:26));
                heading.setLayout(new BorderLayout(8,narrow?10:0));
                if(narrow){heading.add(labels,BorderLayout.NORTH);heading.add(add,BorderLayout.SOUTH);}
                else{heading.add(labels,BorderLayout.CENTER);heading.add(add,BorderLayout.EAST);}
                toolbar.setLayout(new BorderLayout(8,narrow?10:0));
                if(narrow){toolbar.add(titleBox,BorderLayout.NORTH);toolbar.add(search,BorderLayout.SOUTH);search.setPreferredSize(new Dimension(0,40));}
                else{toolbar.add(titleBox,BorderLayout.WEST);toolbar.add(search,BorderLayout.EAST);search.setPreferredSize(new Dimension(250,40));}
                listing.removeAll();listing.add(narrow?cardsList:tableScroll,BorderLayout.CENTER);listing.revalidate();
                page.revalidate();
                currentPage=0;rebuildFilteredPatients();
            }
        });
        refresh();
    }

    private void configurePagination(){
        pagination.setOpaque(false);pageInfo.setFont(new Font(UiColors.FONT,Font.PLAIN,10));pageInfo.setForeground(UiColors.MUTED);
        for(JButton b:new JButton[]{previousPage,nextPage}){b.setFont(new Font(UiColors.FONT,Font.BOLD,10));b.setFocusPainted(false);b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UiColors.BORDER),new EmptyBorder(6,11,6,11)));b.setBackground(Color.WHITE);b.setForeground(UiColors.INK);}
        previousPage.addActionListener(e->{if(currentPage>0){currentPage--;rebuildFilteredPatients();}});
        nextPage.addActionListener(e->{int pages=Math.max(1,(filteredPatients.size()+pageSize()-1)/pageSize());if(currentPage<pages-1){currentPage++;rebuildFilteredPatients();}});
        pagination.setBorder(new EmptyBorder(12,0,0,0));pagination.add(pageInfo);pagination.add(previousPage);pagination.add(nextPage);
    }
    private void updatePagination(){int pages=Math.max(1,(filteredPatients.size()+pageSize()-1)/pageSize());pageInfo.setText("Página "+(currentPage+1)+" de "+pages+"  ·  "+filteredPatients.size()+" registros");previousPage.setEnabled(currentPage>0);nextPage.setEnabled(currentPage<pages-1);previousPage.setVisible(pages>1);nextPage.setVisible(pages>1);pageInfo.setVisible(pages>1);pagination.revalidate();pagination.repaint();}

    private void register(){
        JTextField id=field(),name=field(),age=field(),triage=field(),reason=field();
        JPanel fields=new JPanel(new GridLayout(0,2,14,13));fields.setOpaque(false);
        fields.setBorder(new EmptyBorder(8,2,8,2));
        fields.add(labelField("IDENTIFICACIÓN",id));fields.add(labelField("NOMBRE COMPLETO",name));
        fields.add(labelField("EDAD",age));fields.add(labelField("NIVEL DE TRIAGE (1–5)",triage));
        fields.add(labelField("MOTIVO DE INGRESO",reason));
        fields.setPreferredSize(new Dimension(490,205));
        id.setToolTipText("Número de documento del paciente");
        name.setToolTipText("Nombre y apellidos");
        age.setToolTipText("Edad en años");
        triage.setToolTipText("Valor entre 1 y 5");
        reason.setToolTipText("Motivo clínico del ingreso");
        if(!UiDialogs.confirm(this,"Nuevo paciente","Registra los datos básicos para crear la ficha clínica.",fields))return;
        try{
            controller.registrarPaciente(id.getText(),name.getText(),Integer.parseInt(age.getText().trim()),
                Integer.parseInt(triage.getText().trim()),reason.getText());
            refresh();UiDialogs.message(this,"Registro exitoso","El paciente fue registrado correctamente.",JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){UiDialogs.message(this,"No se pudo registrar",ex.getMessage(),JOptionPane.WARNING_MESSAGE);}
    }
    private JTextField field(){JTextField f=new UiTextField(16);f.setFont(new Font(UiColors.FONT,Font.PLAIN,12));f.setPreferredSize(new Dimension(210,40));return f;}
    private JPanel labelField(String text,JTextField field){
        JPanel p=new JPanel(new BorderLayout(0,7));p.setOpaque(false);
        JLabel l=new JLabel(text);l.setFont(new Font(UiColors.FONT,Font.BOLD,9));l.setForeground(UiColors.INK);
        p.add(l,BorderLayout.NORTH);p.add(field,BorderLayout.CENTER);return p;
    }
    public void refresh(){
        currentPage=0;
        rebuildFilteredPatients();
    }
    private int pageSize(){return page.getWidth()<720?4:PAGE_SIZE;}

    private void rebuildFilteredPatients(){
        String q=search.getText().trim().toLowerCase();filteredPatients.clear();
        for(Paciente p:controller.pacientes()){
            String text=(p.getIdentificacion()+" "+p.getNombre()+" "+p.getMotivoIngreso()+" "+p.getNivelTriage()).toLowerCase();
            if(q.isEmpty()||text.contains(q))filteredPatients.add(p);
        }
        int pages=Math.max(1,(filteredPatients.size()+PAGE_SIZE-1)/PAGE_SIZE);currentPage=Math.min(currentPage,pages-1);
        count.setText(filteredPatients.size()+(filteredPatients.size()==1?" paciente encontrado":" pacientes encontrados"));
        model.setRowCount(0);int from=currentPage*pageSize(),to=Math.min(from+pageSize(),filteredPatients.size());
        for(int i=from;i<to;i++){Paciente p=filteredPatients.get(i);model.addRow(new Object[]{p.getIdentificacion(),p.getNombre(),p.getEdad(),p.getNivelTriage(),p.getMotivoIngreso()});}
        refreshCards();updatePagination();
    }
    private void refreshCards(){
        cardsList.removeAll();int shown=0;int from=currentPage*pageSize(),to=Math.min(from+pageSize(),filteredPatients.size());
        for(int i=from;i<to;i++){
            Paciente p=filteredPatients.get(i);
            UiCard item=new UiCard(14,12);item.setLayout(new BorderLayout(10,0));
            JPanel identity=new JPanel();identity.setOpaque(false);identity.setLayout(new BoxLayout(identity,BoxLayout.Y_AXIS));
            JLabel name=new JLabel(p.getNombre());name.setFont(new Font(UiColors.FONT,Font.BOLD,12));name.setForeground(UiColors.INK);
            JLabel id=new JLabel("ID  "+p.getIdentificacion()+"  ·  "+p.getEdad()+" años");
            id.setFont(new Font(UiColors.FONT,Font.PLAIN,9));id.setForeground(UiColors.MUTED);
            JLabel reason=new JLabel(p.getMotivoIngreso());reason.setFont(new Font(UiColors.FONT,Font.PLAIN,10));reason.setForeground(UiColors.MUTED);
            identity.add(name);identity.add(Box.createVerticalStrut(4));identity.add(id);identity.add(Box.createVerticalStrut(5));identity.add(reason);
            JLabel triage=new JLabel("T"+p.getNivelTriage(),SwingConstants.CENTER);triage.setOpaque(true);
            triage.setBackground(p.getNivelTriage()<=2?UiColors.DANGER_SOFT:UiColors.WARNING_SOFT);
            triage.setForeground(p.getNivelTriage()<=2?UiColors.DANGER:UiColors.WARNING);
            triage.setFont(new Font(UiColors.FONT,Font.BOLD,10));triage.setPreferredSize(new Dimension(34,30));
            item.add(identity,BorderLayout.CENTER);item.add(triage,BorderLayout.EAST);
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE,86));cardsList.add(item);cardsList.add(Box.createVerticalStrut(9));shown++;
        }
        if(shown==0){JLabel empty=new JLabel(search.getText().trim().isEmpty()?"Aún no hay pacientes registrados.":"No se encontraron pacientes.");
            empty.setFont(new Font(UiColors.FONT,Font.PLAIN,11));empty.setForeground(UiColors.MUTED);empty.setBorder(new EmptyBorder(16,4,16,4));cardsList.add(empty);}
        cardsList.revalidate();cardsList.repaint();
    }
    private static class TriageRenderer extends javax.swing.table.DefaultTableCellRenderer {
        TriageRenderer(){setHorizontalAlignment(SwingConstants.CENTER);}
        @Override public Component getTableCellRendererComponent(JTable table,Object value,boolean selected,boolean focus,int row,int col){
            super.getTableCellRendererComponent(table,value,selected,focus,row,col);
            int triage=Integer.parseInt(String.valueOf(value));setText("Triage "+triage);
            setOpaque(true);setHorizontalAlignment(SwingConstants.CENTER);setFont(new Font(UiColors.FONT,Font.BOLD,9));
            boolean urgent=triage<=2;setBackground(selected?UiColors.PRIMARY_SOFT:urgent?UiColors.DANGER_SOFT:UiColors.WARNING_SOFT);
            setForeground(urgent?UiColors.DANGER:UiColors.WARNING);setBorder(new EmptyBorder(5,7,5,7));return this;
        }
    }
    static void styleTable(JTable table){
        table.setRowHeight(52);table.setFont(new Font(UiColors.FONT,Font.PLAIN,11));table.setForeground(UiColors.INK);
        table.setSelectionBackground(UiColors.PRIMARY_SOFT);table.setSelectionForeground(UiColors.INK);
        table.setGridColor(new Color(244,247,251));table.setShowVerticalLines(false);table.setShowHorizontalLines(true);table.setIntercellSpacing(new Dimension(0,0));
        table.setFillsViewportHeight(true);table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setPreferredSize(new Dimension(0,44));table.getTableHeader().setFont(new Font(UiColors.FONT,Font.BOLD,10));
        table.getTableHeader().setBackground(new Color(249,251,254));table.getTableHeader().setForeground(UiColors.MUTED);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0,0,1,0,UiColors.BORDER));
        table.getTableHeader().setReorderingAllowed(false);
        javax.swing.table.DefaultTableCellRenderer header=new javax.swing.table.DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t,Object value,boolean selected,boolean focus,int row,int col){
                super.getTableCellRendererComponent(t,value,selected,focus,row,col);setHorizontalAlignment(SwingConstants.LEFT);
                setBorder(new EmptyBorder(0,12,0,8));setFont(new Font(UiColors.FONT,Font.BOLD,9));setForeground(UiColors.MUTED);setBackground(new Color(249,251,254));return this;
            }};
        table.getTableHeader().setDefaultRenderer(header);
        javax.swing.table.DefaultTableCellRenderer body=new javax.swing.table.DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(JTable t,Object value,boolean selected,boolean focus,int row,int col){
                super.getTableCellRendererComponent(t,value,selected,focus,row,col);setBorder(new EmptyBorder(0,12,0,8));
                setBackground(selected?UiColors.PRIMARY_SOFT:(row%2==0?Color.WHITE:new Color(251,252,254)));
                setForeground(UiColors.INK);return this;
            }};
        for(int i=0;i<table.getColumnCount();i++)table.getColumnModel().getColumn(i).setCellRenderer(body);
    }
}