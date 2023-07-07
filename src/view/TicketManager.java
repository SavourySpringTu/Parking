package view;

import About.Check;
import Connection.ConnectionSQL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicketManager extends JFrame{
    private JFrame frame;
    private JPanel panel;
    private JLabel lbId;
    private JLabel lbNumber;
    private JLabel lbType;
    private JLabel lbPrice;
    private JTextField tfId;
    private JTextField tfNumber;
    private JTextField tfType;
    private JTextField tfPrice;
    private JButton btnExit;
    private JButton btnTakeIn;
    private JButton btnTakeOut;
    private ConnectionSQL connectionSQL;
    private DefaultTableModel model = new DefaultTableModel();
    private Check check = new Check();
    public TicketManager(){
        connectionSQL = new ConnectionSQL();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(170,60,1600, 1000);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lbPrice = new JLabel("Price");
        lbNumber = new JLabel("Number");
        lbId = new JLabel("Id");
        lbType = new JLabel("Type");
        tfId = new JTextField();
        tfPrice = new JTextField();
        tfType = new JTextField();
        tfNumber = new JTextField();
        btnExit = new JButton("EXIT");
        btnTakeIn = new JButton("TAKE");
        btnTakeOut = new JButton("TAKE");

        panel.add(btnTakeIn);
        btnTakeIn.setBounds(178,430,130,35);
        btnExit.setBackground(Color.GREEN);

        panel.add(btnTakeOut);
        btnTakeOut.setBounds(178,810,130,35);
        btnTakeOut.setBackground(Color.GREEN);

        panel.add(btnExit);
        btnExit.setBounds(10,10,100,50);
        btnExit.setBackground(Color.RED);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager manager = new Manager();
                manager.setVisible(true);
                dispose();
            }
        });

    }

}
