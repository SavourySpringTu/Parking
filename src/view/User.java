package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class User extends JFrame {
    private JFrame frame;
    private JPanel panel;
    public User(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1960, 1080);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);
    }
}
