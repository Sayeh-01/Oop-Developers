import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Faculty extends User {
    private String facultyID;
    private String department;
    private String position;
    private ArrayList<CourseGrade> teachingCourses;
    private String expertise;
    private String officeHours;
    private String officeLocation;
    private Map<CourseGrade, List<Assignment>> courseAssignments;

    // Constants for workload limits
    private static final int MAX_COURSES_PER_SEMESTER = 4;
    private static final int MAX_CREDIT_HOURS_PER_SEMESTER = 12;
    private static final int MIN_OFFICE_HOURS_PER_WEEK = 5;
    private static final int MAX_OFFICE_HOURS_PER_WEEK = 20;

    // Add notification-related fields
    private List<String> notifications;
    private boolean emailNotificationsEnabled;
    private String notificationEmail;

    public Faculty(String facultyID, String department, String position, String contactInfo, String email, String name, String userId) {
        super(contactInfo, email, name, userId);
        this.facultyID = facultyID;
        this.department = department;
        this.position = position;
        this.teachingCourses = new ArrayList<>();
        this.courseAssignments = new HashMap<>();
        this.notifications = new ArrayList<>();
        this.emailNotificationsEnabled = true;
        this.notificationEmail = email;
    }

    @Override
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        // Check if username and password match
        if (getUserName().equals(username) && getPassword().equals(password)) {
            setCurrentUser(username);
            System.out.println("Login successful! Welcome, " + getName());
            return true;
        }
        System.out.println("Invalid username or password!");
        return false;
    }

    public void assignGrade(Student student, CourseGrade course, double grade) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and course cannot be null");
        }

        if (!teachingCourses.contains(course)) {
            System.out.println("You are not teaching this course: " + course.getTitle());
            return;
        }

        if (!course.getEnrolledStudents().contains(student)) {
            System.out.println("Student " + student.getName() + " is not enrolled in this course");
            return;
        }

        if (grade < 0.0 || grade > 100.0) {
            System.out.println("Grade must be between 0.0 and 100.0");
            return;
        }

        course.setGrade(grade);
        System.out.println("Grade " + grade + " has been assigned to " + student.getName() + 
                          " for course " + course.getTitle());
    }

    public void addCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (teachingCourses.contains(course)) {
            System.out.println("You are already teaching this course: " + course.getTitle());
            return;
        }

        // Validate if the course can be added
        if (!canAddCourse(course)) {
            return;
        }

        teachingCourses.add(course);
        course.setInstructorName(getName());
        
        // Create and send notification
        String notificationMessage = String.format(
            "You have been assigned to teach %s (Course ID: %s)\n" +
            "Schedule: %s %s\n" +
            "Location: %s\n" +
            "Credit Hours: %d",
            course.getTitle(),
            course.getCourseId(),
            course.getDays(),
            course.getTimes(),
            course.getLocation(),
            course.getCreditHours()
        );
        
        addNotification(notificationMessage);
        System.out.println("Course " + course.getTitle() + " has been added to your teaching schedule");
    }

    public void removeCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (!teachingCourses.contains(course)) {
            System.out.println("You are not teaching this course: " + course.getTitle());
            return;
        }

        teachingCourses.remove(course);
        course.setInstructorName("");
        
        // Create and send notification
        String notificationMessage = String.format(
            "You have been removed from teaching %s (Course ID: %s)",
            course.getTitle(),
            course.getCourseId()
        );
        
        addNotification(notificationMessage);
        System.out.println("Course " + course.getTitle() + " has been removed from your teaching schedule");
    }

    public void viewTeachingSchedule() {
        if (teachingCourses.isEmpty()) {
            System.out.println("You are not currently teaching any courses.");
            return;
        }

        System.out.println("\nYour Teaching Schedule:");
        System.out.println("----------------------------------------");
        for (CourseGrade course : teachingCourses) {
            System.out.println("Course: " + course.getTitle());
            System.out.println("Schedule: " + course.getScheduele());
            System.out.println("Enrolled Students: " + course.getEnrolledStudents().size());
            System.out.println("----------------------------------------");
        }
    }

    public void viewStudentRoster(CourseGrade course) {
        if (teachingCourses.contains(course)) {
            System.out.println("\n--- Student Roster for " + course.getTitle() + " ---");
            if (course.getEnrolledStudents().isEmpty()) {
                System.out.println("No students enrolled in this course.");
            } else {
                for (Student student : course.getEnrolledStudents()) {
                    System.out.println("Student: " + student.getName() + " (ID: " + student.getStudentID() + ")");
                }
            }
        } else {
            System.out.println("You are not teaching this course.");
        }
    }

    public void manageCourse(CourseGrade course) {
        if (!teachingCourses.contains(course)) {
            System.out.println("You are not teaching this course.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean continueManaging = true;

        while (continueManaging) {
            System.out.println("\n=== Managing Course: " + course.getTitle() + " ===");
            System.out.println("1. View Course Details");
            System.out.println("2. View Student Roster");
            System.out.println("3. Add Assignment");
            System.out.println("4. Enter Grades");
            System.out.println("5. Update Course Information");
            System.out.println("6. Return to Previous Menu");
            System.out.print("Select an option (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayCourseDetails(course);
                    break;
                case 2:
                    viewStudentRoster(course);
                    break;
                case 3:
                    addAssignmentToCourse(course);
                    break;
                case 4:
                    enterGrades(course);
                    break;
                case 5:
                    updateCourseInformation(course);
                    break;
                case 6:
                    continueManaging = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void displayDashboard() {
        System.out.println("\n--- Faculty Dashboard ---");
        System.out.println("Welcome, Professor/Dr. " + getName() + " (" + getFacultyID() + ")");
        System.out.println("Department: " + department);
        System.out.println("Position: " + position);
        System.out.println("Expertise: " + expertise);
        System.out.println("Courses Teaching: " + teachingCourses.stream().map(CourseGrade::getTitle).toList());
        System.out.println("-------------------------");
    }

    public void createAssignment(CourseGrade course, String assignmentId, String title, String type, double maxPoints, String dueDate, String description) {
        if (!teachingCourses.contains(course)) {
            throw new IllegalArgumentException("You are not teaching this course");
        }

        Assignment newAssignment = new Assignment(assignmentId, title, type, maxPoints, dueDate, description);
        courseAssignments.computeIfAbsent(course, k -> new ArrayList<>()).add(newAssignment);
        System.out.println("Assignment created successfully: " + title);
    }

    public void gradeAssignment(CourseGrade course, String assignmentId, Student student, double points) {
        if (!teachingCourses.contains(course)) {
            throw new IllegalArgumentException("You are not teaching this course");
        }

        List<Assignment> assignments = courseAssignments.get(course);
        if (assignments == null) {
            throw new IllegalArgumentException("No assignments found for this course");
        }

        for (Assignment assignment : assignments) {
            if (assignment.getAssignmentId().equals(assignmentId)) {
                assignment.gradeStudent(student, points);
                System.out.println("Grade assigned successfully for " + student.getName());
                return;
            }
        }

        throw new IllegalArgumentException("Assignment not found");
    }

    public void viewAssignmentGrades(CourseGrade course, String assignmentId) {
        if (!teachingCourses.contains(course)) {
            throw new IllegalArgumentException("You are not teaching this course");
        }

        List<Assignment> assignments = courseAssignments.get(course);
        if (assignments == null) {
            throw new IllegalArgumentException("No assignments found for this course");
        }

        for (Assignment assignment : assignments) {
            if (assignment.getAssignmentId().equals(assignmentId)) {
                System.out.println("\n=== Assignment Grades ===");
                System.out.println(assignment);
                System.out.println("\nStudent Grades:");
                System.out.println("----------------------------------------");
                System.out.printf("%-20s %-10s %-10s\n", "Student Name", "Points", "Percentage");
                System.out.println("----------------------------------------");

                for (Map.Entry<Student, Double> entry : assignment.getStudentGrades().entrySet()) {
                    Student student = entry.getKey();
                    double points = entry.getValue();
                    double percentage = (points / assignment.getMaxPoints()) * 100;
                    System.out.printf("%-20s %-10.2f %-10.2f%%\n",
                        student.getName(), points, percentage);
                }
                return;
            }
        }

        throw new IllegalArgumentException("Assignment not found");
    }

    public List<Assignment> getCourseAssignments(CourseGrade course) {
        if (!teachingCourses.contains(course)) {
            throw new IllegalArgumentException("You are not teaching this course");
        }
        return new ArrayList<>(courseAssignments.getOrDefault(course, new ArrayList<>()));
    }

    public void displayCoursesAndFaculty() {
        System.out.println("\n=== Course and Faculty Information ===");
        System.out.println("----------------------------------------");
        
        // Get all courses from the system
        List<CourseGrade> allCourses = AdminStaff.getAvailableCourses();
        if (allCourses == null || allCourses.isEmpty()) {
            System.out.println("No courses are currently available.");
            return;
        }

        // Display courses and their assigned faculty
        System.out.println("\nAvailable Courses:");
        System.out.println("----------------------------------------");
        System.out.printf("%-10s %-30s %-20s %-15s %-10s\n", 
            "Course ID", "Title", "Instructor", "Schedule", "Available Seats");
        System.out.println("----------------------------------------");

        for (CourseGrade course : allCourses) {
            System.out.printf("%-10s %-30s %-20s %-15s %-10d\n",
                course.getCourseId(),
                course.getTitle(),
                course.getInstructor(),
                course.getDays() + " " + course.getTimes(),
                course.getAvailableSeats());
        }

        // Display faculty members and their teaching load
        System.out.println("\nFaculty Members:");
        System.out.println("----------------------------------------");
        System.out.printf("%-20s %-15s %-10s %-10s\n", 
            "Name", "Department", "Position", "Courses Teaching");
        System.out.println("----------------------------------------");

        // Get all faculty members from the system
        List<Faculty> allFaculty = AdminStaff.getAllFaculty();
        if (allFaculty != null) {
            for (Faculty faculty : allFaculty) {
                System.out.printf("%-20s %-15s %-10s %-10d\n",
                    faculty.getName(),
                    faculty.getDepartment(),
                    faculty.getPosition(),
                    faculty.getTeachingCourses().size());
            }
        }

        // Display faculty availability
        System.out.println("\nFaculty Availability:");
        System.out.println("----------------------------------------");
        System.out.printf("%-20s %-15s %-20s\n", 
            "Name", "Office Hours", "Office Location");
        System.out.println("----------------------------------------");

        if (allFaculty != null) {
            for (Faculty faculty : allFaculty) {
                System.out.printf("%-20s %-15s %-20s\n",
                    faculty.getName(),
                    faculty.getOfficeHours(),
                    faculty.getOfficeLocation());
            }
        }

        System.out.println("\nNote: Faculty members with fewer courses may be available for additional teaching assignments.");
    }

    public boolean hasScheduleConflict(CourseGrade newCourse) {
        for (CourseGrade existingCourse : teachingCourses) {
            // Check if days overlap
            if (existingCourse.getDays().equals(newCourse.getDays())) {
                // Check if times overlap
                if (doTimeRangesOverlap(existingCourse.getTimes(), newCourse.getTimes())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method to check if two time ranges overlap
    private boolean doTimeRangesOverlap(String time1, String time2) {
        try {
            String[] range1 = time1.split("-");
            String[] range2 = time2.split("-");
            
            int start1 = convertTimeToMinutes(range1[0]);
            int end1 = convertTimeToMinutes(range1[1]);
            int start2 = convertTimeToMinutes(range2[0]);
            int end2 = convertTimeToMinutes(range2[1]);
            
            return (start1 < end2) && (start2 < end1);
        } catch (Exception e) {
            return false;
        }
    }

    // Helper method to convert time string to minutes
    private int convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    // Method to validate faculty workload
    public boolean validateWorkload(CourseGrade newCourse) {
        // Check if adding the course would exceed maximum courses
        if (teachingCourses.size() >= MAX_COURSES_PER_SEMESTER) {
            System.out.println("Cannot add course: Maximum number of courses per semester reached (" + MAX_COURSES_PER_SEMESTER + ")");
            return false;
        }

        // Calculate total credit hours including the new course
        int totalCreditHours = teachingCourses.stream()
            .mapToInt(CourseGrade::getCreditHours)
            .sum() + newCourse.getCreditHours();

        // Check if adding the course would exceed maximum credit hours
        if (totalCreditHours > MAX_CREDIT_HOURS_PER_SEMESTER) {
            System.out.println("Cannot add course: Maximum credit hours per semester would be exceeded (" + MAX_CREDIT_HOURS_PER_SEMESTER + ")");
            return false;
        }

        // Check if the course is in the faculty's department
        if (!newCourse.getDepartment().equals(department)) {
            System.out.println("Cannot add course: Course department (" + newCourse.getDepartment() + 
                             ") does not match faculty department (" + department + ")");
            return false;
        }

        return true;
    }

    // Method to validate office hours
    public boolean validateOfficeHours(String newOfficeHours) {
        if (newOfficeHours == null || newOfficeHours.trim().isEmpty()) {
            System.out.println("Office hours cannot be empty");
            return false;
        }

        // Parse office hours to calculate total hours per week
        String[] sessions = newOfficeHours.split(",");
        int totalHours = 0;

        for (String session : sessions) {
            try {
                String[] parts = session.trim().split("-");
                if (parts.length != 2) {
                    System.out.println("Invalid office hours format. Use format: 'Day HH:MM-HH:MM'");
                    return false;
                }

                String[] time1 = parts[0].split(" ")[1].split(":");
                String[] time2 = parts[1].split(":");
                
                int startHour = Integer.parseInt(time1[0]);
                int startMin = Integer.parseInt(time1[1]);
                int endHour = Integer.parseInt(time2[0]);
                int endMin = Integer.parseInt(time2[1]);

                int duration = (endHour * 60 + endMin) - (startHour * 60 + startMin);
                totalHours += duration / 60;
            } catch (Exception e) {
                System.out.println("Invalid office hours format. Use format: 'Day HH:MM-HH:MM'");
                return false;
            }
        }

        if (totalHours < MIN_OFFICE_HOURS_PER_WEEK) {
            System.out.println("Office hours must be at least " + MIN_OFFICE_HOURS_PER_WEEK + " hours per week");
            return false;
        }

        if (totalHours > MAX_OFFICE_HOURS_PER_WEEK) {
            System.out.println("Office hours cannot exceed " + MAX_OFFICE_HOURS_PER_WEEK + " hours per week");
            return false;
        }

        return true;
    }

    // Method to check if a course can be added considering all constraints
    public boolean canAddCourse(CourseGrade newCourse) {
        // Check workload limits
        if (!validateWorkload(newCourse)) {
            return false;
        }

        // Check schedule conflicts
        if (hasScheduleConflict(newCourse)) {
            System.out.println("Cannot add course: Schedule conflict detected");
            return false;
        }

        // Check if the course is in the faculty's expertise area
        if (expertise != null && !expertise.isEmpty() && 
            !newCourse.getTitle().toLowerCase().contains(expertise.toLowerCase())) {
            System.out.println("Warning: Course may be outside your expertise area (" + expertise + ")");
        }

        return true;
    }

    // Method to display faculty workload summary
    public void displayWorkloadSummary() {
        System.out.println("\n=== Faculty Workload Summary ===");
        System.out.println("Name: " + getName());
        System.out.println("Department: " + department);
        System.out.println("Position: " + position);
        System.out.println("Expertise: " + expertise);
        System.out.println("\nTeaching Load:");
        System.out.println("----------------------------------------");
        System.out.printf("%-10s %-30s %-10s %-15s\n", 
            "Course ID", "Title", "Credits", "Schedule");
        System.out.println("----------------------------------------");

        int totalCreditHours = 0;
        for (CourseGrade course : teachingCourses) {
            System.out.printf("%-10s %-30s %-10d %-15s\n",
                course.getCourseId(),
                course.getTitle(),
                course.getCreditHours(),
                course.getDays() + " " + course.getTimes());
            totalCreditHours += course.getCreditHours();
        }

        System.out.println("----------------------------------------");
        System.out.println("Total Courses: " + teachingCourses.size() + "/" + MAX_COURSES_PER_SEMESTER);
        System.out.println("Total Credit Hours: " + totalCreditHours + "/" + MAX_CREDIT_HOURS_PER_SEMESTER);
        System.out.println("Office Hours: " + officeHours);
        System.out.println("Office Location: " + officeLocation);
    }

    // Method to add a notification
    private void addNotification(String message) {
        notifications.add(message);
        System.out.println("\n=== New Notification ===");
        System.out.println(message);
        System.out.println("=======================\n");

        // Send email notification if enabled
        if (emailNotificationsEnabled && notificationEmail != null && !notificationEmail.isEmpty()) {
            sendEmailNotification(message);
        }
    }

    // Method to send email notification
    private void sendEmailNotification(String message) {
        // In a real system, this would use an email service
        System.out.println("Email notification sent to " + notificationEmail);
        System.out.println("Subject: Course Assignment Notification");
        System.out.println("Message: " + message);
    }

    // Method to view all notifications
    public void viewNotifications() {
        if (notifications.isEmpty()) {
            System.out.println("\nNo notifications available.");
            return;
        }

        System.out.println("\n=== Your Notifications ===");
        for (int i = 0; i < notifications.size(); i++) {
            System.out.println((i + 1) + ". " + notifications.get(i));
        }
        System.out.println("=========================\n");
    }

    // Method to clear notifications
    public void clearNotifications() {
        notifications.clear();
        System.out.println("All notifications have been cleared.");
    }

    // Method to toggle email notifications
    public void toggleEmailNotifications(boolean enabled) {
        this.emailNotificationsEnabled = enabled;
        String status = enabled ? "enabled" : "disabled";
        System.out.println("Email notifications have been " + status);
    }

    // Method to update notification email
    public void updateNotificationEmail(String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.notificationEmail = newEmail;
        System.out.println("Notification email has been updated to: " + newEmail);
    }

    // Method to display detailed course information
    private void displayCourseDetails(CourseGrade course) {
        System.out.println("\n=== Course Details ===");
        System.out.println("Course ID: " + course.getCourseId());
        System.out.println("Title: " + course.getTitle());
        System.out.println("Department: " + course.getDepartment());
        System.out.println("Credit Hours: " + course.getCreditHours());
        System.out.println("Schedule: " + course.getDays() + " " + course.getTimes());
        System.out.println("Location: " + course.getLocation());
        System.out.println("Instructor: " + course.getInstructor());
        System.out.println("Enrolled Students: " + course.getEnrolledStudents().size());
        System.out.println("Available Seats: " + course.getAvailableSeats());
        
        // Display prerequisites if any
        if (!course.getPreRequisites().isEmpty()) {
            System.out.println("Prerequisites: " + String.join(", ", course.getPreRequisites()));
        }

        // Display assignments if any
        List<Assignment> assignments = courseAssignments.get(course);
        if (assignments != null && !assignments.isEmpty()) {
            System.out.println("\nAssignments:");
            System.out.println("----------------------------------------");
            System.out.printf("%-15s %-20s %-10s %-15s\n", 
                "ID", "Title", "Type", "Due Date");
            System.out.println("----------------------------------------");
            
            for (Assignment assignment : assignments) {
                System.out.printf("%-15s %-20s %-10s %-15s\n",
                    assignment.getAssignmentId(),
                    assignment.getTitle(),
                    assignment.getType(),
                    assignment.getDueDate());
            }
        }
    }

    // Method to add an assignment to a course
    private void addAssignmentToCourse(CourseGrade course) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== Add New Assignment ===");
        System.out.print("Enter Assignment ID: ");
        String assignmentId = scanner.nextLine();
        
        System.out.print("Enter Assignment Title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter Assignment Type (Homework/Quiz/Exam/Project): ");
        String type = scanner.nextLine();
        
        System.out.print("Enter Maximum Points: ");
        double maxPoints = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter Due Date (MM/DD/YYYY): ");
        String dueDate = scanner.nextLine();
        
        System.out.print("Enter Assignment Description: ");
        String description = scanner.nextLine();

        createAssignment(course, assignmentId, title, type, maxPoints, dueDate, description);
        
        // Notify enrolled students
        notifyStudentsAboutAssignment(course, assignmentId, title, dueDate);
    }

    // Method to notify students about a new assignment
    private void notifyStudentsAboutAssignment(CourseGrade course, String assignmentId, String title, String dueDate) {
        for (Student student : course.getEnrolledStudents()) {
            String notification = String.format(
                "New assignment in %s:\n" +
                "Title: %s\n" +
                "Due Date: %s\n" +
                "Please check the course page for details.",
                course.getTitle(),
                title,
                dueDate
            );
            student.addNotification(notification);
        }
        System.out.println("All enrolled students have been notified about the new assignment.");
    }

    // Method to enter grades for a course
    private void enterGrades(CourseGrade course) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== Enter Grades ===");
        System.out.println("1. Enter Assignment Grades");
        System.out.println("2. Enter Final Course Grade");
        System.out.print("Select an option (1-2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                enterAssignmentGrades(course);
                break;
            case 2:
                enterFinalCourseGrade(course);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Method to enter assignment grades
    private void enterAssignmentGrades(CourseGrade course) {
        List<Assignment> assignments = courseAssignments.get(course);
        if (assignments == null || assignments.isEmpty()) {
            System.out.println("No assignments found for this course.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        
        // Display available assignments
        System.out.println("\nAvailable Assignments:");
        for (int i = 0; i < assignments.size(); i++) {
            System.out.println((i + 1) + ". " + assignments.get(i).getTitle());
        }
        
        System.out.print("Select an assignment (1-" + assignments.size() + "): ");
        int assignmentChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (assignmentChoice < 0 || assignmentChoice >= assignments.size()) {
            System.out.println("Invalid assignment selection.");
            return;
        }

        Assignment selectedAssignment = assignments.get(assignmentChoice);
        
        // Enter grades for each student
        for (Student student : course.getEnrolledStudents()) {
            System.out.print("Enter grade for " + student.getName() + " (0-" + selectedAssignment.getMaxPoints() + "): ");
            double grade = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            
            gradeAssignment(course, selectedAssignment.getAssignmentId(), student, grade);
            
            // Notify student about grade
            String notification = String.format(
                "Grade entered for %s in %s:\n" +
                "Assignment: %s\n" +
                "Grade: %.2f/%.2f",
                course.getTitle(),
                selectedAssignment.getTitle(),
                grade,
                selectedAssignment.getMaxPoints()
            );
            student.addNotification(notification);
        }
    }

    // Method to enter final course grade
    private void enterFinalCourseGrade(CourseGrade course) {
        Scanner scanner = new Scanner(System.in);
        
        for (Student student : course.getEnrolledStudents()) {
            System.out.print("Enter final grade for " + student.getName() + " (0-100): ");
            double grade = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            
            assignGrade(student, course, grade);
            
            // Notify student about final grade
            String notification = String.format(
                "Final grade entered for %s:\n" +
                "Grade: %.2f\n" +
                "Letter Grade: %s",
                course.getTitle(),
                grade,
                course.getLetterGrade()
            );
            student.addNotification(notification);
        }
    }

    // Method to update course information
    private void updateCourseInformation(CourseGrade course) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== Update Course Information ===");
        System.out.println("1. Update Schedule");
        System.out.println("2. Update Location");
        System.out.println("3. Update Maximum Seats");
        System.out.print("Select an option (1-3): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter new schedule (Days|Time|Location): ");
                String newSchedule = scanner.nextLine();
                course.setScheduele(newSchedule);
                notifyStudentsAboutScheduleChange(course);
                break;
            case 2:
                System.out.print("Enter new location: ");
                String newLocation = scanner.nextLine();
                course.setLocation(newLocation);
                notifyStudentsAboutLocationChange(course);
                break;
            case 3:
                System.out.print("Enter new maximum seats: ");
                int newMaxSeats = scanner.nextInt();
                course.setMaxSeats(newMaxSeats);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Method to notify students about schedule change
    private void notifyStudentsAboutScheduleChange(CourseGrade course) {
        for (Student student : course.getEnrolledStudents()) {
            String notification = String.format(
                "Schedule change for %s:\n" +
                "New Schedule: %s %s",
                course.getTitle(),
                course.getDays(),
                course.getTimes()
            );
            student.addNotification(notification);
        }
        System.out.println("All enrolled students have been notified about the schedule change.");
    }

    // Method to notify students about location change
    private void notifyStudentsAboutLocationChange(CourseGrade course) {
        for (Student student : course.getEnrolledStudents()) {
            String notification = String.format(
                "Location change for %s:\n" +
                "New Location: %s",
                course.getTitle(),
                course.getLocation()
            );
            student.addNotification(notification);
        }
        System.out.println("All enrolled students have been notified about the location change.");
    }

    // Getters and Setters
    public String getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ArrayList<CourseGrade> getTeachingCourses() {
        return new ArrayList<>(teachingCourses);
    }

    public void setTeachingCourses(ArrayList<CourseGrade> teachingCourses) {
        if (teachingCourses == null) {
            throw new IllegalArgumentException("Teaching courses list cannot be null");
        }
        this.teachingCourses = new ArrayList<>(teachingCourses);
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }
private Faculty faculty;
    private double baseSalary;
    private double researchBonus;
    private double teachingBonus;

    public double calculatePayroll() {
        // Calculate research bonus based on publications
        researchBonus = faculty.getTeachingCourses().size() * 1000.0;
        
        // Calculate teaching bonus based on course load
        teachingBonus = faculty.getTeachingCourses().size() * 500.0;
        
        return baseSalary + researchBonus + teachingBonus;
    }

   
    public String generatePayrollReport() {
        return String.format("Faculty Payroll Report\n" +
                           "Name: %s\n" +
                           "Base Salary: $%.2f\n" +
                           "Research Bonus: $%.2f\n" +
                           "Teaching Bonus: $%.2f\n" +
                           "Total: $%.2f",
                           faculty.getName(),
                           baseSalary,
                           researchBonus,
                           teachingBonus,
                           calculatePayroll());
    }
} 