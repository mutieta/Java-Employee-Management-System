import javax.swing.*;
import java.awt.*;

public class Home extends JDialog {
    private JPanel contentPane;
    private JButton viewEmployeeButton;
    private JButton updateEmployeeButton;
    private JButton removeEmployeeButton;
    private JButton allowanceButton;
    private JButton addEmployeeButton;

    public Home() {
        setTitle("Main");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
    }
}
