import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminStaff extends User implements PayrollSystem {
    protected String staffID;
    protected String department;
    protected String role;

    private static ArrayList<Student> registeredStudents = new ArrayList<>();
    private static ArrayList<CourseGrade> availableCourses = new ArrayList<>();

    // Static list to track all faculty members
    private static List<Faculty> allFaculty = new ArrayList<>();

    public AdminStaff(String staffID, String department, String role, String userId, String name, String email, String contactInfo) {
        super(contactInfo, email, name, userId);
        this.staffID = staffID;
        this.department = department;
        this.role = role;
    }

    @Override
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (getUserName().equals(username) && getPassword().equals(password)) {
            setCurrentUser(username);
            System.out.println("Login successful! Welcome, " + getName());
            return true;
        }
        System.out.println("Invalid username or password!");
        return false;
    }

    public void registerStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (registeredStudents.contains(student)) {
            System.out.println("Student " + student.getName() + " is already registered.");
            return;
        }

        registeredStudents.add(student);
        System.out.println("Student " + student.getName() + " has been registered successfully.");
    }

    public void createCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (availableCourses.contains(course)) {
            System.out.println("Course " + course.getTitle() + " already exists.");
            return;
        }

        availableCourses.add(course);
        System.out.println("Course " + course.getTitle() + " has been created successfully.");
    }

    public void assignFacultyToCourse() {
        Scanner scanner = new Scanner(System.in);
        
        // Display available courses
        System.out.println("\n=== Available Courses for Assignment ===");
        System.out.println("----------------------------------------");
        System.out.printf("%-10s %-30s %-15s %-10s\n", 
            "Course ID", "Title", "Department", "Current Instructor");
        System.out.println("----------------------------------------");

        List<CourseGrade> courses = getAvailableCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses available for assignment.");
            return;
        }

        // Display courses with numbers for selection
        int courseCount = 0;
        for (CourseGrade course : courses) {
            courseCount++;
            System.out.printf("%d. %-10s %-30s %-15s %-10s\n",
                courseCount,
                course.getCourseId(),
                course.getTitle(),
                course.getDepartment(),
                course.getInstructor() == null ? "Unassigned" : course.getInstructor());
        }

        // Select course
        System.out.print("\nEnter the number of the course to assign (or 0 to cancel): ");
        int courseChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (courseChoice == 0) {
            System.out.println("Assignment cancelled.");
            return;
        }

        if (courseChoice < 1 || courseChoice > courseCount) {
            System.out.println("Invalid course number.");
            return;
        }

        CourseGrade selectedCourse = courses.get(courseChoice - 1);

        // Display available faculty
        System.out.println("\n=== Available Faculty Members ===");
        System.out.println("----------------------------------------");
        System.out.printf("%-20s %-15s %-10s %-10s\n", 
            "Name", "Department", "Position", "Courses Teaching");
        System.out.println("----------------------------------------");

        List<Faculty> facultyList = getAllFaculty();
        if (facultyList.isEmpty()) {
            System.out.println("No faculty members available.");
            return;
        }

        // Display faculty with numbers for selection
        int facultyCount = 0;
        for (Faculty faculty : facultyList) {
            // Only show faculty from the same department
            if (faculty.getDepartment().equals(selectedCourse.getDepartment())) {
                facultyCount++;
                System.out.printf("%d. %-20s %-15s %-10s %-10d\n",
                    facultyCount,
                    faculty.getName(),
                    faculty.getDepartment(),
                    faculty.getPosition(),
                    faculty.getTeachingCourses().size());
            }
        }

        if (facultyCount == 0) {
            System.out.println("No faculty members available in the " + selectedCourse.getDepartment() + " department.");
            return;
        }

        // Select faculty
        System.out.print("\nEnter the number of the faculty member to assign (or 0 to cancel): ");
        int facultyChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (facultyChoice == 0) {
            System.out.println("Assignment cancelled.");
            return;
        }

        if (facultyChoice < 1 || facultyChoice > facultyCount) {
            System.out.println("Invalid faculty number.");
            return;
        }

        // Find the selected faculty member
        int currentCount = 0;
        Faculty selectedFaculty = null;
        for (Faculty faculty : facultyList) {
            if (faculty.getDepartment().equals(selectedCourse.getDepartment())) {
                currentCount++;
                if (currentCount == facultyChoice) {
                    selectedFaculty = faculty;
                    break;
                }
            }
        }

        if (selectedFaculty != null) {
            // Check for schedule conflicts
            if (selectedFaculty.hasScheduleConflict(selectedCourse)) {
                System.out.println("Cannot assign faculty: Schedule conflict detected.");
                return;
            }

            // Assign faculty to course
            selectedCourse.setInstructorName(selectedFaculty.getName());
            selectedFaculty.addCourse(selectedCourse);
            System.out.println("Successfully assigned " + selectedFaculty.getName() + 
                             " to course " + selectedCourse.getTitle());
        }
    }

    public void generateReports() {
        System.out.println("\n--- Administrative Reports ---");
        System.out.println("1. Student Registration Report");
        System.out.println("2. Course Offering Report");
        System.out.println("3. Faculty Assignment Report");
        System.out.println("------------------------------");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose a report type (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                generateStudentReport();
                break;
            case 2:
                generateCourseReport();
                break;
            case 3:
                generateFacultyReport();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void generateStudentReport() {
        System.out.println("\n--- Student Registration Report ---");
        System.out.println("Total Registered Students: " + registeredStudents.size());
        for (Student student : registeredStudents) {
            System.out.println("Student ID: " + student.getStudentID());
            System.out.println("Name: " + student.getName());
            System.out.println("Academic Status: " + student.getAcademicStatus());
            System.out.println("Admission Date: " + student.getAdmissionDate());
            System.out.println("------------------------------");
        }
    }

    private void generateCourseReport() {
        System.out.println("\n--- Course Offering Report ---");
        System.out.println("Total Available Courses: " + availableCourses.size());
        for (CourseGrade course : availableCourses) {
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Credit Hours: " + course.getCreditHours());
            System.out.println("Instructor: " + course.getInstructor());
            System.out.println("Enrolled Students: " + course.getEnrolledStudents().size());
            System.out.println("------------------------------");
        }
    }

    private void generateFacultyReport() {
        System.out.println("\n--- Faculty Assignment Report ---");
        for (CourseGrade course : availableCourses) {
            System.out.println("Course: " + course.getTitle());
            System.out.println("Instructor: " + course.getInstructor());
            System.out.println("Schedule: " + course.getScheduele());
            System.out.println("------------------------------");
        }
    }

    // Getters and Setters
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static ArrayList<Student> getRegisteredStudents() {
        return new ArrayList<>(registeredStudents);
    }

    public static ArrayList<CourseGrade> getAvailableCourses() {
        return new ArrayList<>(availableCourses);
    }

    public static List<Faculty> getAllFaculty() {
        return new ArrayList<>(allFaculty);
    }

    // Method to add a faculty member to the system
    public static void addFaculty(Faculty faculty) {
        if (faculty != null && !allFaculty.contains(faculty)) {
            allFaculty.add(faculty);
        }
    }
    private AdminStaff admin;
    private double hourlyRate;
    private double hoursWorked;
    private double overtimeRate;

   

    public void setHoursWorked(double hours) {
        this.hoursWorked = hours;
    }

    @Override
    public double calculatePayroll() {
        double regularHours = Math.min(hoursWorked, 40.0);
        double overtimeHours = Math.max(0.0, hoursWorked - 40.0);
        
        return (regularHours * hourlyRate) + (overtimeHours * hourlyRate * overtimeRate);
    }

    @Override
    public String generatePayrollReport() {
        double regularPay = Math.min(hoursWorked, 40.0) * hourlyRate;
        double overtimePay = Math.max(0.0, hoursWorked - 40.0) * hourlyRate * overtimeRate;
        
        return String.format("Admin Staff Payroll Report\n" +
                           "Name: %s\n" +
                           "Hourly Rate: $%.2f\n" +
                           "Hours Worked: %.2f\n" +
                           "Regular Pay: $%.2f\n" +
                           "Overtime Pay: $%.2f\n" +
                           "Total: $%.2f",
                           admin.getName(),
                           hourlyRate,
                           hoursWorked,
                           regularPay,
                           overtimePay,
                           calculatePayroll());
    }
} 

