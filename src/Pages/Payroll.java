package Pages;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Payroll extends JDialog {
    private JPanel contentPane;
    private JPanel subPanel;
    private JTextField txtSearch;
    private JButton searchBtn;
    private JTextField txtSalary;
    private JTextField txtName;
    private JTextField txtDepartment;
    private JTextField txtPosition;
    private JTextField txtWorkDay;
    private JTextField txtPH;
    private JTextField txtBonus;
    private JTextField txtInsurance;
    private JTextField txtRatePerDay;
    private JTextField txtRatePerHours;
    private JTextField txtTotalAmount;
    private JButton saveButton;
    private JButton exitButton;
    private JTable table1;
    private JButton calculateButton;
private String employeeIdSearch;
    public Payroll() {
        setTitle("Payroll");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1300,700));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        txtName.setEditable(false);
        txtDepartment.setEditable(false);
        txtPosition.setEditable(false);
        txtSalary.setEditable(false);
        txtTotalAmount.setEditable(false);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Home().setVisible(true);
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeIdSearch = txtSearch.getText();
                ResultSet rs = getAnEmployee(employeeIdSearch);
                try{
                    if(!rs.next()){
                        JOptionPane.showMessageDialog(Payroll.this,"No employee Id found with the given Id!");
                    }else{
                        do{
                            txtName.setText(rs.getString("employee_name"));
                            txtDepartment.setText(rs.getString("department_name"));
                            txtPosition.setText(rs.getString("position_name"));
                            txtSalary.setText(String.valueOf(rs.getInt("base_salary")));
                        }while (rs.next());
                    }
                }catch (SQLException s){
                    s.printStackTrace();
                }
            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs = getAnEmployee(employeeIdSearch);
                try{
                    if(!rs.next()){
                        JOptionPane.showMessageDialog(Payroll.this,"No employee Id found with the given Id!");
                    }else{
                        do{
                            int workDay = Integer.parseInt(txtWorkDay.getText());
                            float ph = Float.parseFloat(txtPH.getText());
                            float bonus = Float.parseFloat(txtBonus.getText());
                            float insurance = Float.parseFloat(txtInsurance.getText());
                            int salary = rs.getInt("base_salary");
                            float ratePerDay = Float.parseFloat(txtRatePerDay.getText());
                            float ratePerHours = Float.parseFloat(txtRatePerHours.getText());
                            float totalAmount = (workDay*ratePerDay)+(ph*ratePerHours)+bonus+insurance+salary;
                            txtTotalAmount.setEditable(false);
                            txtTotalAmount.setText(String.valueOf(totalAmount));
                        }while (rs.next());
                    }
                }catch (SQLException s){
                    s.printStackTrace();
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(Payroll.this, "Please enter a valid number.");
                }
            }
        });
    }
    private static ResultSet getAnEmployee(String employeeId){
        Connection con = DBConnection.getConnection();
        ResultSet rs=null;
        try{
            PreparedStatement data = con.prepareStatement("SELECT * FROM employee_management.employee e\n" +
                    "INNER JOIN employee_management.gender ON gender.gender_id = e.gender_id\n" +
                    "INNER JOIN employee_management.department de ON de.department_id = e.department_id\n" +
                    "INNER JOIN employee_management.position po ON po.position_id = e.position_id\n" +
                    "INNER JOIN employee_management.status ON status.status_id = e.status_id\n"+
                    "WHERE e.employee_id_card =?");
            data.setString(1, employeeId);
            rs = data.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
}
