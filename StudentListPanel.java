import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentListPanel extends JPanel {
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentListPanel() {
        setLayout(new BorderLayout());

        // Create table model
        String[] columns = {"Student ID", "Name", "Email", "Department", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Add sample data (replace with actual data later)
        addSampleData();

        // Add components to panel
        add(new JLabel("Student List", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addSampleData() {
        // Add sample data (replace with actual data later)
        tableModel.addRow(new Object[]{"S001", "John Doe", "john@example.com", "Computer Science", "Active"});
        tableModel.addRow(new Object[]{"S002", "Jane Smith", "jane@example.com", "Mathematics", "Active"});
        tableModel.addRow(new Object[]{"S003", "Bob Johnson", "bob@example.com", "Physics", "Inactive"});
    }
} 