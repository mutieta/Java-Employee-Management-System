package Pages;

import db.DBConnection;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class View extends JDialog {
    private JPanel contentPane;
    private JTable tblEmployee;
    private JTextField txtSearch;
    private JButton exitButton;
    private JButton searchButton;

    public View() {
        ResultSet rs = getEmployeeData("");
        initializedTable(rs);
        setTitle("View");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1300, 700));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Home().setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strSearch = txtSearch.getText();
                ResultSet rs = getEmployeeData(strSearch);
                initializedTable(rs);
            }
        });
    }

    private void initializedTable(ResultSet rs) {
        List<Object[]>employeeList = new ArrayList<>();
        String[] columnNames = {
                "ID Card", "Employee Name", "Gender", "Email",
                "Phone Number", "Address", "Department", "Position",
                "Date of Birth", "Date of Hiring", "Base Salary",
                "Status", "Bank Name", "Bank Account"
        };
        try {
            while (rs.next()) {
                String employeeIdCard = rs.getString("employee_id_card");
                String employeeName = rs.getString("employee_name");
                String genderName = rs.getString("gender_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String address = rs.getString("address");
                String departmentName = rs.getString("department_name");
                String positionName = rs.getString("position_name");

                // Retrieve and format date values from the ResultSet
                Date dobDate = rs.getDate("date_of_birth");
                Date dohDate = rs.getDate("date_of_hiring");
                String formattedDob = dobDate != null ? dobDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
                String formattedDoh = dohDate != null ? dohDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";

                int baseSalary = rs.getInt("base_salary");
                String status = rs.getString("status_name");
                String bankName = rs.getString("bank_name");
                String bankAccount = rs.getString("bank_account_number");

                // Add the data to the list as an Object array
                employeeList.add(new Object[]{
                        employeeIdCard, employeeName, genderName, email,
                        phoneNumber, address, departmentName, positionName,
                        formattedDob, formattedDoh, baseSalary,
                        status, bankName, bankAccount
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Ensure the ResultSet and Statement are closed properly
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Create a table model with the column names and data
        DefaultTableModel model = new DefaultTableModel(employeeList.toArray(new Object[0][0]), columnNames);
        tblEmployee.setModel(model);
    }
    public static ResultSet getEmployeeData(String searchEmployeeId){
        Connection con = DBConnection.getConnection();
        ResultSet resultSet =null;
        try{
            if (searchEmployeeId != null && !searchEmployeeId.isEmpty()) {
                PreparedStatement data = con.prepareStatement("SELECT * FROM employee_management.employee e\n" +
                        "INNER JOIN employee_management.gender ON gender.gender_id = e.gender_id\n" +
                        "INNER JOIN employee_management.department de ON de.department_id = e.department_id\n" +
                        "INNER JOIN employee_management.position po ON po.position_id = e.position_id\n" +
                        "INNER JOIN employee_management.status ON status.status_id = e.status_id\n"+
                        "WHERE e.employee_id_card =?");
                data.setString(1, searchEmployeeId);
                resultSet = data.executeQuery();
            } else {
                PreparedStatement data = con.prepareStatement("SELECT * FROM employee_management.employee e\n" +
                        "INNER JOIN employee_management.gender ON gender.gender_id = e.gender_id\n" +
                        "INNER JOIN employee_management.department de ON de.department_id = e.department_id\n" +
                        "INNER JOIN employee_management.position po ON po.position_id = e.position_id\n" +
                        "INNER JOIN employee_management.status ON status.status_id = e.status_id\n"+
                        "ORDER BY e.employee_id ASC");
                resultSet = data.executeQuery();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
