package View;

import Connection.ConnectionSQL;

import javax.swing.*;
import Process.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register extends JFrame {
    private JPanel panel;
    private JLabel lbName;
    private JLabel lbNumber;
    private JLabel lbPhone;
    private JLabel lbDay;
    private JLabel lbPrice;
    private JTextField tfName;
    private JTextField tfNumber;
    private JTextField tfPhone;
    private JTextField tfDay;
    private JTextField tfPrice;
    private JButton btnExit;
    private JButton btnInsert;
    private Customer customer = new Customer();
    private ImageIcon i;
    private Image m;

    private ConnectionSQL connectionSQL = new ConnectionSQL();
    public Register(String user,Boolean type) {
        connectionSQL = new ConnectionSQL();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(700,150,600, 700);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lbName = new JLabel("Name");
        lbName.setBounds(110,112,100,50);
        lbName.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbName);
        lbNumber = new JLabel("Number");
        lbNumber.setBounds(110,212,100,50);
        lbNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbNumber);
        lbPhone = new JLabel("Phone");
        lbPhone.setBounds(110,294,100,50);
        lbPhone.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbPhone);
        lbDay = new JLabel("Day");
        lbDay.setBounds(110,387,100,50);
        lbDay.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbDay);
        lbPrice = new JLabel("Price");
        lbPrice.setBounds(110,478,100,50);
        lbPrice.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(lbPrice);

        tfName = new JTextField();
        tfName.setBounds(200,110,200,50);
        tfName.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfName);
        tfNumber = new JTextField();
        tfNumber.setBounds(200,200,200,50);
        tfNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfNumber);
        tfPhone = new JTextField();
        tfPhone.setBounds(200,290,200,50);
        tfPhone.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfPhone);
        tfDay = new JTextField();
        tfDay.setBounds(200,380,200,50);
        tfDay.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(tfDay);
        tfPrice = new JTextField();
        tfPrice.setBounds(200,470,200,50);
        tfPrice.setFont(new Font("Verdana", Font.PLAIN, 18));
        tfPrice.setEnabled(false);
        panel.add(tfPrice);

        btnExit = new JButton();
        btnExit.setBounds(10,10,100,50);
        i =new ImageIcon("src\\icon\\logout.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnExit.setIcon(new ImageIcon(m));
        btnExit.setBackground(new Color(42, 115, 196));
        panel.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TicketIn form = new TicketIn(user,type);
                    form.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnInsert = new JButton();
        btnInsert.setBounds(247,569,100,50);
        i =new ImageIcon("src\\icon\\btnInsert.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnInsert.setIcon(new ImageIcon(m));
        btnInsert.setBackground(new Color(42, 115, 196));
        panel.add(btnInsert);
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int i = customer.insetCustomer(tfName.getText(), Integer.parseInt(tfDay.getText()),tfPhone.getText(),tfNumber.getText(),type);
                    tfPrice.setText(String.valueOf(i));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}