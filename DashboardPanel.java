import java.awt.*;
import javax.swing.*;

public class DashboardPanel extends JPanel {
    private JLabel totalStudentsLabel;
    private JLabel totalCoursesLabel;
    private JLabel activeFacultyLabel;
    private JLabel currentSemesterLabel;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        
        // Create header
        JPanel header = new JPanel();
        header.add(new JLabel("Dashboard", SwingConstants.CENTER));
        add(header, BorderLayout.NORTH);

        // Create main content area
        JPanel content = new JPanel(new GridLayout(2, 2, 10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add summary panels
        content.add(createSummaryPanel("Total Students", "0"));
        content.add(createSummaryPanel("Total Courses", "0"));
        content.add(createSummaryPanel("Active Faculty", "0"));
        content.add(createSummaryPanel("Current Semester", "Fall 2024"));

        add(content, BorderLayout.CENTER);
    }

    private JPanel createSummaryPanel(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Store reference to value label for updating
        switch (title) {
            case "Total Students":
                totalStudentsLabel = valueLabel;
                break;
            case "Total Courses":
                totalCoursesLabel = valueLabel;
                break;
            case "Active Faculty":
                activeFacultyLabel = valueLabel;
                break;
            case "Current Semester":
                currentSemesterLabel = valueLabel;
                break;
        }
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }

    public void updateStatistics(long totalStudents, int totalCourses, long activeFaculty, String currentSemester) {
        totalStudentsLabel.setText(String.valueOf(totalStudents));
        totalCoursesLabel.setText(String.valueOf(totalCourses));
        activeFacultyLabel.setText(String.valueOf(activeFaculty));
        currentSemesterLabel.setText(currentSemester);
    }
} 