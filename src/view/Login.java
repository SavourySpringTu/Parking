package view;

import Process.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import Connection.ConnectionSQL;
import com.google.zxing.WriterException;

public class Login extends JFrame {
    private JPanel panel;
    private JButton btnLogin;
    private JTextField tfUser;
    private JPasswordField tfPassword;
    private JLabel label;
    private JLabel notification;
    private ImageIcon i;
    private Image m;
    private User us = new User();
    private ConnectionSQL connectionSQL;
    public static void main (String[] args) throws WriterException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                //try {
                try {
                    Login login = new Login();
                    login.setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public Login() throws SQLException, ClassNotFoundException {
        // ==================================
        connectionSQL = new ConnectionSQL();
        Statement stmt = connectionSQL.ConnectionSQL().createStatement();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 150, 750, 550);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        btnLogin = new JButton("Login");
        btnLogin.setForeground(Color.WHITE);
        tfPassword = new JPasswordField();
        tfUser = new JTextField();
        label = new JLabel("PARKING");
        notification = new JLabel();

        panel.add(tfUser);
        tfUser.setColumns(10);
        tfUser.setBounds(275, 175, 200, 35);
        tfUser.setFont(new Font("Verdana", Font.PLAIN, 18));

        panel.add(tfPassword);
        tfPassword.setColumns(10);
        tfPassword.setBounds(275, 220, 200, 35);
        tfPassword.setFont(new Font("Verdana", Font.PLAIN, 18));

        // ==================== BUTTON LOGIN ====================
        panel.add(btnLogin);
        btnLogin.setBounds(310, 265, 130, 35);
        btnLogin.setBackground(new Color(42, 115, 196));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String a[]=us.login(tfUser.getText(),tfPassword.getText());
                    if(a==null){
                        JOptionPane.showMessageDialog(null, "Login Failed!");
                        return;
                    }
                    if(a[1].equals("R01")==true){
                        Manager manager = new Manager(a[0]);
                        manager.setVisible(true);
                        dispose();
                    }else{
                        ChoseVehicle choseVehicle = new ChoseVehicle(a[0]);
                        choseVehicle.setVisible(true);
                        dispose();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Login Failed!");
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Login Failed!");
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
