package view;

import Process.*;
import Connection.ConnectionSQL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleUserManager extends JFrame {
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
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel modelU = new DefaultTableModel();
    private Check check = new Check();
    private Role role = new Role();
    private User user = new User();
    private String user1;
    private ImageIcon i;
    private Image m;

    public RoleUserManager(String username) throws SQLException, ClassNotFoundException {
        user1 = username;
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
        lbIdU = new JLabel("USER NAME");
        lbNameU = new JLabel("NAME");
        lbPasswordU = new JLabel("PASSWORD");
        lbIdRoleU = new JLabel("ID ROLE");
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
        tfId.setBounds(80,120,200,35);
        panel.add(tfName);
        tfName.setBounds(80,170,200,35);

        panel.add(lbIdU);
        lbIdU.setBounds(950,110,100,50);
        panel.add(lbNameU);
        lbNameU.setBounds(950,160,100,50);
        panel.add(lbPasswordU);
        lbPasswordU.setBounds(950,210,100,50);
        panel.add(lbIdRoleU);
        lbIdRoleU.setBounds(950,260,100,50);

        panel.add(tfIdU);
        tfIdU.setBounds(1030,120,200,35);
        panel.add(tfNameU);
        tfNameU.setBounds(1030,170,200,35);
        panel.add(tfPasswordU);
        tfPasswordU.setBounds(1030,220,200,35);
        panel.add(tfIdRoleU);
        tfIdRoleU.setBounds(1030,270,200,35);

        // =================== ADD USER ======================
        panel.add(btnAddU);
        btnAddU.setBounds(1300, 120, 130, 35);
        btnAddU.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnInsert.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnAddU.setIcon(new ImageIcon(m));
        btnAddU.setBackground(new Color(42, 115, 196));
        btnAddU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.addUser(tfIdU.getText(),tfNameU.getText(),tfPasswordU.getText(),tfIdRoleU.getText());
                    refreshTableU();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ================== BUTTON UPDATE USER ======================
        panel.add(btnUpdateU);
        btnUpdateU.setBounds(1300, 170, 130, 35);
        btnUpdateU.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnUpdate.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnUpdateU.setIcon(new ImageIcon(m));
        btnUpdateU.setBackground(new Color(42, 115, 196));
        btnUpdateU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.updateUser(tfIdU.getText(),tfNameU.getText(),tfPasswordU.getText(),tfIdRoleU.getText());
                    refreshTableU();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // ================== BUTTON DELETE USER =======================
        panel.add(btnDeleteU);
        btnDeleteU.setBounds(1300, 220, 130, 35);
        btnDeleteU.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnDelete.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnDeleteU.setIcon(new ImageIcon(m));
        btnDeleteU.setBackground(new Color(42, 115, 196));
        btnDeleteU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.deleteUser(tfIdU.getText());
                    refreshTableU();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // =================== BUTTON ADD ROLE =====================
        panel.add(btnAddRole);
        btnAddRole.setBounds(350, 120, 130, 35);
        i = new ImageIcon("src\\icon\\btnInsert.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnAddRole.setIcon(new ImageIcon(m));
        btnAddRole.setBackground(new Color(42, 115, 196));
        btnAddRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    role.addRole(tfId.getText(),tfName.getText());
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ====================== BUTTON UPDATE ROLE ==================
        panel.add(btnUpdateRole);
        btnUpdateRole.setBounds(350, 170, 130, 35);
        btnUpdateRole.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnUpdate.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnUpdateRole.setIcon(new ImageIcon(m));
        btnUpdateRole.setBackground(new Color(42, 115, 196));
        btnUpdateRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    role.updateRole(tfId.getText(),tfName.getText(),false);
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // =================== BUTTON DELETE ROLE ==================
        panel.add(btnDeleteRole);
        btnDeleteRole.setBounds(350, 220, 130, 35);
        btnDeleteRole.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnDelete.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnDeleteRole.setIcon(new ImageIcon(m));
        btnDeleteRole.setBackground(new Color(42, 115, 196));
        btnDeleteRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    role.deleteRole(tfId.getText());
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
        btnExit.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\logout.png");
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

        // =================== TABLE ROLE ============================
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.getTableHeader().setBackground(Color.CYAN);
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 25));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(42, 115, 196));
        table.setRowHeight(30);
        table.setModel(showList(model));

        //================== ROLE MOUSE CLICK ========================
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clickTableRole();
            }
        });

        // ================= JScrollPane ROLE ========================
        panel.add(sp);
        sp.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 400, 700, 500);
        getContentPane().add(scrollPane);

        // =================== TABLE USER ============================
        tableU.setForeground(new Color(0, 0, 0));
        tableU.setBackground(new Color(255, 255, 255));
        tableU.getTableHeader().setBackground(Color.CYAN);
        tableU.setFont(new Font("Serif", Font.PLAIN, 20));
        tableU.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 25));
        tableU.getTableHeader().setForeground(Color.WHITE);
        tableU.getTableHeader().setBackground(new Color(42, 115, 196));
        tableU.setRowHeight(30);
        tableU.setModel(showListU(modelU));
        // ================== USER MOUSE CLICK =====================
        tableU.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clickTableUser();
            }
        });

        // ================= JScrollPane USER ========================
        panel.add(spU);
        spU.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPaneU = new JScrollPane(tableU);
        scrollPaneU.setBounds(870, 400, 700, 500);
        getContentPane().add(scrollPaneU);
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
        String header[] = {"Id","Name"};
        table.setModel(DbUtils.resultSetToTableModel(rs,header));
    }
    public void clickTableRole(){
        int i = table.getSelectedRow();
        model = (DefaultTableModel) table.getModel();
        tfId.setText(model.getValueAt(i,0).toString());
        tfName.setText(model.getValueAt(i,1).toString());
    }
    // =================== LIST USER =======================
    public DefaultTableModel showListU(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) tableU.getModel();
        String header[] = {"User name","Name","Password","Id Role","Status"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM user");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            String row [] = {rs.getString("username"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("id_role"),
                    String.valueOf(rs.getBoolean("status"))};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTableU() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM user");
        ResultSet rs = pstmt.executeQuery();
        String header[] = {"User name","Name","Password","Id Role","Status"};
        tableU.setModel(DbUtils.resultSetToTableModel(rs,header));
    }
    public void clickTableUser(){
        int i = tableU.getSelectedRow();
        modelU = (DefaultTableModel) tableU.getModel();
        tfIdU.setText(modelU.getValueAt(i,0).toString());
        tfNameU.setText(modelU.getValueAt(i,1).toString());
        tfPasswordU.setText(modelU.getValueAt(i,2).toString());
        tfIdRoleU.setText(modelU.getValueAt(i,3).toString());
    }
}
