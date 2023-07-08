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
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnExport;

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
        btnAdd = new JButton("ADD");
        btnUpdate = new JButton("UPDATE");
        btnExport = new JButton("EXPORT");

        panel.add(btnTakeIn);
        btnTakeIn.setBounds(178,430,130,35);
        btnTakeIn.setBackground(Color.GREEN);

        panel.add(btnTakeOut);
        btnTakeOut.setBounds(178,810,130,35);
        btnTakeOut.setBackground(Color.GREEN);

        panel.add(lbId);
        lbId.setBounds(600,100,100,50);
        panel.add(lbNumber);
        lbNumber.setBounds(600,150,100,50);
        panel.add(lbPrice);
        lbPrice.setBounds(600,200,100,50);
        panel.add(lbType);
        lbType.setBounds(600,250,100,50);

        panel.add(tfId);
        tfId.setBounds(680,110,200,35);
        panel.add(tfNumber);
        tfNumber.setBounds(680,160,200,35);
        panel.add(tfPrice);
        tfPrice.setBounds(680,210,200,35);
        panel.add(tfType);
        tfType.setBounds(680,260,200,35);

        panel.add(btnAdd);
        btnAdd.setBounds(950, 130, 130, 35);
        btnAdd.setBackground(Color.GREEN);
        panel.add(btnUpdate);
        btnUpdate.setBounds(950, 180, 130, 35);
        btnUpdate.setBackground(Color.ORANGE);
        panel.add(btnExport);
        btnExport.setBounds(950, 230, 130, 35);
        btnExport.setBackground(Color.CYAN);


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
