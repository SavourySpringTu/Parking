package view;

import Process.*;
import Connection.ConnectionSQL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionManager extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JLabel lbId;
    private JLabel lbType;
    private JLabel lbCamera;
    private JTextField tfId;
    private JComboBox tfType;
    private JTextField tfCamera;
    private  JButton btnAdd;
    private  JButton btnUpdate;
    private  JButton btnDelete;
    private JButton btnExit;
    private JTable table;
    private JScrollPane sp;
    private ImageIcon i;
    private Image m;
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private DefaultTableModel model = new DefaultTableModel();
    private Check check = new Check();
    private Positions positions = new Positions();
    private String user1;
    public PositionManager(String user) throws SQLException, ClassNotFoundException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(170,60,1600, 1000);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);
        String [] combobox ={"false","true"};

        lbCamera = new JLabel("Camera");
        lbId = new JLabel("Id");
        lbType = new JLabel("Type");
        tfId = new JTextField();
        tfType = new JComboBox(combobox);
        tfCamera = new JTextField();
        btnAdd = new JButton("ADD");
        btnUpdate = new JButton("UPDATE");
        btnDelete = new JButton("DELETE");
        btnExit = new JButton("EXIT");
        table = new JTable();
        sp = new JScrollPane();

        panel.add(lbId);
        lbId.setBounds(950,110,100,50);
        lbId.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbType);
        lbType.setBounds(950,160,100,50);
        lbType.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbCamera);
        lbCamera.setBounds(950,210,100,50);
        lbCamera.setFont(new Font("Verdana", Font.PLAIN, 18));

        panel.add(tfId);
        tfId.setBounds(1030,120,130,35);
        tfId.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfId.setEnabled(false);
        panel.add(tfCamera);
        tfCamera.setBounds(1030,220,130,35);
        tfCamera.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfType);
        tfType.setBounds(1030,170,130,35);
        tfType.setFont(new Font("Verdana", Font.PLAIN, 18));

        panel.add(btnAdd);
        btnAdd.setBounds(1200, 120, 130, 35);
        btnAdd.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnInsert.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnAdd.setIcon(new ImageIcon(m));
        btnAdd.setBackground(new Color(42, 115, 196));
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    positions.addPosition(Boolean.parseBoolean((String) tfType.getItemAt(tfType.getSelectedIndex())),tfCamera.getText());
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnUpdate);
        btnUpdate.setBounds(1200, 170, 130, 35);
        btnUpdate.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnUpdate.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnUpdate.setIcon(new ImageIcon(m));
        btnUpdate.setBackground(new Color(42, 115, 196));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    positions.updatePosition(tfId.getText(),Boolean.parseBoolean((String) tfType.getItemAt(tfType.getSelectedIndex())),tfCamera.getText());
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnDelete);
        btnDelete.setBounds(1200, 220, 130, 35);
        btnDelete.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnDelete.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnDelete.setIcon(new ImageIcon(m));
        btnDelete.setBackground(new Color(42, 115, 196));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    positions.deletePosition(tfId.getText());
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ================== BUTTON EXIT ======================
        panel.add(btnExit);
        btnExit.setBounds(10,10,100,50);
        i = new ImageIcon("src\\icon\\Logout.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnExit.setIcon(new ImageIcon(m));
        btnExit.setBackground(new Color(42, 115, 196));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager manager = new Manager(user1);
                manager.setVisible(true);
                dispose();
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
        // ================== USER MOUSE CLICK =====================
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clickTableUser();
            }
        });

        // ================= JScrollPane USER ========================
        panel.add(sp);
        sp.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPaneU = new JScrollPane(table);
        scrollPaneU.setBounds(870, 400, 700, 500);
        getContentPane().add(scrollPaneU);
    }
    // =================== LIST =======================
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Type","Camera","Status"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM positions");
        ResultSet rs = pstmt.executeQuery();
        System.out.println("while");
        while(rs.next())
        {
            String row [] = {String.valueOf(rs.getInt("id")), String.valueOf(rs.getBoolean("type")),rs.getString("camera"), String.valueOf(rs.getBoolean("status"))};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTable() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM positions");
        ResultSet rs = pstmt.executeQuery();
        String header[] = {"Id","Type","Camera","Status"};
        table.setModel(DbUtils.resultSetToTableModel(rs,header));
    }
    public void clickTableUser(){
        int i = table.getSelectedRow();
        model = (DefaultTableModel) table.getModel();
        tfId.setText(model.getValueAt(i,0).toString());
        if(model.getValueAt(i,1).toString().equals("true")){
            tfType.setSelectedIndex(1);
        }else{
            tfType.setSelectedIndex(0);
        }
        tfCamera.setText(model.getValueAt(i,2).toString());
    }
}
