package view;

import About.Check;
import About.DbUtils;
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
import java.sql.Statement;

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
    private ConnectionSQL connectionSQL;
    private DefaultTableModel model = new DefaultTableModel();
    private Check check = new Check();
    public PositionManager() throws SQLException, ClassNotFoundException {
        connectionSQL = new ConnectionSQL();

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
        panel.add(lbType);
        lbType.setBounds(950,160,100,50);
        panel.add(lbCamera);
        lbCamera.setBounds(950,210,100,50);

        panel.add(tfId);
        tfId.setBounds(1030,120,130,35);
        tfId.setEditable(false);
        panel.add(tfCamera);
        tfCamera.setBounds(1030,220,130,35);
        panel.add(tfType);
        tfType.setBounds(1030,170,130,35);

        panel.add(btnAdd);
        btnAdd.setBounds(1200, 120, 130, 35);
        btnAdd.setBackground(Color.green);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addPosition();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnUpdate);
        btnUpdate.setBounds(1200, 170, 130, 35);
        btnUpdate.setBackground(Color.ORANGE);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updatePosition();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnDelete);
        btnDelete.setBounds(1200, 220, 130, 35);
        btnDelete.setBackground(Color.RED);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deletePosition();
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
        btnExit.setBackground(Color.RED);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager manager = new Manager();
                manager.setVisible(true);
                dispose();
            }
        });
        // =================== TABLE ============================
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 25));
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
        table.setModel(DbUtils.resultSetToTableModel(rs));
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
    // =========================== ADD ============================
    public void addPosition() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT positions(type,camera,status) VALUES (?,?,?)");
        pstmt.setBoolean(1, Boolean.parseBoolean((String) tfType.getItemAt(tfType.getSelectedIndex())));
        pstmt.setString(2, tfCamera.getText());
        Boolean status = false;
        pstmt.setBoolean(3, status);
        pstmt.executeUpdate();
        pstmt.close();
        refreshTable();
    }
    // ======================== UPDATE ===============================
    public void updatePosition() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE positions SET camera=?,type=? WHERE id=?");
        pstmt.setBoolean(2, Boolean.parseBoolean((String) tfType.getItemAt(tfType.getSelectedIndex())));
        pstmt.setString(1, tfCamera.getText());
        pstmt.setInt(3, Integer.parseInt(tfId.getText()));
        pstmt.executeUpdate();
        pstmt.close();
        refreshTable();
    }
    // ========================== DELETE =============================
    public void deletePosition() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM positions WHERE id=?");
        pstmt.setInt(1, Integer.parseInt(tfId.getText()));
        pstmt.executeUpdate();
        pstmt.close();
        refreshTable();
    }
}
