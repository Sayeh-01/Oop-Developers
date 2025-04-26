import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CourseListPanel extends JPanel {
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public CourseListPanel() {
        setLayout(new BorderLayout());

        // Create table model
        String[] columns = {"Course ID", "Title", "Credits", "Department", "Instructor", "Enrolled"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        courseTable = new JTable(tableModel);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(courseTable);

        // Add sample data (replace with actual data later)
        addSampleData();

        // Add components to panel
        add(new JLabel("Course List", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addSampleData() {
        // Add sample data (replace with actual data later)
        tableModel.addRow(new Object[]{"CS101", "Introduction to Programming", "3", "Computer Science", "Dr. Smith", "30/40"});
        tableModel.addRow(new Object[]{"MATH201", "Calculus II", "4", "Mathematics", "Dr. Johnson", "25/30"});
        tableModel.addRow(new Object[]{"PHYS101", "Physics I", "4", "Physics", "Dr. Brown", "20/25"});
    }
} 