package Pages;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import Constants.KeyValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private KeyValue[] genderItems = {
            new KeyValue(0, "Select gender"),
            new KeyValue(1, "Female"),
            new KeyValue(2, "Male")
    };

    private KeyValue[] statusItems = {
            new KeyValue(0, "Select status"),
            new KeyValue(1, "Active"),
            new KeyValue(2, "Disable")
    };

    private KeyValue[] departmentItem = {
            new KeyValue(0, "Select a department"),
            new KeyValue(1, "Software Developer"),
            new KeyValue(2, "Sale"),
            new KeyValue(3, "Marketing")
    };

    private KeyValue[] positionItem = {
            new KeyValue(0, "Select a position"),
            new KeyValue(1, "Senior Frontend Developer"),
            new KeyValue(2, "Junior Backend Developer"),
            new KeyValue(3, "Sale Assistant"),
            new KeyValue(4, "Senior Digital Marketing")
    };

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
        setMinimumSize(new Dimension(1000, 650));
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
                    String employeId = txtEmployeeId.getText();
                    String employeeName = txtName.getText();
                    KeyValue gender = (KeyValue) txtGender.getSelectedItem();
                    String email = txtEmail.getText();
                    String contact = txtContact.getText();
                    String address = txtAddress.getText();
                    int salary = Integer.parseInt(txtSalary.getText().trim());
                    KeyValue status = (KeyValue) txtStatus.getSelectedItem();
                    String bankName = txtBankName.getText();
                    String bankAccountNumber = txtBankAccountNumber.getText();
                    // Assuming dob and doh are set up correctly with the DatePicker component.
                    String dob = ((DatePicker) dobPickerPanel.getComponent(0)).getText();
                    String doh = ((DatePicker) dohPickerPanel.getComponent(0)).getText();
                    KeyValue department = (KeyValue) departComboBox.getSelectedItem();
                    KeyValue position = (KeyValue) positionComboBox.getSelectedItem();

                    // Validation logic (this can be customized as needed)
                    if (employeId.isEmpty() || employeeName.isEmpty() || gender.getKey() == 0 ||
                            email.isEmpty() || contact.isEmpty() || address.isEmpty() ||
                            salary <= 0 || status.getKey() == 0 || bankName.isEmpty() ||
                            bankAccountNumber.isEmpty() || dob.isEmpty() || doh.isEmpty() ||
                            department.getKey() == 0 || position.getKey() == 0) {

                        JOptionPane.showMessageDialog(Add.this, "You missed something! Please fill in all required fields.");
                    } else {
                        // Process and store the data
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Add.this, "Please enter a valid number for the salary.");
                }
            }
        });
    }

    private void genderComboBox() {
        for (KeyValue kw : genderItems) txtGender.addItem(kw);
    }

    private void statusComboBox() {
        for (KeyValue kw : statusItems) txtStatus.addItem(kw);
    }

    private void positionComboBox() {
        for (KeyValue kw : positionItem) positionComboBox.addItem(kw);
    }

    private void departmentComboBox() {
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
