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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoleManager extends JFrame {
    private JFrame jFrame;
    private JPanel panel;
    private JTable table;
    private JButton btnExit;
    private JLabel lbId;
    private JLabel lbName;
    private JTextField tfId;
    private JTextField tfName;
    private ConnectionSQL connectionSQL;
    private JScrollPane sp;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private String id;
    private String name;
    private DefaultTableModel model = new DefaultTableModel();
    private Check check = new Check();
    public RoleManager() throws SQLException, ClassNotFoundException {
        connectionSQL = new ConnectionSQL();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(170,60,1600, 1000);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        table = new JTable();
        sp = new JScrollPane(table);
        btnExit = new JButton("EXIT");
        lbId = new JLabel("ID");
        lbName = new JLabel("NAME");
        tfId = new JTextField();
        tfName = new JTextField();
        btnAdd = new JButton("ADD");
        btnUpdate = new JButton("UPDATE");
        btnDelete = new JButton("DELETE");

        panel.add(lbId);
        lbId.setBounds(1200,100,100,50);
        lbId.setFont(new Font("Serif", Font.PLAIN, 20));

        panel.add(tfId);
        tfId.setBounds(1200,150,250,35);

        panel.add(lbName);
        lbName.setBounds(1200,220,100,50);
        lbName.setFont(new Font("Serif", Font.PLAIN, 20));

        panel.add(tfName);
        tfName.setBounds(1200,270,250,35);

        // =================== BUTTON ADD ====================
        panel.add(btnAdd);
        btnAdd.setBounds(1200, 350, 130, 35);
        btnAdd.setBackground(Color.green);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = tfId.getText();
                name = tfName.getText();
                try {
                    addRole(id,name);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ====================== BUTTON UPDATE ==================
        panel.add(btnUpdate);
        btnUpdate.setBounds(1200, 400, 130, 35);
        btnUpdate.setBackground(Color.orange);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = tfId.getText();
                name = tfName.getText();
                try {
                    updateRole(id,name);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // =================== BUTTON DELETE ==================
        panel.add(btnDelete);
        btnDelete.setBounds(1200, 450, 130, 35);
        btnDelete.setBackground(Color.RED);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = tfId.getText();
                try {
                    deleteRole(id);
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
        table.setCellSelectionEnabled(true);
        table.setFillsViewportHeight(true);
        table.setSurrendersFocusOnKeystroke(true);
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 25));
        table.setRowHeight(30);
        table.setModel(showList(model));

        // ================= JScrollPane ========================
        panel.add(sp);
        sp.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 110, 700, 800);
        getContentPane().add(scrollPane);
    }

    // ======================= Add ========================
    public void addRole(String id,String name) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true){
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO role(id,name) VALUES (?,?)");
            pstmt.setString(1,id);
            pstmt.setString(2,name);
            pstmt.executeUpdate();
            pstmt.close();
            refreshTable();
        }
        else{
            // notice
        }

    }

    // ======================= Update =====================
    public void updateRole(String id, String name) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true) {
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE role SET name=? WHERE id=?");
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            pstmt.close();
            refreshTable();
        } else{
            // notice
        }
    }

    // ======================= Delete =====================
    public void deleteRole(String id) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true) {
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM role WHERE id=?");
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            refreshTable();
        }else{
            // notice
        }
    }

    // ======================== List ======================
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Name"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM role");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            String row [] = {rs.getString("id"),rs.getString("name")};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTable() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM role");
        ResultSet rs = pstmt.executeQuery();
        table.setModel(DbUtils.resultSetToTableModel(rs));
    }
}
