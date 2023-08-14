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

public class CustomerManager extends JFrame {
    private JPanel panel;
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Customer customer= new Customer();
    private JTable table = new JTable();
    private JScrollPane sp = new JScrollPane();
    private DefaultTableModel model = new DefaultTableModel();

    private JLabel lbId;
    private JLabel lbName;
    private JLabel lbTime;
    private JLabel lbDay;
    private JLabel lbPhone;
    private JLabel lbNumber;
    private JLabel lbType;
    private JLabel lbStatus;
    private JTextField tfId;
    private JTextField tfName;
    private JTextField tfTime;
    private JTextField tfDay;
    private JTextField tfPhone;
    private JTextField tfNumber;
    private JComboBox cbType;
    private JCheckBox cbStatus;
    private JButton btnExit;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private ImageIcon i;
    private Image m;

    public CustomerManager(String user) throws SQLException, ClassNotFoundException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1920, 1080);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lbId = new JLabel("Id");
        lbId.setBounds(90,100,100,50);
        lbId.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbId);
        lbName = new JLabel("Tên");
        lbName.setBounds(90,150,100,50);
        lbName.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbName);
        lbTime = new JLabel("Giờ");
        lbTime.setBounds(90,200,100,50);
        lbTime.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbTime);
        lbDay = new JLabel("Ngày");
        lbDay.setBounds(90,250,100,50);
        lbDay.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbDay);
        lbPhone = new JLabel("SDT");
        lbPhone.setBounds(460,100,100,50);
        lbPhone.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbPhone);
        lbNumber = new JLabel("Số xe");
        lbNumber.setBounds(460,150,100,50);
        lbNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbNumber);
        lbType = new JLabel("Loại xe");
        lbType.setBounds(460,200,100,50);
        lbType.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbType);
        lbStatus = new JLabel("Status");
        lbStatus.setBounds(460,250,100,50);
        lbStatus.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbStatus);

        tfId = new JTextField();
        tfId.setBounds(180,110,200,35);
        tfId.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfId.setEditable(false);
        panel.add(tfId);
        tfName = new JTextField();
        tfName.setBounds(180,160,200,35);
        tfName.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfName);
        tfTime = new JTextField();
        tfTime.setBounds(180,210,200,35);
        tfTime.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfTime.setEditable(false);
        panel.add(tfTime);
        tfDay = new JTextField();
        tfDay.setBounds(180,260,200,35);
        tfDay.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfDay);
        tfPhone = new JTextField();
        tfPhone.setBounds(550,110,200,35);
        tfPhone.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfPhone);
        tfNumber = new JTextField();
        tfNumber.setBounds(550,160,200,35);
        tfNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfNumber);
        String a[]={"Xe máy","Ôtô"};
        cbType = new JComboBox(a);
        cbType.setBounds(550,210,200,35);
        cbType.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(cbType);
        cbStatus = new JCheckBox();
        cbStatus.setBounds(550,250,200,35);
        panel.add(cbStatus);

        btnExit = new JButton();
        panel.add(btnExit);
        btnExit.setBounds(10,10,100,50);
        btnExit.setForeground(Color.WHITE);
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

        btnAdd = new JButton("Insert");
        panel.add(btnAdd);
        btnAdd.setBounds(1100, 130, 130, 35);
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
                    if((cbType.getItemAt(cbType.getSelectedIndex())).equals("Xe máy")==true){
                        typevh = false;
                    }
                    customer.insetCustomer(tfName.getText(),Integer.parseInt(tfDay.getText()),tfPhone.getText(),tfNumber.getText(),typevh);
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

        btnUpdate = new JButton("Update");
        panel.add(btnUpdate);
        btnUpdate.setBounds(1100, 180, 130, 35);
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
                    if((cbType.getItemAt(cbType.getSelectedIndex())).equals("Xe máy")==true){
                        typevh = false;
                    }
                    customer.updateCustomer(Integer.parseInt(tfId.getText()),tfName.getText(),tfPhone.getText(), Integer.parseInt(tfDay.getText()),typevh,tfNumber.getText(),cbStatus.isSelected());
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

        btnDelete = new JButton("Delete");
        panel.add(btnDelete);
        btnDelete.setBounds(1100, 230, 130, 35);
        btnDelete.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnDelete.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnDelete.setIcon(new ImageIcon(m));
        btnDelete.setBackground(new Color(42, 115, 196));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    customer.deleteCustomer(Integer.parseInt(tfId.getText()));
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
        scrollPaneU.setBounds(100, 450, 1700, 550);
        getContentPane().add(scrollPaneU);
    }
    // =================== LIST =======================
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Name","Time","Day","Phone","Number Vehicle","Type","Status"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM customer");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            String vh = "";
            if((rs.getBoolean("type"))==true){
                vh = "Ôtô";
            }else{
                vh = "Xe máy";
            }
            String row [] = {String.valueOf(rs.getInt("id")),
                            rs.getString("name"),
                            String.valueOf(rs.getDate("time")),
                            String.valueOf(rs.getInt("day")),
                            rs.getString("number_phone"),
                            rs.getString("number_vehicle"),
                            vh,
                            String.valueOf(rs.getBoolean("status"))};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTable() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM customer");
        ResultSet rs = pstmt.executeQuery();
        String header[] = {"Id","Name","Time","Day","Phone","Number Vehicle","Type","Status"};
        table.setModel(resultSetToTableModel(rs,header));
    }
    public void clickTableUser(){
        int i = table.getSelectedRow();
        model = (DefaultTableModel) table.getModel();
        tfId.setText(model.getValueAt(i,0).toString());
        tfName.setText(model.getValueAt(i,1).toString());
        tfTime.setText(model.getValueAt(i,2).toString());
        tfDay.setText(model.getValueAt(i,3).toString());
        tfPhone.setText(model.getValueAt(i,4).toString());
        tfNumber.setText(model.getValueAt(i,5).toString());
        String tmp = (String) model.getValueAt(i,6);
        if(tmp.equals("true")==true||model.getValueAt(i,6).equals("Ôtô")==true){
            cbType.setSelectedIndex(1);
        }else{
            cbType.setSelectedIndex(0);
        }
        boolean x = Boolean.parseBoolean(model.getValueAt(i,7).toString());
        cbStatus.setSelected(x);
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
                    if(i==7){
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
