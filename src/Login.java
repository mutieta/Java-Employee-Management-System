import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel MainPanel;
    private JPanel SubPanel;
    private JTextField txtusername;
    private JLabel password;
    private JPasswordField txtpass;
    private JLabel email;
    private JButton logInButton;
    private JButton btnLogin;
    private JButton clearButton;
    private JPanel ButtomPanel;
    private JPanel IconPanel;
    private JLabel icon;

    public Login() {
        setTitle("Login Form");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setContentPane(MainPanel);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtusername.setText("");
                txtpass.setText("");
            }
        });
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username= txtusername.getText();
                String password = new String(txtpass.getPassword());
                if(DBConnection.validatedLogin(username,password)){
                    JOptionPane.showMessageDialog(Login.this,"Login Success...!");
                    dispose();
                    new Home().setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(Login.this,"Login Fail...!");
                }
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
