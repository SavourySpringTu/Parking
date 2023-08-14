package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ChoseVehicle extends JFrame{
    private JPanel panel;
    private JButton btnCar;
    private JButton btnMotor;
    private JButton btnExit;
    private ImageIcon i;
    private Image m;
    public ChoseVehicle(String user){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600,400,600, 250);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        btnCar = new JButton("Car");
        btnCar.setBounds(110,116,150,50);
        i = new ImageIcon("src\\icon\\car.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnCar.setIcon(new ImageIcon(m));
        btnCar.setBackground(new Color(42, 115, 196));
        panel.add(btnCar);
        btnCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TicketIn form = new TicketIn(user,true);
                    form.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnMotor = new JButton("Motor");
        btnMotor.setBounds(343,116,150,50);
        i = new ImageIcon("src\\icon\\motor.png");
        m = i.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnMotor.setIcon(new ImageIcon(m));
        btnMotor.setBackground(new Color(42, 115, 196));
        panel.add(btnMotor);
        btnMotor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TicketIn form = new TicketIn(user,false);
                    form.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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
                try {
                    Login form = new Login();
                    form.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
