package View;

import Process.*;
import Connection.ConnectionSQL;

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

public class PositionManager extends JFrame {
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
    private Positions positions = new Positions();
    public PositionManager(String user) throws SQLException, ClassNotFoundException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(170,60,900, 1000);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);
        String [] combobox ={"Xe máy","Ôtô"};

        lbId = new JLabel("Id");
        lbType = new JLabel("Loại xe");
        tfId = new JTextField();
        tfType = new JComboBox(combobox);
        lbCamera = new JLabel("Camera");
        tfCamera = new JTextField();
        btnAdd = new JButton("THÊM");
        btnUpdate = new JButton("SỬA");
        btnDelete = new JButton("XÓA");
        btnExit = new JButton("EXIT");
        table = new JTable();
        sp = new JScrollPane();

        panel.add(lbId);
        lbId.setBounds(250,110,100,50);
        lbId.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbType);
        lbType.setBounds(250,160,100,50);
        lbType.setFont(new Font("Verdana", Font.PLAIN, 18));
        lbCamera.setBounds(250,210,100,50);
        lbCamera.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbCamera);

        panel.add(tfId);
        tfId.setBounds(330,120,130,35);
        tfId.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfId.setEnabled(false);
        panel.add(tfCamera);
        tfCamera.setBounds(330,220,130,35);
        tfCamera.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfType);
        tfType.setBounds(330,170,130,35);
        tfType.setFont(new Font("Verdana", Font.PLAIN, 18));

        panel.add(btnAdd);
        btnAdd.setBounds(500, 120, 130, 35);
        btnAdd.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnInsert.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnAdd.setIcon(new ImageIcon(m));
        btnAdd.setBackground(new Color(42, 115, 196));
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean typevh =true;
                    if((tfType.getItemAt(tfType.getSelectedIndex())).equals("Xe máy")==true){
                        typevh = false;
                    }
                    positions.addPosition(typevh,tfCamera.getText());
                    refreshTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi!");
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi!");
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnUpdate);
        btnUpdate.setBounds(500, 170, 130, 35);
        btnUpdate.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnUpdate.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnUpdate.setIcon(new ImageIcon(m));
        btnUpdate.setBackground(new Color(42, 115, 196));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean typevh =true;
                    if((tfType.getItemAt(tfType.getSelectedIndex())).equals("Xe máy")==true){
                        typevh = false;
                    }
                    positions.updatePosition(tfId.getText(),typevh,tfCamera.getText());
                    refreshTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi!");
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi!");
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnDelete);
        btnDelete.setBounds(500, 220, 130, 35);
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
                    JOptionPane.showMessageDialog(null, "Lỗi!");
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi!");
                    throw new RuntimeException(ex);
                }
            }
        });

        // ================== BUTTON EXIT ======================
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
        scrollPaneU.setBounds(110, 400, 700, 500);
        getContentPane().add(scrollPaneU);
    }
    // =================== LIST =======================
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Loại xe","Camera","Trạng thái"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM positions");
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
            String row [] = {String.valueOf(rs.getInt("id")),
                    vh,
                    rs.getString("camera"),
                    String.valueOf(rs.getBoolean("status"))};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTable() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM positions");
        ResultSet rs = pstmt.executeQuery();
        String header[] = {"Id","Loại xe","Camera","Trạng thái"};
        table.setModel(resultSetToTableModel(rs,header));
    }
    public void clickTableUser(){
        int i = table.getSelectedRow();
        model = (DefaultTableModel) table.getModel();
        tfId.setText(model.getValueAt(i,0).toString());
        if(model.getValueAt(i,1).equals("Ôtô")==true || model.getValueAt(i,1).equals(true)==true){
            tfType.setSelectedIndex(1);
        }else{
            tfType.setSelectedIndex(0);
        }
        tfCamera.setText(model.getValueAt(i,2).toString());
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
