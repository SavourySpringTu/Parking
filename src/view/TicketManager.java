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
import java.time.LocalDateTime;


public class TicketManager extends JFrame{
    private JPanel panel;
    private JLabel lbId;
    private JLabel lbNumber;
    private JLabel lbTypeTicket;
    private JLabel lbPosition;
    private JLabel lbCustomer;
    private JLabel lbTypeVehicle;
    private JLabel lbImage;
    private ImageIcon imageIcon;
    private JTextField tfId;
    private JTextField tfNumber;
    private JComboBox tfTypeTicket;
    private JTextField tfPosition;
    private JTextField tfCustomer;
    private JComboBox tfTypeVehicle;
    private JButton btnExit;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnExport;
    private JButton choseImage1;
    private JButton choseImage2;
    private JTable table;
    private JScrollPane sp;
    private DefaultTableModel model = new DefaultTableModel();
    private String user1;
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Ticket ticket = new Ticket();
    public TicketManager(String user) throws SQLException, ClassNotFoundException {
        user1 = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1960, 1080);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lbNumber = new JLabel("Number");
        lbId = new JLabel("Id");
        lbPosition = new JLabel("Position");
        tfId = new JTextField();
        tfNumber = new JTextField();
        tfPosition = new JTextField();
        btnExit = new JButton("EXIT");
        lbCustomer = new JLabel("Customer");
        tfCustomer = new JTextField();
        tfCustomer.setEditable(false);
        lbTypeVehicle = new JLabel("Vehicle");
        String TypeVehicle[] ={"Car","Motorbike"};
        tfTypeVehicle = new JComboBox(TypeVehicle);
        lbTypeTicket = new JLabel("Type");
        choseImage1 = new JButton("CHOSE");
        choseImage2 = new JButton("CHOSE");
        tfTypeTicket = new JComboBox();
        loadTypeTicket();

        btnAdd = new JButton("ADD");
        btnUpdate = new JButton("UPDATE");
        btnExport = new JButton("DELETE");
        table = new JTable();
        sp = new JScrollPane();

        panel.add(lbId);
        lbId.setBounds(100,100,100,50);
        tfId.setEditable(false);
        panel.add(lbNumber);
        lbNumber.setBounds(100,150,100,50);
        panel.add(lbPosition);
        lbPosition.setBounds(100,200,100,50);
        panel.add(lbTypeTicket);
        lbTypeTicket.setBounds(470,150,100,50);
        panel.add(lbCustomer);
        lbCustomer.setBounds(470,100,100,50);
        panel.add(lbTypeVehicle);
        lbTypeVehicle.setBounds(470,200,100,50);

        panel.add(tfId);
        tfId.setBounds(180,110,200,35);
        panel.add(tfNumber);
        tfNumber.setBounds(180,160,200,35);
        panel.add(tfPosition);
        tfPosition.setBounds(180,210,200,35);
        panel.add(tfCustomer);
        tfCustomer.setBounds(550,110,200,35);
        panel.add(tfTypeTicket);
        tfTypeTicket.setBounds(550,160,200,35);
        panel.add(tfTypeVehicle);
        tfTypeVehicle.setBounds(550,210,200,35);

        ImageIcon image = new ImageIcon("D:\\Doc\\Java\\IntelliJ Project\\Parking---Swing---MySQL---OpenCV\\src\\Image\\a.png");
        lbImage= new JLabel(image,JLabel.CENTER);
        lbImage.setBounds(800,100,300,250);
        panel.add(lbImage);

        panel.add(btnAdd);
        btnAdd.setBounds(1500, 130, 130, 35);
        btnAdd.setBackground(Color.GREEN);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ticket.addTicket(tfNumber.getText(), (String) tfTypeTicket.getItemAt(tfTypeTicket.getSelectedIndex()),tfPosition.getText(),user1);
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnUpdate);
        btnUpdate.setBounds(1500, 180, 130, 35);
        btnUpdate.setBackground(Color.ORANGE);

        panel.add(btnExport);
        btnExport.setBounds(1500, 230, 130, 35);
        btnExport.setBackground(Color.red);

        panel.add(choseImage1);
        choseImage1.setBounds(885, 375, 130, 35);
        btnExit.setBackground(Color.yellow);

        panel.add(btnExit);
        btnExit.setBounds(10,10,100,50);
        btnExit.setBackground(Color.RED);
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
        table.getTableHeader().setBackground(Color.CYAN);
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
        scrollPaneU.setBounds(100, 450, 1700, 550);
        getContentPane().add(scrollPaneU);
    }

    // =================== LIST =======================
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Number","Type ticket","Time in","Time out","Type vehicle","Position","Employee","Customer"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM ticket");
        ResultSet rs = pstmt.executeQuery();
        System.out.println("while");
        while(rs.next())
        {
            String row [] = {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("number_vehicle"),
                    rs.getString("id_type"),
                    String.valueOf(rs.getDate("timein")),
                    String.valueOf(rs.getDate("timeout")),
                    String.valueOf(rs.getBoolean("type_vehicle")),
                    String.valueOf(rs.getInt("id_position")),
                    rs.getString("id_user"),
                    rs.getString("id_customer")};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTable() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM ticket");
        ResultSet rs = pstmt.executeQuery();
        table.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void clickTableUser(){
        int i = table.getSelectedRow();
        model = (DefaultTableModel) table.getModel();
        tfId.setText(model.getValueAt(i,0).toString());
        tfNumber.setText(model.getValueAt(i,1).toString());
        if(model.getValueAt(i,2).toString().equals("true")){
            tfTypeTicket.setSelectedIndex(1);
        }else{
            tfTypeTicket.setSelectedIndex(0);
        }
        tfNumber.setText(model.getValueAt(i,3).toString());
    }

    // =================== ABOUT ============================
    public void loadTypeTicket() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT id FROM type");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            tfTypeTicket.addItem(rs.getString("id"));
        }
        pstmt.close();
    }
    public void loadImage(){

    }

}
