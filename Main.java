import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the university
        University university = new University("Test University");
        
        // Create departments
        university.createDepartment("Computer Science", "CS", "Computer Science Department");
        university.createDepartment("Mathematics", "MATH", "Mathematics Department");
        university.createDepartment("Physics", "PHYS", "Physics Department");
        
        // Create users
        Student student1 = new Student("Active", "2024-01-01", 1001, "123-456-7890", "student1@university.edu", "John Doe", "1001");
        Student student2 = new Student("Active", "2024-01-01", 1002, "123-456-7891", "student2@university.edu", "Jane Smith", "1002");
        
        Faculty faculty1 = new Faculty("F001", "Computer Science", "Professor", "123-456-7892", "faculty1@university.edu", "Dr. Smith", "F001");
        Faculty faculty2 = new Faculty("F002", "Mathematics", "Associate Professor", "123-456-7893", "faculty2@university.edu", "Dr. Johnson", "F002");
        
        AdminStaff admin1 = new AdminStaff("A001", "Administration", "Registrar", "A001", "Admin User", "admin@university.edu", "123-456-7894");
        
        // Register users
        university.registerStudent(student1, "CS");
        university.registerStudent(student2, "MATH");
        university.hireFaculty(faculty1, "CS");
        university.hireFaculty(faculty2, "MATH");
        university.addAdminStaff(admin1);
        
        // Create courses
        CourseGrade course1 = new CourseGrade("CS101", "Introduction to Programming", 3, "Monday,Wednesday", "10:00-11:30", "Room 101", "Dr. Smith", 40, "CS");
        CourseGrade course2 = new CourseGrade("MATH201", "Calculus II", 4, "Tuesday,Thursday", "13:00-14:30", "Room 201", "Dr. Johnson", 30, "MATH");
        CourseGrade course3 = new CourseGrade("PHYS101", "Physics I", 4, "Monday,Wednesday", "14:00-15:30", "Room 301", "Dr. Brown", 25, "PHYS");
        
        // Offer courses
        university.offerCourse(course1, "CS", faculty1);
        university.offerCourse(course2, "MATH", faculty2);
        university.offerCourse(course3, "PHYS", faculty1);
        
        // Enroll students in courses
        university.enrollStudentInCourse(student1, course1);
        university.enrollStudentInCourse(student1, course2);
        university.enrollStudentInCourse(student2, course2);
        university.enrollStudentInCourse(student2, course3);
        
        // Test grading system
        List<AssignmentGrade> assignments = new ArrayList<>();
        assignments.add(new AssignmentGrade("Lab1", 85.0, 0.2));
        assignments.add(new AssignmentGrade("Lab2", 90.0, 0.2));
        assignments.add(new AssignmentGrade("Midterm", 75.0, 0.3));
        assignments.add(new AssignmentGrade("Final", 80.0, 0.3));
        
        // Create grading systems
        GradingSystem gradingStrategy = new GradingSystem() {
            @Override
            public double calculateGrade(List<AssignmentGrade> assignments) {
                double total = 0.0;
                for (AssignmentGrade ag : assignments) {
                    total += ag.getGrade() * ag.getWeight();
                }
                return total;
            }
            
            @Override
            public String getLetterGrade(double numericGrade) {
                if (numericGrade >= 90) return "A";
                if (numericGrade >= 80) return "B";
                if (numericGrade >= 70) return "C";
                if (numericGrade >= 60) return "D";
                return "F";
            }
        };
        
        RegularCourseGrading regularGrading = new RegularCourseGrading(course1, gradingStrategy, 0.3, 0.3, 0.4);
        
        // Calculate final grade
        double finalGrade = regularGrading.calculateFinalGrade(assignments);
        System.out.println("Final Grade: " + finalGrade);
        System.out.println("Letter Grade: " + regularGrading.getGradingStrategy().getLetterGrade(finalGrade));
        
        // Test payroll system
        FacultyPayroll facultyPayroll = new FacultyPayroll(faculty1, 5000.0);
        double payroll = facultyPayroll.calculatePayroll();
        System.out.println("Faculty Payroll: $" + payroll);
        System.out.println(facultyPayroll.generatePayrollReport());
        
        // Launch GUI
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
            
            // Update dashboard with actual data
            DashboardPanel dashboard = new DashboardPanel();
            dashboard.updateStatistics(
                university.getUsers().stream().filter(u -> u instanceof Student).count(),
                university.getCourses().size(),
                university.getUsers().stream().filter(u -> u instanceof Faculty).count(),
                "Spring 2024"
            );
        });
    }
}