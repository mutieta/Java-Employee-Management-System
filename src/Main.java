import Pages.Home;
import Pages.Payroll;
import Pages.Update;
import Pages.View;

import javax.swing.*;
public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Payroll().setVisible(true);
            }
        });
        //Check if login success
    }
}
