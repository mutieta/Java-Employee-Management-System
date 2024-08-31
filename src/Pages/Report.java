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

public class Report extends JDialog {
    private JPanel contentPane;
    private JTable table1;
    private JTextField txtSearch;
    private JButton searchButton;
    private JButton calculateButton;
    private JButton updateButton;
    private JButton exitButton;
    private JTextField txtEmployeeId;
    private JTextField txtName;
    private JTextField txtWorkDay;
    private JTextField txtPH;
    private JTextField txtBonus;
    private JTextField txtInsurance;
    private JTextField txtRatePerDay;
    private JTextField txtRatePerHours;
    private JTextField txtTotalAmount;
    private JButton deleteButton;
    private JButton printButton;
    private JPanel StartDatePicker;
    private JPanel EndDatePicker;
    private int workDay;
    private float ph;
    private float bonus;
    private float insurance;
    private float ratePerDay;
    private float ratePerHours;
    private float totalAmount;
    private String employeeIdSearch;
    public Report() {
        setTitle("Report");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1500,800));
        setLocationRelativeTo(null);
        txtTotalAmount.setEditable(false);
        setContentPane(contentPane);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeIdSearch=txtSearch.getText();
                ResultSet rs = getPayroll(employeeIdSearch);
                try{
                    if(!rs.next()){
                        clearAllField();
                        JOptionPane.showMessageDialog(Report.this,"Employee given is not found, please try again!");
                    }else{
                        do{
                            txtEmployeeId.setText(rs.getString("employee_id_card"));
                            txtName.setText(rs.getString("employee_name"));
                            txtWorkDay.setText(String.valueOf(rs.getInt("working_day")));
                            txtPH.setText(String.valueOf(rs.getFloat("ph")));
                            txtBonus.setText(String.valueOf(rs.getFloat("bonus")));
                            txtInsurance.setText(String.valueOf(rs.getFloat("insurance")));
                            txtRatePerDay.setText(String.valueOf(rs.getFloat("rate_per_day")));
                            txtRatePerHours.setText(String.valueOf(rs.getFloat("rate_per_hour")));
                            txtTotalAmount.setText(String.valueOf(rs.getFloat("total_amount")));
                        }while (rs.next());
                    }
                }catch (SQLException s){
                    clearAllField();
                    s.printStackTrace();
                }
            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs = getPayroll(employeeIdSearch);
                try{
                    if(!rs.next()){
                        clearAllField();
                        JOptionPane.showMessageDialog(Report.this,"No employee Id found with the given Id!");
                    }else{
                        do{
                            workDay = Integer.parseInt(txtWorkDay.getText());
                            ph = Float.parseFloat(txtPH.getText());
                            bonus = Float.parseFloat(txtBonus.getText());
                            insurance = Float.parseFloat(txtInsurance.getText());
                            int salary = rs.getInt("base_salary");
                            ratePerDay = Float.parseFloat(txtRatePerDay.getText());
                            ratePerHours = Float.parseFloat(txtRatePerHours.getText());
                            totalAmount = (workDay*ratePerDay)+(ph*ratePerHours)+bonus+insurance+salary;
                            txtTotalAmount.setEditable(false);
                            txtTotalAmount.setText(String.valueOf(totalAmount));
                        }while (rs.next());
                    }
                }catch (SQLException s){
                    s.printStackTrace();
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(Report.this, "Please enter a valid number.");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(workDay==0 && ph==0 && insurance==0&& bonus==0&& ratePerDay==0&& ratePerHours==0&&totalAmount==0){
                    JOptionPane.showMessageDialog(Report.this, "You might missed to click Calculate button or input all the field!");
                }else{
                    if(updatePayroll()){
                        JOptionPane.showMessageDialog(Report.this,"Update a payroll data is successfully!");
                        clearAllField();
                    }else{
                        clearAllField();
                        JOptionPane.showMessageDialog(Report.this, "Failed to update the employee.");
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(employeeIdSearch==null){
                    JOptionPane.showMessageDialog(Report.this,"No employee Id found with the given Id!");
                }else{
                    if(deletePayroll()){
                        JOptionPane.showMessageDialog(Report.this,"Delete a payroll data is successfully!");
                        clearAllField();
                    }else{
                        clearAllField();
                        JOptionPane.showMessageDialog(Report.this, "Failed to delete the employee.");
                    }
                }

            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Home().setVisible(true);
            }
        });
    }
    private void clearAllField(){
        txtEmployeeId.setText("");
        txtName.setText("");
        txtWorkDay.setText("");
        txtPH.setText("");
        txtBonus.setText("");
        txtInsurance.setText("");
        txtRatePerDay.setText("");
        txtRatePerHours.setText("");
        txtSearch.setText("");
        txtTotalAmount.setText("");
        workDay=0;
        ph=0;
        insurance=0;
        bonus=0;
        ratePerDay=0;
        ratePerHours=0;
        totalAmount=0;
    }
    private static ResultSet getPayroll(String searchEmployeeId){
        Connection con = DBConnection.getConnection();
        ResultSet rs=null;
        try{
            PreparedStatement payrollStmt = con.prepareStatement("Select * FROM employee_management.payroll pay INNER JOIN employee_management.employee em ON em.employee_id = pay.employee_id WHERE em.employee_id_card = ?");
            payrollStmt.setString(1,searchEmployeeId);
            rs = payrollStmt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
    private boolean updatePayroll(){
        Connection con =DBConnection.getConnection();
        try{
            PreparedStatement payrollStmt = con.prepareStatement("UPDATE employee_management.payroll pay\n" +
                    "JOIN employee_management.employee em ON em.employee_id = pay.employee_id\n" +
                    "SET pay.working_day = ?, \n" +
                    "    pay.ph = ?, \n" +
                    "    pay.bonus = ?, \n" +
                    "    pay.insurance = ?, \n" +
                    "    pay.rate_per_day = ?, \n" +
                    "    pay.rate_per_hour = ?, \n" +
                    "    pay.total_amount = ?\n" +
                    "WHERE em.employee_id_card = ?;");
            payrollStmt.setInt(1,workDay);
            payrollStmt.setFloat(2, ph);
            payrollStmt.setFloat(3,bonus);
            payrollStmt.setFloat(4,insurance);
            payrollStmt.setFloat(5,ratePerDay);
            payrollStmt.setFloat(6,ratePerHours);
            payrollStmt.setFloat(7,totalAmount);
            payrollStmt.setString(8,employeeIdSearch);
            int rowAffected = payrollStmt.executeUpdate();
            return rowAffected>0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    private boolean deletePayroll(){
        Connection con = DBConnection.getConnection();
        try{
            PreparedStatement payrollStmt = con.prepareStatement("DELETE pay \n" +
                    "FROM employee_management.payroll pay\n" +
                    "JOIN employee_management.employee em ON em.employee_id = pay.employee_id\n" +
                    "WHERE em.employee_id_card = ?;");
            payrollStmt.setString(1,employeeIdSearch);
            int rowAffected  = payrollStmt.executeUpdate();
            return rowAffected>0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
