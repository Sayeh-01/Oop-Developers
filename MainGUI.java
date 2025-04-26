import java.awt.*;
import javax.swing.*;

public class MainGUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JMenuBar menuBar;
    private JMenu fileMenu, studentMenu, courseMenu, adminMenu;
    private JMenuItem exitItem, registerStudentItem, viewStudentsItem, 
                     addCourseItem, viewCoursesItem, manageCoursesItem;

    public MainGUI() {
        setTitle("Course Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create menu bar
        createMenuBar();

        // Create main panel with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels
        mainPanel.add(new DashboardPanel(), "dashboard");
        mainPanel.add(new StudentRegistrationPanel(), "studentRegistration");
        mainPanel.add(new CourseManagementPanel(), "courseManagement");
        mainPanel.add(new StudentListPanel(), "studentList");
        mainPanel.add(new CourseListPanel(), "courseList");

        add(mainPanel);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        // File Menu
        fileMenu = new JMenu("File");
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Student Menu
        studentMenu = new JMenu("Students");
        registerStudentItem = new JMenuItem("Register Student");
        registerStudentItem.addActionListener(e -> cardLayout.show(mainPanel, "studentRegistration"));
        viewStudentsItem = new JMenuItem("View Students");
        viewStudentsItem.addActionListener(e -> cardLayout.show(mainPanel, "studentList"));
        studentMenu.add(registerStudentItem);
        studentMenu.add(viewStudentsItem);

        // Course Menu
        courseMenu = new JMenu("Courses");
        addCourseItem = new JMenuItem("Add Course");
        addCourseItem.addActionListener(e -> cardLayout.show(mainPanel, "courseManagement"));
        viewCoursesItem = new JMenuItem("View Courses");
        viewCoursesItem.addActionListener(e -> cardLayout.show(mainPanel, "courseList"));
        manageCoursesItem = new JMenuItem("Manage Courses");
        manageCoursesItem.addActionListener(e -> cardLayout.show(mainPanel, "courseManagement"));
        courseMenu.add(addCourseItem);
        courseMenu.add(viewCoursesItem);
        courseMenu.add(manageCoursesItem);

        // Admin Menu
        adminMenu = new JMenu("Admin");
        // Add admin menu items here

        menuBar.add(fileMenu);
        menuBar.add(studentMenu);
        menuBar.add(courseMenu);
        menuBar.add(adminMenu);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }
} 