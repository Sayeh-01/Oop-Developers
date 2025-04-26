import java.awt.*;
import javax.swing.*;

public class CourseManagementPanel extends JPanel {
    private JTextField courseIdField, titleField, creditField;
    private JComboBox<String> departmentCombo;
    private JButton submitButton;

    public CourseManagementPanel() {
        setLayout(new BorderLayout());
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        courseIdField = new JTextField(20);
        formPanel.add(courseIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        creditField = new JTextField(20);
        formPanel.add(creditField, gbc);

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
        submitButton = new JButton("Add Course");
        submitButton.addActionListener(e -> addCourse());
        formPanel.add(submitButton, gbc);

        // Add form panel to main panel
        add(formPanel, BorderLayout.CENTER);
    }

    private void addCourse() {
        // TODO: Implement course addition logic
        String courseId = courseIdField.getText();
        String title = titleField.getText();
        String credits = creditField.getText();
        String department = (String) departmentCombo.getSelectedItem();

        if (courseId.isEmpty() || title.isEmpty() || credits.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show success message
        JOptionPane.showMessageDialog(this, 
            "Course added successfully!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Clear fields
        courseIdField.setText("");
        titleField.setText("");
        creditField.setText("");
    }
} 