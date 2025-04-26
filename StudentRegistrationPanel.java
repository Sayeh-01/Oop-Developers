import java.awt.*;
import javax.swing.*;

public class StudentRegistrationPanel extends JPanel {
    private JTextField nameField, idField, emailField;
    private JComboBox<String> departmentCombo;
    private JButton submitButton;

    public StudentRegistrationPanel() {
        setLayout(new BorderLayout());
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(20);
        formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        String[] departments = {"Computer Science", "Mathematics", "Physics", "Chemistry"};
        departmentCombo = new JComboBox<>(departments);
        formPanel.add(departmentCombo, gbc);

        // Add submit button
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Register Student");
        submitButton.addActionListener(e -> registerStudent());
        formPanel.add(submitButton, gbc);

        // Add form panel to main panel
        add(formPanel, BorderLayout.CENTER);
    }

    private void registerStudent() {
        // TODO: Implement student registration logic
        String name = nameField.getText();
        String id = idField.getText();
        String email = emailField.getText();
        String department = (String) departmentCombo.getSelectedItem();

        if (name.isEmpty() || id.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show success message
        JOptionPane.showMessageDialog(this, 
            "Student registered successfully!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Clear fields
        nameField.setText("");
        idField.setText("");
        emailField.setText("");
    }
} 