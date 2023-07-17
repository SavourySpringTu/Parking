package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Connection.ConnectionSQL;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Login extends JFrame {
    private static JFrame frame;
    private JPanel panel;
    private JButton btnLogin;
    private JTextField tfUser;
    private JTextField tfPassword;
    private JLabel label;
    private JLabel notification;
    private ConnectionSQL connectionSQL;
    private String user;
    private String password;
    private String id_role;
    public static void main (String[] args) throws WriterException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Manager manager = new Manager();
                    manager.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Login() throws SQLException, ClassNotFoundException {
        connectionSQL = new ConnectionSQL();
        Statement stmt = connectionSQL.ConnectionSQL().createStatement();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 150, 750, 550);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        btnLogin = new JButton("Login");
        tfPassword = new JTextField();
        tfUser = new JTextField();
        label = new JLabel("PARKING");
        notification = new JLabel();

        panel.add(tfUser);
        tfUser.setColumns(10);
        tfUser.setBounds(275, 175, 200, 35);

        panel.add(tfPassword);
        tfPassword.setColumns(10);
        tfPassword.setBounds(275, 220, 200, 35);

        // ==================== BUTTON LOGIN ====================
        panel.add(btnLogin);
        btnLogin.setBounds(310, 265, 130, 35);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user = tfUser.getText();
                password = tfPassword.getText();
                try {
                    ResultSet rs = stmt.executeQuery("SELECT id,password,id_role FROM user");
                    while(rs.next()){
                        if(rs.getString("id").equals(user)==true && rs.getString("password").equals(password)==true){
                            if(rs.getString("id_role").equals("R01")==true){
                                Manager manager = new Manager();
                                manager.setVisible(true);
                                dispose();
                            }else{
                                User user = new User();
                                user.setVisible(true);
                                dispose();
                            }
                        }else{
                            notification.setText("EROR");
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        panel.add(label);
        label.setFont(new Font("Serif", Font.PLAIN, 40));
        label.setBounds(285,50,300,100);

        panel.add(notification);
        notification.setBounds(360,300,200,200);
    }
}
