package hospital.vista.atomos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Diálogos modales redondeados para formularios, confirmaciones y avisos. */
public final class UiDialogs {
    private UiDialogs() {}
    public static boolean confirm(Component parent,String title,String description,JComponent content) {
        return show(parent,title,description,content,true,JOptionPane.QUESTION_MESSAGE);
    }
    public static void message(Component parent,String title,String message,int type) {
        show(parent,title,message,null,false,type);
    }
    private static boolean show(Component parent,String title,String description,JComponent content,boolean confirm,int type) {
        Window owner=SwingUtilities.getWindowAncestor(parent);
        JDialog dialog=new JDialog(owner,title,Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);dialog.setBackground(new Color(0,0,0,0));dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel shell=new JPanel(new BorderLayout(0,0)) {
            @Override protected void paintComponent(Graphics graphics) {
                Graphics2D g=(Graphics2D)graphics.create();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(new Color(29,43,73,24));g.fillRoundRect(3,5,getWidth()-6,getHeight()-8,26,26);
                g.setColor(Color.WHITE);g.fillRoundRect(1,1,getWidth()-6,getHeight()-8,26,26);g.dispose();
            }
        };
        shell.setOpaque(false);shell.setBorder(new EmptyBorder(22,24,18,24));
        JPanel header=new JPanel(new BorderLayout(12,0));header.setOpaque(false);
        JLabel marker=new JLabel(type==JOptionPane.ERROR_MESSAGE?"!":type==JOptionPane.WARNING_MESSAGE?"!":type==JOptionPane.INFORMATION_MESSAGE?"✓":"?");
        marker.setHorizontalAlignment(SwingConstants.CENTER);marker.setFont(new Font(UiColors.FONT,Font.BOLD,16));
        marker.setForeground(type==JOptionPane.ERROR_MESSAGE?UiColors.DANGER:type==JOptionPane.WARNING_MESSAGE?UiColors.WARNING:UiColors.PRIMARY);
        marker.setOpaque(true);marker.setBackground(type==JOptionPane.ERROR_MESSAGE?UiColors.DANGER_SOFT:type==JOptionPane.WARNING_MESSAGE?UiColors.WARNING_SOFT:UiColors.PRIMARY_SOFT);
        marker.setPreferredSize(new Dimension(40,40));
        JPanel titleBox=new JPanel();titleBox.setOpaque(false);titleBox.setLayout(new BoxLayout(titleBox,BoxLayout.Y_AXIS));
        JLabel heading=new JLabel(title);heading.setFont(new Font(UiColors.FONT,Font.BOLD,16));heading.setForeground(UiColors.INK);
        JLabel sub=new JLabel(description==null?"":("<html>"+escape(description)+"</html>"));sub.setFont(new Font(UiColors.FONT,Font.PLAIN,11));sub.setForeground(UiColors.MUTED);
        titleBox.add(heading);titleBox.add(Box.createVerticalStrut(5));titleBox.add(sub);
        header.add(marker,BorderLayout.WEST);header.add(titleBox,BorderLayout.CENTER);shell.add(header,BorderLayout.NORTH);
        if(content!=null){content.setOpaque(false);content.setBorder(new EmptyBorder(18,0,4,0));shell.add(content,BorderLayout.CENTER);}
        JPanel footer=new JPanel(new FlowLayout(FlowLayout.RIGHT,9,0));footer.setOpaque(false);footer.setBorder(new EmptyBorder(18,0,0,0));
        final boolean[] accepted={false};
        if(confirm){UiButton cancel=new UiButton("Cancelar",false);cancel.addActionListener(e->dialog.dispose());footer.add(cancel);}
        UiButton action=new UiButton(confirm?"Confirmar":"Entendido",true);action.addActionListener(e->{accepted[0]=true;dialog.dispose();});footer.add(action);
        shell.add(footer,BorderLayout.SOUTH);dialog.setContentPane(shell);
        int width=content==null?410:Math.max(420,Math.min(560,content.getPreferredSize().width+52));
        int height=content==null?170:Math.max(250,Math.min(620,content.getPreferredSize().height+176));
        dialog.setSize(width,height);dialog.setMinimumSize(new Dimension(340,170));dialog.setLocationRelativeTo(parent);dialog.setVisible(true);
        return accepted[0];
    }
    private static String escape(String value){return value.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");}
}
