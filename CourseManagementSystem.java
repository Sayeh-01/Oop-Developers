import java.util.ArrayList;
import java.util.List;

public class CourseManagementSystem {
    private static List<Student> students = new ArrayList<>();
    private static List<Faculty> faculty = new ArrayList<>();
    private static List<AdminStaff> admins = new ArrayList<>();
    private static User currentUser = null;

    public static void main(String[] args) {
        // Initialize some sample data
        initializeSampleData();

        boolean running = true;
        while (running) {
            MenuSystem.clearConsole();
            MenuSystem.displayMainMenu();
            int choice = MenuSystem.getIntegerInput("");

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    running = false;
                    MenuSystem.displayInfoMessage("Thank you for using the Course Management System. Goodbye!");
                    break;
                default:
                    MenuSystem.displayErrorMessage("Invalid option. Please try again.");
            }
        }
    }

    private static void login() {
        String username = MenuSystem.getUserInput("Enter username: ");
        String password = MenuSystem.getUserInput("Enter password: ");

        // Check student login
        for (Student student : students) {
            if (student.getUserName().equals(username) && student.getPassword().equals(password)) {
                currentUser = student;
                handleStudentMenu(student);
                return;
            }
        }

        // Check faculty login
        for (Faculty facultyMember : faculty) {
            if (facultyMember.getUserName().equals(username) && facultyMember.getPassword().equals(password)) {
                currentUser = facultyMember;
                handleFacultyMenu(facultyMember);
                return;
            }
        }

        // Check admin login
        for (AdminStaff admin : admins) {
            if (admin.getUserName().equals(username) && admin.getPassword().equals(password)) {
                currentUser = admin;
                handleAdminMenu(admin);
                return;
            }
        }

        MenuSystem.displayErrorMessage("Invalid username or password.");
    }

    private static void handleStudentMenu(Student student) {
        boolean loggedIn = true;
        while (loggedIn) {
            MenuSystem.clearConsole();
            MenuSystem.displayStudentMenu(student);
            int choice = MenuSystem.getIntegerInput("");

            switch (choice) {
                case 1:
                    student.displayAvailableCourses(AdminStaff.getAvailableCourses());
                    break;
                case 2:
                    student.registerForCourse();
                    break;
                case 3:
                    handleDropCourse(student);
                    break;
                case 4:
                    student.displaySchedule();
                    break;
                case 5:
                    student.viewGrade();
                    break;
                case 6:
                    student.displayAcademicRecords();
                    break;
                case 7:
                    student.viewNotifications();
                    break;
                case 8:
                    loggedIn = false;
                    currentUser = null;
                    MenuSystem.displaySuccessMessage("Successfully logged out.");
                    break;
                default:
                    MenuSystem.displayErrorMessage("Invalid option. Please try again.");
            }
            if (loggedIn) {
                MenuSystem.getUserInput("\nPress Enter to continue...");
            }
        }
    }

    private static void handleFacultyMenu(Faculty faculty) {
        boolean loggedIn = true;
        while (loggedIn) {
            MenuSystem.clearConsole();
            MenuSystem.displayFacultyMenu(faculty);
            int choice = MenuSystem.getIntegerInput("");

            switch (choice) {
                case 1:
                    faculty.viewTeachingSchedule();
                    break;
                case 2:
                    handleManageCourse(faculty);
                    break;
                case 3:
                    handleViewStudentRoster(faculty);
                    break;
                case 4:
                    handleCreateAssignment(faculty);
                    break;
                case 5:
                    handleEnterGrades(faculty);
                    break;
                case 6:
                    faculty.displayWorkloadSummary();
                    break;
                case 7:
                    faculty.viewNotifications();
                    break;
                case 8:
                    loggedIn = false;
                    currentUser = null;
                    MenuSystem.displaySuccessMessage("Successfully logged out.");
                    break;
                default:
                    MenuSystem.displayErrorMessage("Invalid option. Please try again.");
            }
            if (loggedIn) {
                MenuSystem.getUserInput("\nPress Enter to continue...");
            }
        }
    }

    private static void handleAdminMenu(AdminStaff admin) {
        boolean loggedIn = true;
        while (loggedIn) {
            MenuSystem.clearConsole();
            MenuSystem.displayAdminMenu(admin);
            int choice = MenuSystem.getIntegerInput("");

            switch (choice) {
                case 1:
                    handleRegisterStudent(admin);
                    break;
                case 2:
                    handleCreateCourse(admin);
                    break;
                case 3:
                    admin.assignFacultyToCourse();
                    break;
                case 4:
                    admin.generateReports();
                    break;
                case 5:
                    displayAllCourses();
                    break;
                case 6:
                    displayAllFaculty();
                    break;
                case 7:
                    displayAllStudents();
                    break;
                case 8:
                    loggedIn = false;
                    currentUser = null;
                    MenuSystem.displaySuccessMessage("Successfully logged out.");
                    break;
                default:
                    MenuSystem.displayErrorMessage("Invalid option. Please try again.");
            }
            if (loggedIn) {
                MenuSystem.getUserInput("\nPress Enter to continue...");
            }
        }
    }

    private static void handleDropCourse(Student student) {
        List<CourseGrade> enrolledCourses = student.getEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            MenuSystem.displayErrorMessage("You are not enrolled in any courses.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"#", "Course ID", "Title", "Instructor"});
        for (int i = 0; i < enrolledCourses.size(); i++) {
            CourseGrade course = enrolledCourses.get(i);
            MenuSystem.displayTableRow(new String[]{
                String.valueOf(i + 1),
                course.getCourseId(),
                course.getTitle(),
                course.getInstructor()
            });
        }
        MenuSystem.displayTableFooter();

        int choice = MenuSystem.getIntegerInput("Select a course to drop (0 to cancel): ");
        if (choice == 0) return;
        if (choice < 1 || choice > enrolledCourses.size()) {
            MenuSystem.displayErrorMessage("Invalid course selection.");
            return;
        }

        CourseGrade selectedCourse = enrolledCourses.get(choice - 1);
        if (student.dropCourse(selectedCourse)) {
            MenuSystem.displaySuccessMessage("Successfully dropped course: " + selectedCourse.getTitle());
        }
    }

    private static void handleManageCourse(Faculty faculty) {
        List<CourseGrade> teachingCourses = faculty.getTeachingCourses();
        if (teachingCourses.isEmpty()) {
            MenuSystem.displayErrorMessage("You are not teaching any courses.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"#", "Course ID", "Title", "Schedule"});
        for (int i = 0; i < teachingCourses.size(); i++) {
            CourseGrade course = teachingCourses.get(i);
            MenuSystem.displayTableRow(new String[]{
                String.valueOf(i + 1),
                course.getCourseId(),
                course.getTitle(),
                course.getDays() + " " + course.getTimes()
            });
        }
        MenuSystem.displayTableFooter();

        int choice = MenuSystem.getIntegerInput("Select a course to manage (0 to cancel): ");
        if (choice == 0) return;
        if (choice < 1 || choice > teachingCourses.size()) {
            MenuSystem.displayErrorMessage("Invalid course selection.");
            return;
        }

        faculty.manageCourse(teachingCourses.get(choice - 1));
    }

    private static void handleViewStudentRoster(Faculty faculty) {
        List<CourseGrade> teachingCourses = faculty.getTeachingCourses();
        if (teachingCourses.isEmpty()) {
            MenuSystem.displayErrorMessage("You are not teaching any courses.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"#", "Course ID", "Title", "Enrolled Students"});
        for (int i = 0; i < teachingCourses.size(); i++) {
            CourseGrade course = teachingCourses.get(i);
            MenuSystem.displayTableRow(new String[]{
                String.valueOf(i + 1),
                course.getCourseId(),
                course.getTitle(),
                String.valueOf(course.getEnrolledStudents().size())
            });
        }
        MenuSystem.displayTableFooter();

        int choice = MenuSystem.getIntegerInput("Select a course to view roster (0 to cancel): ");
        if (choice == 0) return;
        if (choice < 1 || choice > teachingCourses.size()) {
            MenuSystem.displayErrorMessage("Invalid course selection.");
            return;
        }

        faculty.viewStudentRoster(teachingCourses.get(choice - 1));
    }

    private static void handleCreateAssignment(Faculty faculty) {
        List<CourseGrade> teachingCourses = faculty.getTeachingCourses();
        if (teachingCourses.isEmpty()) {
            MenuSystem.displayErrorMessage("You are not teaching any courses.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"#", "Course ID", "Title"});
        for (int i = 0; i < teachingCourses.size(); i++) {
            CourseGrade course = teachingCourses.get(i);
            MenuSystem.displayTableRow(new String[]{
                String.valueOf(i + 1),
                course.getCourseId(),
                course.getTitle()
            });
        }
        MenuSystem.displayTableFooter();

        int choice = MenuSystem.getIntegerInput("Select a course to create assignment (0 to cancel): ");
        if (choice == 0) return;
        if (choice < 1 || choice > teachingCourses.size()) {
            MenuSystem.displayErrorMessage("Invalid course selection.");
            return;
        }

        CourseGrade selectedCourse = teachingCourses.get(choice - 1);
        String assignmentName = MenuSystem.getUserInput("Enter assignment name: ");
        String description = MenuSystem.getUserInput("Enter assignment description: ");
        double maxScore = MenuSystem.getDoubleInput("Enter maximum score: ");
        selectedCourse.setGrade(maxScore);
        MenuSystem.displaySuccessMessage("Assignment created successfully.");
    }

    private static void handleEnterGrades(Faculty faculty) {
        List<CourseGrade> teachingCourses = faculty.getTeachingCourses();
        if (teachingCourses.isEmpty()) {
            MenuSystem.displayErrorMessage("You are not teaching any courses.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"#", "Course ID", "Title"});
        for (int i = 0; i < teachingCourses.size(); i++) {
            CourseGrade course = teachingCourses.get(i);
            MenuSystem.displayTableRow(new String[]{
                String.valueOf(i + 1),
                course.getCourseId(),
                course.getTitle()
            });
        }
        MenuSystem.displayTableFooter();

        int choice = MenuSystem.getIntegerInput("Select a course to enter grades (0 to cancel): ");
        if (choice == 0) return;
        if (choice < 1 || choice > teachingCourses.size()) {
            MenuSystem.displayErrorMessage("Invalid course selection.");
            return;
        }

        CourseGrade selectedCourse = teachingCourses.get(choice - 1);
        List<Student> enrolledStudents = selectedCourse.getEnrolledStudents();
        if (enrolledStudents.isEmpty()) {
            MenuSystem.displayErrorMessage("No students enrolled in this course.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"#", "Student ID", "Name"});
        for (int i = 0; i < enrolledStudents.size(); i++) {
            Student student = enrolledStudents.get(i);
            MenuSystem.displayTableRow(new String[]{
                String.valueOf(i + 1),
                String.valueOf(student.getStudentID()),
                student.getName()
            });
        }
        MenuSystem.displayTableFooter();

        int studentChoice = MenuSystem.getIntegerInput("Select a student to enter grade (0 to cancel): ");
        if (studentChoice == 0) return;
        if (studentChoice < 1 || studentChoice > enrolledStudents.size()) {
            MenuSystem.displayErrorMessage("Invalid student selection.");
            return;
        }

        Student selectedStudent = enrolledStudents.get(studentChoice - 1);
        double grade = MenuSystem.getDoubleInput("Enter grade (0-100): ");
        selectedCourse.setGrade(grade);
        MenuSystem.displaySuccessMessage("Grade entered successfully.");
    }

    private static void handleRegisterStudent(AdminStaff admin) {
        String name = MenuSystem.getUserInput("Enter student name: ");
        String email = MenuSystem.getUserInput("Enter student email: ");
        String contactInfo = MenuSystem.getUserInput("Enter contact information: ");
        int studentID = MenuSystem.getIntegerInput("Enter student ID: ");
        String academicStatus = MenuSystem.getUserInput("Enter academic status: ");
        String admissionDate = MenuSystem.getUserInput("Enter admission date (MM/DD/YYYY): ");

        Student newStudent = new Student(academicStatus, admissionDate, studentID, contactInfo, email, name, String.valueOf(studentID));
        admin.registerStudent(newStudent);
        students.add(newStudent);
        MenuSystem.displaySuccessMessage("Student registered successfully.");
    }

    private static void handleCreateCourse(AdminStaff admin) {
        String courseId = MenuSystem.getUserInput("Enter course ID: ");
        String title = MenuSystem.getUserInput("Enter course title: ");
        int creditHours = MenuSystem.getIntegerInput("Enter credit hours: ");
        String days = MenuSystem.getUserInput("Enter days (e.g., Monday,Wednesday): ");
        String times = MenuSystem.getUserInput("Enter times (e.g., 10:00-11:30): ");
        String location = MenuSystem.getUserInput("Enter location: ");
        int maxSeats = MenuSystem.getIntegerInput("Enter maximum seats: ");
        String department = MenuSystem.getUserInput("Enter department: ");

        CourseGrade newCourse = new CourseGrade(courseId, title, creditHours, days, times, location, "", maxSeats, department);
        admin.createCourse(newCourse);
        MenuSystem.displaySuccessMessage("Course created successfully.");
    }

    private static void displayAllCourses() {
        List<CourseGrade> courses = AdminStaff.getAvailableCourses();
        if (courses.isEmpty()) {
            MenuSystem.displayErrorMessage("No courses available.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"Course ID", "Title", "Department", "Instructor", "Schedule"});
        for (CourseGrade course : courses) {
            MenuSystem.displayTableRow(new String[]{
                course.getCourseId(),
                course.getTitle(),
                course.getDepartment(),
                course.getInstructor(),
                course.getDays() + " " + course.getTimes()
            });
        }
        MenuSystem.displayTableFooter();
    }

    private static void displayAllFaculty() {
        List<Faculty> allFaculty = AdminStaff.getAllFaculty();
        if (allFaculty.isEmpty()) {
            MenuSystem.displayErrorMessage("No faculty members available.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"Name", "Department", "Position", "Courses Teaching"});
        for (Faculty faculty : allFaculty) {
            MenuSystem.displayTableRow(new String[]{
                faculty.getName(),
                faculty.getDepartment(),
                faculty.getPosition(),
                String.valueOf(faculty.getTeachingCourses().size())
            });
        }
        MenuSystem.displayTableFooter();
    }

    private static void displayAllStudents() {
        List<Student> allStudents = AdminStaff.getRegisteredStudents();
        if (allStudents.isEmpty()) {
            MenuSystem.displayErrorMessage("No students registered.");
            return;
        }

        MenuSystem.displayTableHeader(new String[]{"Student ID", "Name", "Email", "Academic Status"});
        for (Student student : allStudents) {
            MenuSystem.displayTableRow(new String[]{
                String.valueOf(student.getStudentID()),
                student.getName(),
                student.getEmail(),
                student.getAcademicStatus()
            });
        }
        MenuSystem.displayTableFooter();
    }

    private static void initializeSampleData() {
        // Create sample admin staff
        AdminStaff admin = new AdminStaff(
            "ADM001",
            "Administration",
            "System Administrator",
            "admin123",
            "Admin User",
            "admin@university.edu",
            "123-456-7890"
        );
        admins.add(admin);

        // Initialize sample faculty
        Faculty faculty1 = new Faculty("FAC001", "Computer Science", "Professor", "123-456-7891", "prof1@university.edu", "John Smith", "prof1");
        Faculty faculty2 = new Faculty("FAC002", "Mathematics", "Associate Professor", "123-456-7892", "prof2@university.edu", "Jane Doe", "prof2");
        faculty.add(faculty1);
        faculty.add(faculty2);

        // Initialize sample students
        Student student1 = new Student("Freshman", "01/01/2023", 1001, "123-456-7893", "student1@university.edu", "Alice Johnson", "student1");
        Student student2 = new Student("Sophomore", "01/01/2022", 1002, "123-456-7894", "student2@university.edu", "Bob Wilson", "student2");
        students.add(student1);
        students.add(student2);

        // Initialize sample courses
        CourseGrade course1 = new CourseGrade("CS101", "Introduction to Programming", 3, "Monday,Wednesday", "10:00-11:30", "Room 101", "", 30, "Computer Science");
        CourseGrade course2 = new CourseGrade("MATH101", "Calculus I", 4, "Tuesday,Thursday", "13:00-14:30", "Room 201", "", 25, "Mathematics");
        admin.createCourse(course1);
        admin.createCourse(course2);
    }
} 