package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JDialog {
    private JPanel contentPane;
    private JPanel subPanel;
    private JButton addEmployeeButton;
    private JButton viewEmployeeButton;

    public Home(){
        setTitle("Main");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Add().setVisible(true);
            }
        });
    }
}

