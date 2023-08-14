package View;

import Connection.ConnectionSQL;

import javax.imageio.ImageIO;
import javax.swing.*;
import Process.*;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.NotFoundException;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class TicketOut extends JFrame {
    private JButton btnExport;
    private JLabel camera;
    private JLabel camera1;
    private JLabel imageQR;
    private JLabel lbNumber;
    private JLabel lbTimeIn;
    private JLabel lbCustomer;
    private JTextField tfNumber;
    private JTextField tfTimeIn;
    private JTextField tfCustomer;
    private JButton btnTicketIn;
    private JButton btnChoseImage;
    private JLabel image1;
    private JLabel image;
    private ImageIcon i;
    private Image m;
    private JPanel panel;
    private ConnectionSQL connectionSQL;
    private int idExport;
    private Ticket ticket = new Ticket();

    // ====================================
    Webcam webcam;

    public TicketOut(String user,Boolean type) {
        connectionSQL = new ConnectionSQL();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lbNumber = new JLabel("Number");
        lbNumber.setBounds(750, 617, 100, 50);
        lbNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbNumber);
        lbTimeIn = new JLabel("Time in");
        lbTimeIn.setBounds(750, 782, 100, 20);
        lbTimeIn.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbTimeIn);
        lbCustomer = new JLabel("Customer");
        lbCustomer.setBounds(750, 873, 100, 20);
        lbCustomer.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbCustomer);

        btnTicketIn = new JButton("VÀO");
        btnTicketIn.setBounds(10, 10, 200, 50);
        btnTicketIn.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\ticketin.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnTicketIn.setIcon(new ImageIcon(m));
        btnTicketIn.setBackground(new Color(42, 115, 196));
        panel.add(btnTicketIn);
        btnTicketIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    webcam.close();
                    TicketIn ticketIn = new TicketIn(user, type);
                    ticketIn.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        tfNumber = new JTextField();
        tfNumber.setBounds(900, 610, 200, 50);
        tfNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfNumber.setEditable(false);
        panel.add(tfNumber);
        tfTimeIn = new JTextField();
        tfTimeIn.setBounds(900, 769, 200, 50);
        tfTimeIn.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfTimeIn.setEditable(false);
        panel.add(tfTimeIn);
        tfCustomer = new JTextField();
        tfCustomer.setBounds(900, 860, 200, 50);
        tfCustomer.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfCustomer.setEditable(false);
        panel.add(tfCustomer);

        btnExport = new JButton("XUẤT");
        btnExport.setBounds(900, 390, 130, 50);
        btnExport.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\logout.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnExport.setIcon(new ImageIcon(m));
        btnExport.setBackground(new Color(42, 115, 196));
        panel.add(btnExport);
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ticket.exportTicket(idExport);
                    idExport=0;
                    JOptionPane.showMessageDialog(null, "Success!");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (NotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        image = new JLabel();
        image.setBounds(1273, 85, 600, 450);
        panel.add(image);
        JPanel p4 = new JPanel();
        p4.setBackground(Color.darkGray);
        p4.setBounds(1273, 85, 600, 450);
        panel.add(p4);

        image1 = new JLabel();
        image1.setBounds(1273, 590, 600, 450);
        panel.add(image1);
        JPanel p5 = new JPanel();
        p5.setBackground(Color.darkGray);
        p5.setBounds(1273, 590, 600, 450);
        panel.add(p5);

        btnChoseImage= new JButton();
        btnChoseImage.setBounds(1115, 200, 100, 50);
        btnChoseImage.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\btnBrowser.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnChoseImage.setIcon(new ImageIcon(m));
        btnChoseImage.setBackground(new Color(42, 115, 196));
        panel.add(btnChoseImage);
        btnChoseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageIO.write(webcam.getImage(),"png",new File("src\\imageQR\\a.png"));
                    refreshForm();

                    String a[]= ticket.loadImageByQR("src\\imageQR\\a.png",type);
                    tfNumber.setText(a[0]);
                    tfTimeIn.setText(a[1]);
                    tfCustomer.setText(a[2]);
                    idExport = Integer.parseInt(a[5]);

                    ImageIcon ii = new ImageIcon(a[3]);
                    Image im = ii.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
                    image.setIcon(new ImageIcon(im));

                    ImageIcon iii = new ImageIcon(a[4]);
                    Image imm = iii.getImage().getScaledInstance(image1.getWidth(), image1.getHeight(), Image.SCALE_SMOOTH);
                    image1.setIcon(new ImageIcon(imm));
                } catch (NotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        camera = new JLabel();
        panel.add(camera);
        camera.setBounds(40, 85, 600, 450);
        camera.setBackground(Color.black);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.darkGray);
        p1.setBounds(40, 85, 600, 450);
        panel.add(p1);

        camera1 = new JLabel();
        panel.add(camera1);
        camera1.setBounds(40, 580, 600, 450);
        JPanel p2 = new JPanel();
        p2.setBackground(Color.darkGray);
        p2.setBounds(40, 580, 600, 450);
        panel.add(p2);

        // ====================== Webcam =================
        webcam= Webcam.getDefault();
        webcam.setViewSize(new Dimension(320,240));
        webcam.open();
        new Thread(new Runnable() {
            @Override public void run()
            {
                while(true){
                    try {
                        Image image = webcam.getImage();
                        camera.setIcon(new ImageIcon(image));
                        camera1.setIcon(new ImageIcon(image));
                        imageQR.setIcon(new ImageIcon(image));
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        imageQR = new JLabel();
        panel.add(imageQR);
        imageQR.setBounds(789, 114, 300, 225);
        JPanel p6 = new JPanel();
        p6.setBackground(Color.darkGray);
        p6.setBounds(789, 114, 300, 225);
        panel.add(p6);

        JPanel p3 = new JPanel();
        p3.setBackground(Color.GRAY);
        p3.setBounds(673, 85, 559, 950);
        panel.add(p3);
    }
    public void refreshForm(){
        tfTimeIn.setText("");
        tfCustomer.setText("");
        tfNumber.setText("");
        ImageIcon ii = new ImageIcon("");
        Image im = ii.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
        image.setIcon(new ImageIcon(im));

        ImageIcon iii = new ImageIcon("");
        Image imm = iii.getImage().getScaledInstance(image1.getWidth(), image1.getHeight(), Image.SCALE_SMOOTH);
        image1.setIcon(new ImageIcon(imm));
    }
}

