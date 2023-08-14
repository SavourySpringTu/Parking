package View;

import Process.*;
import Connection.ConnectionSQL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;


public class TicketManager extends JFrame{
    private JPanel panel;
    private JLabel lbId;
    private JLabel lbNumber;
    private JLabel lbTypeTicket;
    private JLabel lbPosition;
    private JLabel lbCustomer;
    private JLabel lbTypeVehicle;
    private JLabel lbImage;
    private JLabel lbImage1;
    private JTextField tfId;
    private JTextField tfNumber;
    private JTextField tfTypeTicket;
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
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Ticket ticket = new Ticket();
    private Images image = new Images();
    private String path1;
    private String path2;
    private ImageIcon i;
    private Image m;
    public TicketManager(String user) throws SQLException, ClassNotFoundException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1960, 1080);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lbNumber = new JLabel("Số xe");
        lbId = new JLabel("Id");
        lbPosition = new JLabel("Vị trí");
        tfId = new JTextField();
        tfNumber = new JTextField();
        tfPosition = new JTextField();
        btnExit = new JButton("THOÁT");
        lbCustomer = new JLabel("Khách");
        tfCustomer = new JTextField();
        tfCustomer.setEditable(false);
        lbTypeVehicle = new JLabel("Loại xe");
        String TypeVehicle[] ={"Xe máy","Ôtô"};
        tfTypeVehicle = new JComboBox(TypeVehicle);
        lbTypeTicket = new JLabel("Loại");
        choseImage1 = new JButton("THÊM");
        choseImage2 = new JButton("THÊM");
        tfTypeTicket = new JTextField();

        btnAdd = new JButton("THÊM");
        btnUpdate = new JButton("SỬA");
        btnExport = new JButton("XÓA");
        table = new JTable();
        sp = new JScrollPane();

        panel.add(lbId);
        lbId.setBounds(100,100,100,50);
        lbId.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfId.setEnabled(false);
        panel.add(lbNumber);
        lbNumber.setBounds(100,150,100,50);
        lbNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbPosition);
        lbPosition.setBounds(100,200,100,50);
        lbPosition.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbTypeTicket);
        lbTypeTicket.setBounds(460,150,100,50);
        lbTypeTicket.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbCustomer);
        lbCustomer.setBounds(460,100,100,50);
        lbCustomer.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbTypeVehicle);
        lbTypeVehicle.setBounds(460,200,100,50);
        lbTypeVehicle.setFont(new Font("Verdana", Font.PLAIN, 18));

        panel.add(tfId);
        tfId.setBounds(180,110,200,35);
        tfId.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfNumber);
        tfNumber.setBounds(180,160,200,35);
        tfNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfPosition);
        tfPosition.setBounds(180,210,200,35);
        tfPosition.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfCustomer);
        tfCustomer.setBounds(550,110,200,35);
        tfCustomer.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfTypeTicket);
        tfTypeTicket.setBounds(550,160,200,35);
        tfTypeTicket.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfTypeVehicle.setBounds(550,210,200,35);
        tfTypeVehicle.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfTypeVehicle);

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

        panel.add(btnAdd);
        btnAdd.setBounds(1500, 130, 130, 35);
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
                    if((tfTypeVehicle.getItemAt(tfTypeVehicle.getSelectedIndex())).equals("Xe máy")==true){
                        typevh = false;
                    }
                    ticket.addTicket(tfNumber.getText(), tfTypeTicket.getText(),typevh, Integer.parseInt(tfPosition.getText()),user,path1,path2);
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
        btnUpdate.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnUpdate.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnUpdate.setIcon(new ImageIcon(m));
        btnUpdate.setBackground(new Color(42, 115, 196));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ticket.updateTicket(Integer.parseInt(tfId.getText()),tfNumber.getText(),tfTypeTicket.getText(), Integer.parseInt(tfPosition.getText()),path1,path2);
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnExport);
        btnExport.setBounds(1500, 230, 130, 35);
        btnExport.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnDelete.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnExport.setIcon(new ImageIcon(m));
        btnExport.setBackground(new Color(42, 115, 196));
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ticket.deleteTicket(Integer.parseInt(tfId.getText()));
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(choseImage1);
        choseImage1.setBounds(885, 375, 130, 35);
        choseImage1.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnBrowser.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        choseImage1.setIcon(new ImageIcon(m));
        choseImage1.setBackground(new Color(42, 115, 196));
        choseImage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                jButtonBrowseImageActionPerformed(evt,1);
            }
        });

        panel.add(choseImage2);
        choseImage2.setBounds(1235, 375, 130, 35);
        choseImage2.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnBrowser.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        choseImage2.setIcon(new ImageIcon(m));
        choseImage2.setBackground(new Color(42, 115, 196));
        choseImage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseImageActionPerformed(evt,2);
            }
        });


        panel.add(btnExit);
        btnExit.setBounds(10,10,200,50);
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
        panel.add(sp);
        sp.setFont(new Font("Serif", Font.PLAIN, 20));
        JScrollPane scrollPaneU = new JScrollPane(table);
        scrollPaneU.setBounds(100, 450, 1700, 550);
        getContentPane().add(scrollPaneU);
    }

    // =================== LIST =======================
    public DefaultTableModel showList(DefaultTableModel model) throws SQLException, ClassNotFoundException {
        model = (DefaultTableModel) table.getModel();
        String header[] = {"Id","Giờ vào","Giờ ra","Số xe","Loại xe","Loại vé","Vị trí","Nhân viên","Kho","Khách"};
        model.setColumnIdentifiers(header);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM ticket");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            String vh = "";
            if((rs.getBoolean("type_vehicle"))==true){
                vh = "Ôtô";
            }else{
                vh = "Xe máy";
            }
            String row [] = {
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getObject("timein")),
                    String.valueOf(rs.getObject("timeout")),
                    rs.getString("number_vehicle"),
                    vh,
                    rs.getString("id_type"),
                    String.valueOf(rs.getInt("id_position")),
                    String.valueOf(rs.getString("id_user")),
                    String.valueOf(rs.getString("id_warehouse")),
                    String.valueOf(rs.getString("id_customer"))};
            model.addRow(row);
        }
        pstmt.close();
        return model;
    }
    public void refreshTable() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM ticket");
        ResultSet rs = pstmt.executeQuery();
        String header[] = {"Id","Giờ vào","Giờ ra","Số xe","Loại xe","Loại vé","Vị trí","Nhân viên","Kho","Khách"};
        table.setModel(resultSetToTableModel(rs,header));
    }
    public void clickTableUser() throws SQLException, ClassNotFoundException {
        int i = table.getSelectedRow();
        model = (DefaultTableModel) table.getModel();
        tfId.setText(model.getValueAt(i,0).toString());
        tfNumber.setText(model.getValueAt(i,3).toString());
        if(model.getValueAt(i,4).equals("Ôtô")==true || model.getValueAt(i,4).equals(true)==true){
            tfTypeVehicle.setSelectedIndex(1);
        }else{
            tfTypeVehicle.setSelectedIndex(0);
        }
        tfTypeTicket.setText(model.getValueAt(i,5).toString());
        tfPosition.setText(model.getValueAt(i,6).toString());
        if(model.getValueAt(i,9)==null){
            tfCustomer.setText("null");
        }else {
            tfCustomer.setText(model.getValueAt(i,9).toString());
        }
        String a[] = image.findPathImagebyIdTicket(Integer.parseInt(model.getValueAt(i,0).toString()));
        ImageIcon ii = new ImageIcon(a[0]);
        Image im = ii.getImage().getScaledInstance(lbImage.getWidth(), lbImage.getHeight(), Image.SCALE_SMOOTH);
        lbImage.setIcon(new ImageIcon(im));
        path1=a[0];

        ImageIcon iii = new ImageIcon(a[1]);
        Image imm = iii.getImage().getScaledInstance(lbImage1.getWidth(), lbImage1.getHeight(), Image.SCALE_SMOOTH);
        lbImage1.setIcon(new ImageIcon(imm));
        path2=a[1];
    }

    //========================= IMAGE ===========================
    protected void jButtonBrowseImageActionPerformed(java.awt.event.ActionEvent evt,int i)
    {
        JFileChooser browseImageFile = new JFileChooser("src\\Image");
        //Filter image extensions
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
        browseImageFile.addChoosableFileFilter(fnef);
        int showOpenDialogue = browseImageFile.showOpenDialog(null);

        if (showOpenDialogue == JFileChooser.APPROVE_OPTION) {
            File selectedImageFile = browseImageFile.getSelectedFile();
            String selectedImagePath = selectedImageFile.getAbsolutePath();
            //Display image on jlable
            ImageIcon ii = new ImageIcon(selectedImagePath);
            //Resize image to fit jlabel
            if(i==1){
                Image image = ii.getImage().getScaledInstance(lbImage.getWidth(), lbImage.getHeight(), Image.SCALE_SMOOTH);
                lbImage.setIcon(new ImageIcon(image));
                path1=selectedImagePath;
            }else{
                Image image = ii.getImage().getScaledInstance(lbImage1.getWidth(), lbImage1.getHeight(), Image.SCALE_SMOOTH);
                lbImage1.setIcon(new ImageIcon(image));
                path2=selectedImagePath;
            }
        }
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
                    if(i==5){
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
