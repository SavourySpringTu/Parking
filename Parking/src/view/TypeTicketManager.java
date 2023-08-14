package View;

import Connection.ConnectionSQL;
import Process.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class TypeTicketManager extends JFrame {
    private JPanel panel;
    private JLabel lbId;
    private JLabel lbTimeStart;
    private JLabel lbTimeEnd;
    private JLabel lbType;
    private JLabel lbPrice;
    private JTextField tfId;
    private JComboBox cbType;
    private JTextField tfTimeStart;
    private JTextField tfTimeEnd;
    private JTextField tfPrice;
    private  JButton btnUpdate;
    private JButton btnExit;
    private JTable table;
    private JScrollPane sp;
    private ImageIcon i;
    private Image m;
    private TypeTicket tt = new TypeTicket();
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private DefaultTableModel model = new DefaultTableModel();
    private Check check = new Check();
    public TypeTicketManager(String user) throws SQLException, ClassNotFoundException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(170,60,1100, 850);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);
        String [] combobox ={"Xe máy","Ôtô"};

        lbId = new JLabel("Id");
        lbType = new JLabel("Loại");
        lbTimeStart = new JLabel("Giờ vào");
        lbTimeEnd = new JLabel("Giờ ra");
        lbPrice = new JLabel("Giá");
        tfId = new JTextField();
        cbType = new JComboBox(combobox);
        tfTimeStart = new JTextField();
        tfTimeEnd = new JTextField();
        tfPrice = new JTextField();
        btnUpdate = new JButton("SỬA");
        btnExit = new JButton("THOÁT");
        table = new JTable();
        sp = new JScrollPane();

        panel.add(lbId);
        lbId.setBounds(150,110,100,50);
        lbId.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbTimeStart);
        lbTimeStart.setBounds(150,160,100,50);
        lbTimeStart.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbTimeEnd);
        lbTimeEnd.setBounds(150,210,100,50);
        lbTimeEnd.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbType);
        lbType.setBounds(500,110,100,50);
        lbType.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbPrice);
        lbPrice.setBounds(500,160,100,50);
        lbPrice.setFont(new Font("Verdana", Font.PLAIN, 18));

        panel.add(tfId);
        tfId.setBounds(250,120,200,35);
        tfId.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfId.setEditable(false);
        panel.add(tfTimeStart);
        tfTimeStart.setBounds(250,170,200,35);
        tfTimeStart.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfTimeStart.setEditable(false);
        panel.add(tfTimeEnd);
        tfTimeEnd.setBounds(250,220,200,35);
        tfTimeEnd.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfTimeEnd.setEditable(false);
        panel.add(cbType);
        cbType.setBounds(550,120,200,35);
        cbType.setFont(new Font("Verdana", Font.PLAIN, 18));
        cbType.setEditable(false);
        panel.add(tfPrice);
        tfPrice.setBounds(550,170,200,35);
        tfPrice.setFont(new Font("Verdana", Font.PLAIN, 18));

        panel.add(btnExit);
        btnExit.setBounds(10,10,200,50);
        i = new ImageIcon("src\\icon\\Logout.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnExit.setIcon(new ImageIcon(m));
        btnExit.setBackground(new Color(42, 115, 196));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager manager = new Manager(user);
                manager.setVisible(true);
                dispose();
            }
        });

        panel.add(btnUpdate);
        btnUpdate.setBounds(800,150,200,50);
        btnUpdate.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnUpdate.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnUpdate.setIcon(new ImageIcon(m));
        btnUpdate.setBackground(new Color(42, 115, 196));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(check.checkInt(tfPrice.getText())==false){
                        JOptionPane.showMessageDialog(null, "Giá phải là số dương!");
                        return;
                    }
                    tt.updatePrice(tfId.getText(), Integer.parseInt(tfPrice.getText()));
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // =================== TABLE ============================
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 25));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(42, 115, 196));
        table.setRowHeight(30);
        table.setModel(showList(model));
        // ================== MOUSE CLICK =====================
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clickTableUser();
            }
        });

        // ================= JScrollPane ========================
        panel.add(sp);
        sp.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPaneU = new JScrollPane(table);
        scrollPaneU.setBounds(110, 400, 900, 350);
        getContentPane().add(scrollPaneU);
    }
    // =================== LIST =======================
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Loại xe","Giờ vào","Giờ ra","Giá"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM type");
        ResultSet rs = pstmt.executeQuery();
        System.out.println("while");
        while(rs.next())
        {
            String vh = "";
            if((rs.getBoolean("type"))==true){
                vh = "Ôtô";
            }else{
                vh = "Xe máy";
            }
            String row [] = {String.valueOf(rs.getString("id")),
                    vh,
                    String.valueOf(rs.getObject("timestart")),
                    String.valueOf(rs.getObject("timeend")),
                    String.valueOf(rs.getInt("price"))};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTable() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM type");
        ResultSet rs = pstmt.executeQuery();
        String header[] = {"Id","Loại xe","Giờ vào","Giờ ra","Giá"};
        table.setModel(resultSetToTableModel(rs,header));
    }
    public void clickTableUser(){
        int i = table.getSelectedRow();
        model = (DefaultTableModel) table.getModel();
        tfId.setText(model.getValueAt(i,0).toString());
        if(model.getValueAt(i,1).equals("Ôtô")==true || model.getValueAt(i,1).equals(true)==true){
            cbType.setSelectedIndex(1);
        }else{
            cbType.setSelectedIndex(0);
        }
        tfTimeStart.setText(model.getValueAt(i,2).toString());
        tfTimeEnd.setText(model.getValueAt(i,3).toString());
        tfPrice.setText(model.getValueAt(i,4).toString());
    }
    public static TableModel resultSetToTableModel(ResultSet rs, String a[]) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(a[column]);
            }
            Vector rows = new Vector();
            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    if(i==2){
                        boolean tmp = (boolean) rs.getObject(i);
                        if(tmp==true){
                            newRow.addElement("Ôtô");
                        }else{
                            newRow.addElement("Xe máy");
                        }
                    }else{
                        newRow.addElement(rs.getObject(i));
                    }
                }
                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
