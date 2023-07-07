package view;

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
    DefaultTableModel model = new DefaultTableModel();
    public RoleManager() throws SQLException, ClassNotFoundException {
        connectionSQL = new ConnectionSQL();
        Statement stmt = connectionSQL.ConnectionSQL().createStatement();

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

        panel.add(btnAdd);
        btnAdd.setBounds(1200, 350, 130, 35);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    id = tfId.getText();
                    name = tfName.getText();
                    PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO role(id,name) VALUES (?,?)");
                    pstmt.setString(1,id);
                    pstmt.setString(2,name);
                    pstmt.executeUpdate();
                    table.setModel(updateTable(model));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnUpdate);
        btnUpdate.setBounds(1200, 400, 130, 35);

        panel.add(btnDelete);
        btnDelete.setBounds(1200, 450, 130, 35);

        stmt.close();

        // ================== BUTTON EXIT ======================
        panel.add(btnExit);
        btnExit.setBounds(10,10,100,50);
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
        table.setRowHeight(30);
        table.setModel(showList(model));

        // ================= JScrollPane ========================
        panel.add(sp);
        sp.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 110, 700, 800);
        getContentPane().add(scrollPane);
    }
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Name"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM role");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            Object objList[] = {rs.getString("id"),rs.getString("name")};
            model.addRow(objList);
        }
        pstmt.close();
        return model;
    }
    public DefaultTableModel updateTable(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        this.model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Name"};
        this.model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM role");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            String a[]={rs.getString("id"),rs.getString("name")};
            if(rs.next()==false){
                model.addRow(a);
            }
        }
        return this.model;
    }
}
