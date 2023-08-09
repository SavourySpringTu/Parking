package view;

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

public class WarehouseManager extends JFrame{
    private JPanel panel;
    private JLabel lbId;
    private JLabel lbTimeIn;
    private JLabel lbTimeOut;
    private JLabel lbIdTicket;
    private JLabel lbStatus;
    private JLabel lbImage;
    private JLabel lbImage1;
    private JTextField tfId;
    private JTextField tfIdTicket;
    private JTextField tfTimeIn;
    private JTextField tfTimeOut;
    private JComboBox jcStatus;
    private JButton btnExport;
    private JButton btnExit;
    private JTable table;
    private JScrollPane sp;

    private String path1;
    private String path2;
    private DefaultTableModel model = new DefaultTableModel();
    private ConnectionSQL connectionSQL;
    private Images images = new Images();
    private Warehouse warehouse = new Warehouse();
    private ImageIcon i;
    private Image m;
    public WarehouseManager(String user) throws SQLException, ClassNotFoundException {
        connectionSQL = new ConnectionSQL();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1960, 1080);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lbId = new JLabel("Id");
        lbId.setBounds(90,100,100,50);
        lbId.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbId);
        lbTimeIn = new JLabel("Giờ vào");
        lbTimeIn.setBounds(90,150,100,50);
        lbTimeIn.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbTimeIn);
        lbTimeOut = new JLabel("Giờ ra");
        lbTimeOut.setBounds(90,200,100,50);
        lbTimeOut.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbTimeOut);
        lbIdTicket = new JLabel("Id vé");
        lbIdTicket.setBounds(440,100,100,50);
        lbIdTicket.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbIdTicket);
        lbStatus = new JLabel("Trạng thái");
        lbStatus.setBounds(440,150,100,50);
        lbStatus.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbStatus);

        tfId = new JTextField();
        tfId.setBounds(180,110,200,35);
        tfId.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfId);
        tfTimeIn = new JTextField();
        tfTimeIn.setBounds(180,160,200,35);
        tfTimeIn.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfTimeIn);
        tfTimeOut = new JTextField();
        tfTimeOut.setBounds(180,210,200,35);
        tfTimeOut.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfTimeOut);
        tfIdTicket = new JTextField();
        tfIdTicket.setBounds(550,110,200,35);
        tfIdTicket.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfIdTicket);
        String TypeVehicle[] ={"Ôtô","Xe máy"};
        jcStatus = new JComboBox(TypeVehicle);
        jcStatus.setBounds(550,160,200,35);
        jcStatus.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(jcStatus);

        lbImage= new JLabel();
        lbImage.setBounds(800,100,300,250);
        lbImage.setBackground(Color.BLACK);
        panel.add(lbImage);
        JPanel im = new JPanel();
        im.setBounds(800,100,300,250);
        im.setBackground(Color.DARK_GRAY);
        panel.add(im);

        lbImage1 = new JLabel();
        lbImage1.setBounds(1150,100,300,250);
        panel.add(lbImage1);
        JPanel im1 = new JPanel();
        im1.setBounds(1150,100,300,250);
        im1.setBackground(Color.DARK_GRAY);
        panel.add(im1);

        btnExport=new JButton("XUẤT");
        panel.add(btnExport);
        btnExport.setBounds(1500, 200, 130, 35);
        btnExport.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnExport.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnExport.setIcon(new ImageIcon(m));
        btnExport.setBackground(new Color(42, 115, 196));
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    warehouse.exportWarehouse(Integer.parseInt(tfId.getText()));
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

        btnExit = new JButton("THOÁT");
        btnExit.setForeground(Color.WHITE);
        btnExit.setBounds(10,10,200,50);
        i = new ImageIcon("src\\icon\\logout.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnExit.setIcon(new ImageIcon(m));
        btnExit.setBackground(new Color(42, 115, 196));
        panel.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager form = new Manager(user);
                form.setVisible(true);
                dispose();
            }
        });

        // =================== TABLE ============================
        table = new JTable();
        panel.add(table);
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
                try {
                    clickTableUser();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ================= JScrollPane ========================
        sp = new JScrollPane();
        panel.add(sp);
        sp.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPaneU = new JScrollPane(table);
        scrollPaneU.setBounds(100, 450, 1700, 550);
        getContentPane().add(scrollPaneU);
    }
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Giờ vào","Giờ ra","Id vé","Trạng thái"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM warehouse");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            String vh = "";
            if((rs.getBoolean("status"))==true){
                vh = "True";
            }else{
                vh = "False";
            }
            String row [] = {
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getDate("timein")),
                    String.valueOf(rs.getDate("timeout")),
                    String.valueOf(rs.getString("id_ticket")),
                    vh};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTable() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM warehouse");
        ResultSet rs = pstmt.executeQuery();
        String header[] = {"Id","Giờ vào","Giờ ra","Id vé","Trạng thái"};
        table.setModel(resultSetToTableModel(rs,header));
    }
    public void clickTableUser() throws SQLException, ClassNotFoundException {
        int i = table.getSelectedRow();
        model = (DefaultTableModel) table.getModel();
        tfId.setText(model.getValueAt(i,0).toString());
        tfTimeIn.setText(model.getValueAt(i,1).toString());
        tfTimeOut.setText(model.getValueAt(i,2).toString());
        tfIdTicket.setText(model.getValueAt(i,3).toString());
        if(model.getValueAt(i,4).equals("True")==true){
            jcStatus.setSelectedIndex(0);
        }else{
            jcStatus.setSelectedIndex(1);
        }
        String a[] = images.findPathImagebyIdTicket(Integer.parseInt(model.getValueAt(i,3).toString()));
        ImageIcon ii = new ImageIcon(a[0]);
        Image im = ii.getImage().getScaledInstance(lbImage.getWidth(), lbImage.getHeight(), Image.SCALE_SMOOTH);
        lbImage.setIcon(new ImageIcon(im));
        path1=a[0];

        ImageIcon iii = new ImageIcon(a[1]);
        Image imm = iii.getImage().getScaledInstance(lbImage1.getWidth(), lbImage1.getHeight(), Image.SCALE_SMOOTH);
        lbImage1.setIcon(new ImageIcon(imm));
        path2=a[1];
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
                    newRow.addElement(rs.getObject(i));
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
