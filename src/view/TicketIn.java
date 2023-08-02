package view;

import Connection.ConnectionSQL;

import javax.swing.*;
import Process.*;
import com.google.zxing.WriterException;

import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class TicketIn extends JFrame {
    private JButton btnInsert;
    private JButton btnChoseImage1;
    private JButton btnExit;
    private JButton btnChoseImage;
    private JLabel camera;
    private JLabel camera1;
    private JLabel imageQR;
    private JLabel lbNumber;
    private JLabel lbPrice;
    private JLabel lbPosition;
    private JLabel lbCustomer;
    private JTextField tfNumber;
    private JTextField tfPrice;
    private JTextField tfPosition;
    private JTextField tfCustomer;
    private JButton btnTicketOut;
    private JButton btnCustomer;
    private ImageIcon i;
    private Image m;

    private String path;
    private String path1;
    private JPanel panel;
    private ConnectionSQL connectionSQL;
    private Ticket ticket = new Ticket();
    public TicketIn(String user,Boolean type){
        connectionSQL = new ConnectionSQL();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1920, 1080);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lbNumber = new JLabel("Number");
        lbNumber.setBounds(900,117,100,50);
        lbNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbNumber);
        lbPrice = new JLabel("Price");
        lbPrice.setBounds(900,282,100,20);
        lbPrice.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbPrice);
        lbPosition = new JLabel("Position");
        lbPosition.setBounds(900,373,100,20);
        lbPosition.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbPosition);
        lbCustomer = new JLabel("Customer");
        lbCustomer.setBounds(900,470,100,20);
        lbCustomer.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbCustomer);

        btnTicketOut = new JButton();
        btnTicketOut.setBounds(150,10,100,50);
        i = new ImageIcon("src\\icon\\btnOutTicket.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnTicketOut.setIcon(new ImageIcon(m));
        btnTicketOut.setBackground(new Color(42, 115, 196));
        panel.add(btnTicketOut);
        btnTicketOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicketOut ticketOut = new TicketOut(user,type);
                ticketOut.setVisible(true);
                dispose();

            }
        });

        btnCustomer = new JButton();
        btnCustomer.setBounds(290,10,100,50);
        i =new ImageIcon("src\\icon\\peopel.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnCustomer.setIcon(new ImageIcon(m));
        btnCustomer.setBackground(new Color(42, 115, 196));
        panel.add(btnCustomer);
        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register form = new Register(user,type);
                form.setVisible(true);
                dispose();
            }
        });

        tfNumber = new JTextField();
        tfNumber.setBounds(1015,110,200,50);
        tfNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfNumber.setEditable(false);
        panel.add(tfNumber);
        tfPrice = new JTextField();
        tfPrice.setBounds(1015,269,200,50);
        tfPrice.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfPrice.setEditable(false);
        panel.add(tfPrice);
        tfPosition = new JTextField();
        tfPosition.setBounds(1015,360,200,50);
        tfPosition.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfPosition.setEditable(false);
        panel.add(tfPosition);
        tfCustomer = new JTextField();
        tfCustomer.setBounds(1015,457,200,50);
        tfCustomer.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfCustomer.setEditable(false);
        panel.add(tfCustomer);

        camera = new JLabel();
        panel.add(camera);
        camera.setBounds(40,80,600,450);
        camera.setBackground(Color.black);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.darkGray);
        p1.setBounds(40,80,600,450);
        panel.add(p1);

        camera1 = new JLabel();
        panel.add(camera1);
        camera1.setBounds(40,580,600,450);
        JPanel p2 = new JPanel();
        p2.setBackground(Color.darkGray);
        p2.setBounds(40,580,600,450);
        panel.add(p2);


        btnInsert = new JButton();
        panel.add(btnInsert);
        btnInsert.setBounds(1650,450,100,50);
        i =new ImageIcon("src\\icon\\btnInsert.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnInsert.setIcon(new ImageIcon(m));
        btnInsert.setBackground(new Color(42, 115, 196));
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("bien so:"+tfNumber.getText());
                    String a[] = ticket.insertTicket(tfNumber.getText(),type,user,path,path1);
                    if(a==null){
                        return;
                    }
                    tfCustomer.setText(a[0]);
                    tfPosition.setText(a[1]);
                    tfPrice.setText(a[2]);
                    setImageQR(imageQR,ticket.createQR(Integer.parseInt(a[3])));
                    tfNumber.setEditable(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (WriterException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        imageQR = new JLabel();
        panel.add(imageQR);
        imageQR.setBounds(1540,110,300,300);
        JPanel p4 = new JPanel();
        p4.setBackground(Color.darkGray);
        p4.setBounds(1540,110,300,300);
        panel.add(p4);


        JPanel p3 = new JPanel();
        p3.setBackground(Color.GRAY);
        p3.setBounds(810,80,1060,450);
        panel.add(p3);

        btnExit = new JButton();
        btnExit.setBounds(10,10,100,50);
        i = new ImageIcon("src\\icon\\logout.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnExit.setIcon(new ImageIcon(m));
        btnExit.setBackground(new Color(42, 115, 196));
        panel.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChoseVehicle form = new ChoseVehicle("khaibui");
                form.setVisible(true);
                dispose();
            }
        });

        btnChoseImage = new JButton();
        panel.add(btnChoseImage);
        btnChoseImage.setBounds(670,280,100,50);
        i =new ImageIcon("src\\icon\\btnBrowser.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnChoseImage.setIcon(new ImageIcon(m));
        btnChoseImage.setBackground(new Color(42, 115, 196));
        btnChoseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                path = jButtonBrowseImageActionPerformed(camera);
            }
        });
        btnChoseImage1 = new JButton();
        panel.add(btnChoseImage1);
        btnChoseImage1.setBounds(670,780,100,50);
        i =new ImageIcon("src\\icon\\btnBrowser.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnChoseImage1.setIcon(new ImageIcon(m));
        btnChoseImage1.setBackground(new Color(42, 115, 196));
        btnChoseImage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                path1 = jButtonBrowseImageActionPerformed(camera1);
                tfNumber.setEditable(true);
            }
        });

    }
    private String jButtonBrowseImageActionPerformed(JLabel label)
    {
        JFileChooser browseImageFile = new JFileChooser("D:\\Doc\\Java\\IntelliJ Project\\Parking---Swing---MySQL---OpenCV\\src\\Image");
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
            Image image = ii.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(image));
            return selectedImagePath;
        }
        return null;
    }
    private void setImageQR(JLabel label,String path){
        ImageIcon ii = new ImageIcon(path);
        Image image = ii.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(image));
    }
    private void resetForm(){
        camera1.setIcon(new ImageIcon("asdf"));
        camera.setIcon(new ImageIcon("asdf"));
        tfNumber.setText("");
        tfPrice.setText("");
        tfPosition.setText("");
        tfCustomer.setText("");
    }
}
