package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View extends JDialog {
    private JPanel contentPane;

    public View() {
        setTitle("View");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
    }
}
