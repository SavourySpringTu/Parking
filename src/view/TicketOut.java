package view;

import Connection.ConnectionSQL;

import javax.swing.*;
import Process.*;
import com.google.zxing.NotFoundException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class TicketOut extends JFrame {
    private JButton btnExport;
    private JLabel camera;
    private JLabel camera1;
    private JLabel imageQR;
    private JLabel lbNumber;
    private JLabel lbTimeIn;
    private JLabel lbPosition;
    private JLabel lbCustomer;
    private JTextField tfNumber;
    private JTextField tfTimeIn;
    private JTextField tfPosition;
    private JTextField tfCustomer;
    private JButton btnTicketIn;
    private JButton btnChoseImage;
    private JLabel image1;
    private JLabel image;
    private ImageIcon i;
    private Image m;
    // ======================================
    private static JLabel cameraScreen;
    private JButton btnCapture;
    private static VideoCapture capture;
    private static Mat mat;
    private static boolean clicked = false;
    // ====================================

    private JPanel panel;
    private ConnectionSQL connectionSQL;
    private String pathQR;
    private Ticket ticket = new Ticket();
    public TicketOut(String user,Boolean type) {
        // ==================== CAMERA THREAD ==============================


        // =================================================================
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
        lbPosition = new JLabel("Position");
        lbPosition.setBounds(750, 873, 100, 20);
        lbPosition.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbPosition);
        lbCustomer = new JLabel("Customer");
        lbCustomer.setBounds(750, 970, 100, 20);
        lbCustomer.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbCustomer);

        btnTicketIn = new JButton();
        btnTicketIn.setBounds(150, 10, 100, 50);
        btnTicketIn.setForeground(Color.WHITE);
        i = new ImageIcon("src\\icon\\ticketin.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnTicketIn.setIcon(new ImageIcon(m));
        btnTicketIn.setBackground(new Color(42, 115, 196));
        panel.add(btnTicketIn);
        btnTicketIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicketIn ticketIn = new TicketIn(user, type);
                ticketIn.setVisible(true);
                dispose();
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
        tfPosition = new JTextField();
        tfPosition.setBounds(900, 860, 200, 50);
        tfPosition.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfPosition.setEditable(false);
        panel.add(tfPosition);
        tfCustomer = new JTextField();
        tfCustomer.setBounds(900, 957, 200, 50);
        tfCustomer.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfCustomer.setEditable(false);
        panel.add(tfCustomer);

        btnExport = new JButton();
        btnExport.setBounds(889, 436, 100, 50);
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
                    ticket.exportTicket(pathQR);
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
        btnChoseImage.setBounds(1115, 239, 100, 50);
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
                    refreshForm();
                    pathQR = jButtonBrowseImageActionPerformed(imageQR);
                    String a[]= ticket.loadImageByQR(pathQR);
                    tfNumber.setText(a[0]);
                    tfTimeIn.setText(a[1]);
                    tfCustomer.setText(a[2]);
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

        imageQR = new JLabel();
        panel.add(imageQR);
        imageQR.setBounds(789, 114, 300, 300);
        JPanel p6 = new JPanel();
        p6.setBackground(Color.darkGray);
        p6.setBounds(789, 114, 300, 300);
        panel.add(p6);

        JPanel p3 = new JPanel();
        p3.setBackground(Color.GRAY);
        p3.setBounds(673, 85, 559, 980);
        panel.add(p3);
    }
    public static void startCamera()
    {
        System.out.println("toi day");
        capture = new VideoCapture(0);
        mat = new Mat();
        byte[] imageData;
        ImageIcon icon;
        while (true) {
            capture.read(mat);

            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", mat, buf);
            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);

            if (clicked) {
                String name = JOptionPane.showInputDialog(
                        cameraScreen, "Enter image name");
                if (name == null) {
                    name = new SimpleDateFormat(
                            "yyyy-mm-dd-hh-mm-ss")
                            .format(new Date(
                                    HEIGHT, WIDTH, 5));
                }
                Imgcodecs.imwrite("D:/Doc/Java/IntelliJ Project/Parking-Swing/src/Image/" + name + ".png",mat);
//
                clicked = false;
            }
        }
    }
    private String jButtonBrowseImageActionPerformed(JLabel label)
    {
        JFileChooser browseImageFile = new JFileChooser("D:\\Doc\\Java\\IntelliJ Project\\Parking---Swing---MySQL---OpenCV\\src\\imageQR");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
        browseImageFile.addChoosableFileFilter(fnef);
        int showOpenDialogue = browseImageFile.showOpenDialog(null);

        if (showOpenDialogue == JFileChooser.APPROVE_OPTION) {
            File selectedImageFile = browseImageFile.getSelectedFile();
            String selectedImagePath = selectedImageFile.getAbsolutePath();
            ImageIcon ii = new ImageIcon(selectedImagePath);
            Image image = ii.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(image));
            return selectedImagePath;
        }
        return null;
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

