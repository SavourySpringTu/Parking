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
    private JButton btnAddRole;
    private JButton btnUpdateRole;
    private JButton btnDeleteRole;
    private JLabel lbIdU;
    private JLabel lbNameU;
    private JLabel lbPasswordU;
    private JLabel lbIdRoleU;
    private JTextField tfIdU;
    private JTextField tfNameU;
    private JTextField tfPasswordU;
    private JTextField tfIdRoleU;
    private JButton btnAddU;
    private JButton btnUpdateU;
    private JButton btnDeleteU;
    private JTable tableU;
    private JScrollPane spU;
    private String id;
    private String name;
    private String idU;
    private String nameU;
    private String passwordU;
    private String idRoleU;
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel modelU = new DefaultTableModel();
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
        btnAddRole = new JButton("ADD");
        btnUpdateRole = new JButton("UPDATE");
        btnDeleteRole = new JButton("DELETE");
        lbIdU = new JLabel("Id");
        lbNameU = new JLabel("Name");
        lbPasswordU = new JLabel("Password");
        lbIdRoleU = new JLabel("Id Role");
        tfIdRoleU = new JTextField();
        tfNameU = new JTextField();
        tfPasswordU = new JTextField();
        tfIdU = new JTextField();
        btnAddU = new JButton("ADD");
        btnUpdateU = new JButton("UPDATE");
        btnDeleteU = new JButton("DELETE");
        tableU = new JTable();
        spU = new JScrollPane();

        panel.add(lbId);
        lbId.setBounds(30,110,100,50);
        panel.add(lbName);
        lbName.setBounds(30,160,100,50);
        panel.add(tfId);
        tfId.setBounds(80,120,100,35);
        panel.add(tfName);
        tfName.setBounds(80,170,100,35);

        panel.add(lbIdU);
        lbIdU.setBounds(950,110,100,50);
        panel.add(lbNameU);
        lbNameU.setBounds(950,160,100,50);
        panel.add(lbPasswordU);
        lbPasswordU.setBounds(950,210,100,50);
        panel.add(lbIdRoleU);
        lbIdRoleU.setBounds(950,260,100,50);

        panel.add(tfIdU);
        tfIdU.setBounds(1030,120,130,35);
        panel.add(tfNameU);
        tfNameU.setBounds(1030,170,130,35);
        panel.add(tfPasswordU);
        tfPasswordU.setBounds(1030,220,130,35);
        panel.add(tfIdRoleU);
        tfIdRoleU.setBounds(1030,270,130,35);

        // =================== ADD USER ======================
        panel.add(btnAddU);
        btnAddU.setBounds(1200, 120, 130, 35);
        btnAddU.setBackground(Color.green);
        btnAddU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idU = tfIdU.getText();
                nameU = tfNameU.getText();
                passwordU = tfPasswordU.getText();
                idRoleU = tfIdRoleU.getText();
                try {
                    addUser(idU,nameU,passwordU,idRoleU);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ================== BUTTON UPDATE USER ======================
        panel.add(btnUpdateU);
        btnUpdateU.setBounds(1200, 170, 130, 35);
        btnUpdateU.setBackground(Color.ORANGE);

        // ================== BUTTON DELETE USER =======================
        panel.add(btnDeleteU);
        btnDeleteU.setBounds(1200, 220, 130, 35);
        btnDeleteU.setBackground(Color.RED);

        // =================== BUTTON ADD ROLE =====================
        panel.add(btnAddRole);
        btnAddRole.setBounds(250, 120, 130, 35);
        btnAddRole.setBackground(Color.green);
        btnAddRole.addActionListener(new ActionListener() {
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

        // ====================== BUTTON UPDATE ROLE ==================
        panel.add(btnUpdateRole);
        btnUpdateRole.setBounds(250, 170, 130, 35);
        btnUpdateRole.setBackground(Color.orange);
        btnUpdateRole.addActionListener(new ActionListener() {
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

        // =================== BUTTON DELETE ROLE ==================
        panel.add(btnDeleteRole);
        btnDeleteRole.setBounds(250, 220, 130, 35);
        btnDeleteRole.setBackground(Color.RED);
        btnDeleteRole.addActionListener(new ActionListener() {
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

        // =================== TABLE ROLE ============================
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 25));
        table.setRowHeight(30);
        table.setModel(showList(model));

        // ================= JScrollPane ROLE ========================
        panel.add(sp);
        sp.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 400, 700, 500);
        getContentPane().add(scrollPane);

        // =================== TABLE USER ============================
        tableU.setForeground(new Color(0, 0, 0));
        tableU.setBackground(new Color(255, 255, 255));
        tableU.setFont(new Font("Serif", Font.PLAIN, 20));
        tableU.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 25));
        tableU.setRowHeight(30);
        tableU.setModel(showListU(modelU));

        // ================= JScrollPane USER ========================
        panel.add(spU);
        spU.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPaneU = new JScrollPane(tableU);
        scrollPaneU.setBounds(870, 400, 700, 500);
        getContentPane().add(scrollPaneU);
    }

    // ======================= ADD ROLE ========================
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

    // ======================= UPDATE ROLE =====================
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

    // ======================= DELETE ROLE =====================
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

    // ======================== LIST ROLE ======================
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

    // =================== LIST USER =======================
    public DefaultTableModel showListU(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) tableU.getModel();
        String header[] = {"Id","Name","Password","Id Role"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM user");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            String row [] = {rs.getString("id"),rs.getString("name"),rs.getString("password"),rs.getString("id_role")};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTableU() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM user");
        ResultSet rs = pstmt.executeQuery();
        tableU.setModel(DbUtils.resultSetToTableModel(rs));
    }

    // ======================= ADD USER =======================
    public void addUser(String id,String name,String password,String role) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true){
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO user(id,name,password,id_role) VALUES (?,?,?,?)");
            pstmt.setString(1,id);
            pstmt.setString(2,name);
            pstmt.setString(3,password);
            pstmt.setString(4,role);
            pstmt.executeUpdate();
            pstmt.close();
            refreshTableU();
        }
        else{
            // notice
        }

    }
}
