package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Add extends JDialog {
    private JPanel contentPane;
    public Add() {
        setTitle("Add Employee");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
    }
}
