import Pages.Home;
import Pages.Login;
import Pages.View;

import javax.swing.*;
public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new View().setVisible(true);
            }
        });
        //Check if login success
    }
}
