package Pages;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import Constants.KeyValue;
import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Add extends JDialog {
    private JPanel contentPane;
    private JTextField txtEmployeeId;
    private JTextField txtName;
    private JComboBox txtGender;
    private JTextField txtEmail;
    private JTextField txtContact;
    private JTextField txtAddress;
    private JTextField txtSalary;
    private JComboBox txtStatus;
    private JTextField txtBankName;
    private JButton createButton;
    private JButton exitButton;
    private JPanel addPanelLeft;
    private JPanel addPanelRight;
    private JPanel dobPickerPanel;
    private JComboBox departComboBox;
    private JComboBox positionComboBox;
    private JTextField txtBankAccountNumber;
    private JPanel dohPickerPanel;
    private JPanel btnPanel;
    public Add() {
        genderComboBox();
        statusComboBox();
        positionComboBox();
        departmentComboBox();
        dobDatePicker();
        dohDatePicker();
        setTitle("Add Employee");
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
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String employeeId = txtEmployeeId.getText();
                    String employeeName = txtName.getText();
                    KeyValue gender = (KeyValue) txtGender.getSelectedItem();
                    int genderId = gender.getKey();
                    String email = txtEmail.getText();
                    String contact = txtContact.getText();
                    String address = txtAddress.getText();
                    int salary = Integer.parseInt(txtSalary.getText().trim());
                    KeyValue status = (KeyValue) txtStatus.getSelectedItem();
                    int statusId = status.getKey();
                    String bankName = txtBankName.getText();
                    String bankAccountNumber = txtBankAccountNumber.getText();
                    // Retrieving LocalDate from DatePicker components
                    LocalDate dobLocalDate = ((DatePicker) dobPickerPanel.getComponent(0)).getDate();
                    LocalDate dohLocalDate = ((DatePicker) dohPickerPanel.getComponent(0)).getDate();

                    // Converting LocalDate to java.sql.Date
                    java.sql.Date dob = java.sql.Date.valueOf(dobLocalDate);
                    java.sql.Date doh = java.sql.Date.valueOf(dohLocalDate);
                    KeyValue department = (KeyValue) departComboBox.getSelectedItem();
                    int departmentId = department.getKey();
                    KeyValue position = (KeyValue) positionComboBox.getSelectedItem();
                    int positionId= position.getKey();
                    // Validation logic (this can be customized as needed)
                    if (employeeId.isEmpty() || employeeName.isEmpty() || genderId == 0 ||
                            email.isEmpty() || contact.isEmpty() || address.isEmpty() ||
                            salary <= 0 || statusId == 0 || bankName.isEmpty() ||
                            bankAccountNumber.isEmpty() || dobLocalDate == null || dohLocalDate == null ||
                            departmentId == 0 || positionId == 0) {

                        JOptionPane.showMessageDialog(Add.this, "You missed something! Please fill in all required fields.");
                    } else {
                        // Process and store the data
                        boolean insertEmployee= insertEmployeeData(employeeId,employeeName,genderId,email,contact,address,departmentId,positionId,dob,doh,salary,statusId,bankName,bankAccountNumber);
                        if(insertEmployee){
                            JOptionPane.showMessageDialog(Add.this,"Insert an employee data was successfully!");
                            dispose();
                            new Home().setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(Add.this,"Insert an employee data was Fail!");
                        }
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Add.this, "Please enter a valid number for the salary.");
                }
            }
        });
    }

    private void genderComboBox() {
        List<KeyValue> genderItems = getGender();
        for (KeyValue kw : genderItems) txtGender.addItem(kw);
    }
    private void statusComboBox() {
        List<KeyValue> statusItems = getStatus();
        for (KeyValue kw : statusItems) txtStatus.addItem(kw);
    }
    private void positionComboBox() {
        List<KeyValue> positionItem = getPosition();
        for (KeyValue kw : positionItem) positionComboBox.addItem(kw);
    }
    private void departmentComboBox() {
        List<KeyValue> departmentItem= getDepartment();
        for (KeyValue kw : departmentItem) departComboBox.addItem(kw);
    }

    private void dobDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        DatePicker datePicker = new DatePicker(dateSettings);
        dobPickerPanel.add(datePicker); // Add the date picker to the center of the panel
    }
    private void dohDatePicker() {
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        DatePicker datePicker = new DatePicker(dateSettings);
        dohPickerPanel.add(datePicker); // Add the date picker to the center of the panel
    }
    public static boolean insertEmployeeData(String employee_id_card, String employee_name, int gender_id, String email, String phone_number, String address, int department_id, int position_id, Date date_of_birth, Date date_of_hiring, int base_salary, int status_id, String bank_name, String bank_account_number ){
        Connection con = DBConnection.getConnection();
        try{
            String query="INSERT INTO employee_management.employee (employee_id_card, employee_name, gender_id, email, phone_number, address, department_id, position_id, date_of_birth, date_of_hiring, base_salary, status_id, bank_name, bank_account_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(query);
            insertStmt.setString(1,employee_id_card);
            insertStmt.setString(2,employee_name);
            insertStmt.setInt(3, gender_id);
            insertStmt.setString(4,email);
            insertStmt.setString(5,phone_number);
            insertStmt.setString(6,address);
            insertStmt.setInt(7, department_id);
            insertStmt.setInt(8, position_id);
            insertStmt.setDate(9, date_of_birth);
            insertStmt.setDate(10, date_of_hiring);
            insertStmt.setInt(11, base_salary);
            insertStmt.setInt(12, status_id);
            insertStmt.setString(13, bank_name);
            insertStmt.setString(14, bank_account_number);
            int rowAffected= insertStmt.executeUpdate();
            return rowAffected>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
private static List<KeyValue> getGender(){
        Connection con= DBConnection.getConnection();
        List<KeyValue> genderList = new ArrayList<>();
    ResultSet resultSet=null;
        try {
            PreparedStatement getGenderStmt = con.prepareStatement("SELECT * FROM employee_management.gender");
            resultSet = getGenderStmt.executeQuery();
                genderList.add(new KeyValue(0,"Select gender"));

                while (resultSet.next()){
                    int genderId = resultSet.getInt("gender_id");
                    String genderName = resultSet.getString("gender_name");
                    genderList.add(new KeyValue(genderId, genderName));
                }
        }catch (SQLException e){
            e.printStackTrace();
        }
    return genderList;
}private static List<KeyValue> getStatus(){
        Connection con = DBConnection.getConnection();
        List<KeyValue> statusList = new ArrayList<>();
        try {
            PreparedStatement statusStmt = con.prepareStatement("SELECT * FROM employee_management.status");
            ResultSet resultSet = statusStmt.executeQuery();
            statusList.add(new KeyValue(0, "Select department"));

            while (resultSet.next()) {
                int status_id = resultSet.getInt("status_id");
                String statusName= resultSet.getString("status_name");
                statusList.add(new KeyValue(status_id,statusName));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return statusList;
    };
private static List<KeyValue> getPosition(){
        Connection con = DBConnection.getConnection();
        List<KeyValue> positionList = new ArrayList<>();
        try {
            PreparedStatement getPositionStmt = con.prepareStatement("SELECT * FROM employee_management.position");
            ResultSet resultSet = getPositionStmt.executeQuery();
            positionList.add(new KeyValue(0, "Select position"));
            while (resultSet.next()) {
                int positionId = resultSet.getInt("position_id");
                String positionName= resultSet.getString("position_name");
                positionList.add(new KeyValue(positionId,positionName));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return positionList;
    };
private static List<KeyValue> getDepartment(){
        Connection con = DBConnection.getConnection();
        List<KeyValue> departmentList = new ArrayList<>();
        try {
            PreparedStatement getDepartmentStmt = con.prepareStatement("SELECT * FROM employee_management.department");
            ResultSet resultSet = getDepartmentStmt.executeQuery();
            departmentList.add(new KeyValue(0, "Select department"));

            while (resultSet.next()) {
                int departmentId = resultSet.getInt("department_id");
                String departmentName= resultSet.getString("department_name");
                departmentList.add(new KeyValue(departmentId,departmentName));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    return departmentList;
};
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
