package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Manager extends JFrame {
    private JPanel panel;
    private JButton btnRole;
    private JButton btnTicket;
    private JButton btnUser;
    private JButton btnPosition;
    private JButton btnWareHouse;
    private JButton btnRevenue;
    private JButton btnExit;
    public Manager(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(170,60,1600, 1000);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        btnExit = new JButton("EXIT");
        btnTicket = new JButton("TICKET");
        btnRole = new JButton("ROLE");
        btnUser = new JButton("USER");
        btnPosition = new JButton("POSITIONS");
        btnRevenue = new JButton("REVENUE");
        btnWareHouse = new JButton("WAREHOUSE");

        // ==================== BUTTON EXIT ====================
        panel.add(btnExit);
        btnExit.setBounds(10,10,100,50);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login login = new Login();
                    login.setVisible(true);
                    dispose();
                    System.out.println("Role");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // ==================== BUTTON TICKET =====================
        panel.add(btnTicket);
        btnTicket.setBounds(600,100,400,70);
        btnTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicketManager ticketManager = new TicketManager();
                ticketManager.setVisible(true);
                dispose();
            }
        });

        // ===================== BUTTON ROLE =====================
        panel.add(btnRole);
        btnRole.setBounds(600,220,400,70);
        btnRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    RoleManager roleManager = new RoleManager();
                    roleManager.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnUser);
        btnUser.setBounds(600,340,400,70);

        panel.add(btnPosition);
        btnPosition.setBounds(600,460,400,70);

        panel.add(btnRevenue);
        btnRevenue.setBounds(600,580,400,70);

        panel.add(btnWareHouse);
        btnWareHouse.setBounds(600,700,400,70);
    }
}
