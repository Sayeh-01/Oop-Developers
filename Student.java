import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends User {
    private int studentID;
    private String admissionDate;
    private String academicStatus;
    private ArrayList<CourseGrade> enrolledCourses = new ArrayList<>();
    private static final int MAX_CREDIT_HOURS_PER_SEMESTER = 34; // Maximum credit hours allowed per semester
    private List<String> notifications;

    public Student(String academicStatus, String admissionDate, int studentID, String contactInfo, String email, String name, String userId) {
        super(contactInfo, email, name, String.valueOf(studentID));
        this.academicStatus = academicStatus;
        this.admissionDate = admissionDate;
        this.studentID = studentID;
        this.notifications = new ArrayList<>();
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

    public ArrayList<CourseGrade> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses); // Return a copy to prevent external modification
    }

    public void setEnrolledCourses(ArrayList<CourseGrade> enrolledCourses) {
        if (enrolledCourses == null) {
            throw new IllegalArgumentException("Enrolled courses list cannot be null");
        }
        this.enrolledCourses = new ArrayList<>(enrolledCourses);
    }

    public boolean registerForCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        // Check if already enrolled
        if (enrolledCourses.contains(course)) {
            System.out.println("You are already enrolled in this course: " + course.getTitle());
            return false;
        }

        // Check prerequisites
        if (!course.isPrerequisiteSatisfied(this)) {
            System.out.println("Prerequisites not satisfied for course: " + course.getTitle());
            return false;
        }

        // Check available seats
        if (course.getAvailableSeats() <= 0) {
            System.out.println("Course is full: " + course.getTitle());
            return false;
        }

        // Enroll in the course
        enrolledCourses.add(course);
        course.addStudent(this);
        System.out.println("Successfully enrolled in course: " + course.getTitle());
        return true;
    }

    // Helper method to check if two time ranges overlap
    private boolean doTimeRangesOverlap(String time1, String time2) {
        try {
            // Parse time ranges (format: "HH:MM-HH:MM")
            String[] range1 = time1.split("-");
            String[] range2 = time2.split("-");
            
            // Convert times to minutes since midnight
            int start1 = convertTimeToMinutes(range1[0]);
            int end1 = convertTimeToMinutes(range1[1]);
            int start2 = convertTimeToMinutes(range2[0]);
            int end2 = convertTimeToMinutes(range2[1]);
            
            // Check for overlap
            return (start1 < end2) && (start2 < end1);
        } catch (Exception e) {
            // If time format is invalid, assume no conflict
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

    // Helper method to check if two sets of days overlap
    private boolean doDaysOverlap(String days1, String days2) {
        String[] daysArray1 = days1.split(",");
        String[] daysArray2 = days2.split(",");
        
        for (String day1 : daysArray1) {
            for (String day2 : daysArray2) {
                if (day1.trim().equals(day2.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Method to check if a course conflicts with any enrolled courses
    private boolean hasScheduleConflict(CourseGrade newCourse) {
        for (CourseGrade enrolledCourse : enrolledCourses) {
            // Check if days overlap
            if (doDaysOverlap(enrolledCourse.getDays(), newCourse.getDays())) {
                // If days overlap, check if times overlap
                if (doTimeRangesOverlap(enrolledCourse.getTimes(), newCourse.getTimes())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void registerForCourse() {
        Scanner scanner = new Scanner(System.in);
        
        // Calculate current total credit hours
        int currentCreditHours = enrolledCourses.stream()
                .mapToInt(CourseGrade::getCreditHours)
                .sum();
        
        // First display all available courses
        System.out.println("\n--- Available Courses for Registration ---");
        System.out.println("----------------------------------------");
        System.out.println("Current Total Credit Hours: " + currentCreditHours);
        System.out.println("Maximum Credit Hours Allowed: " + MAX_CREDIT_HOURS_PER_SEMESTER);
        System.out.println("Remaining Available Credit Hours: " + (MAX_CREDIT_HOURS_PER_SEMESTER - currentCreditHours));
        System.out.println("Current GPA: " + String.format("%.2f", calculateGPA()));
        System.out.println("----------------------------------------");
        
        List<CourseGrade> allCourses = AdminStaff.getAvailableCourses();
        if (allCourses == null || allCourses.isEmpty()) {
            System.out.println("No courses are currently available for registration.");
            return;
        }

        // Display available courses with numbers for selection
        int courseCount = 0;
        for (CourseGrade course : allCourses) {
            // Skip if already enrolled
            if (enrolledCourses.contains(course)) {
                continue;
            }

            // Skip if course is full
            if (course.getAvailableSeats() <= 0) {
                continue;
            }

            // Check prerequisites
            if (!course.isPrerequisiteSatisfied(this)) {
                continue;
            }

            // Check if adding this course would exceed max credit hours
            if (currentCreditHours + course.getCreditHours() > MAX_CREDIT_HOURS_PER_SEMESTER) {
                continue;
            }

            // Check GPA requirement for max credit hours
            if (currentCreditHours + course.getCreditHours() >= MAX_CREDIT_HOURS_PER_SEMESTER && calculateGPA() < 3.0) {
                continue;
            }

            // Check for schedule conflicts
            if (hasScheduleConflict(course)) {
                continue;
            }

            courseCount++;
            System.out.println("\nCourse #" + courseCount);
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Instructor: " + course.getInstructor());
            System.out.println("Days: " + course.getDays());
            System.out.println("Time: " + course.getTimes());
            System.out.println("Location: " + course.getLocation());
            System.out.println("Credit Hours: " + course.getCreditHours());
            System.out.println("Available Seats: " + course.getAvailableSeats());
            
            // Display prerequisites if any
            if (!course.getPreRequisites().isEmpty()) {
                System.out.println("Prerequisites: " + String.join(", ", course.getPreRequisites()));
            }
            System.out.println("----------------------------------------");
        }

        if (courseCount == 0) {
            System.out.println("No courses are currently available for registration.");
            System.out.println("This could be because:");
            System.out.println("- You are already enrolled in all available courses");
            System.out.println("- All courses are full");
            System.out.println("- You don't meet the prerequisites for available courses");
            System.out.println("- The courses conflict with your current schedule");
            if (currentCreditHours >= MAX_CREDIT_HOURS_PER_SEMESTER) {
                System.out.println("- You have reached the maximum credit hours allowed for this semester");
            }
            if (currentCreditHours + 1 >= MAX_CREDIT_HOURS_PER_SEMESTER && calculateGPA() < 3.0) {
                System.out.println("- You need a GPA of 3.0 or higher to register for the maximum credit hours");
            }
            return;
        }

        // Allow student to select a course
        System.out.print("\nEnter the course number you want to register for (or 0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 0) {
            System.out.println("Registration cancelled.");
            return;
        }

        if (choice < 1 || choice > courseCount) {
            System.out.println("Invalid course number. Please try again.");
            return;
        }

        // Find the selected course
        int currentCount = 0;
        CourseGrade selectedCourse = null;
        for (CourseGrade course : allCourses) {
            if (enrolledCourses.contains(course) || 
                course.getAvailableSeats() <= 0 || 
                !course.isPrerequisiteSatisfied(this) ||
                currentCreditHours + course.getCreditHours() > MAX_CREDIT_HOURS_PER_SEMESTER ||
                (currentCreditHours + course.getCreditHours() >= MAX_CREDIT_HOURS_PER_SEMESTER && calculateGPA() < 3.0) ||
                hasScheduleConflict(course)) {
                continue;
            }
            currentCount++;
            if (currentCount == choice) {
                selectedCourse = course;
                break;
            }
        }

        if (selectedCourse != null) {
            // Check if adding this course would exceed max credit hours
            if (currentCreditHours + selectedCourse.getCreditHours() > MAX_CREDIT_HOURS_PER_SEMESTER) {
                System.out.println("Cannot register for this course. It would exceed the maximum credit hours allowed for this semester.");
                return;
            }
            
            // Check GPA requirement for max credit hours
            if (currentCreditHours + selectedCourse.getCreditHours() >= MAX_CREDIT_HOURS_PER_SEMESTER && calculateGPA() < 3.0) {
                System.out.println("Cannot register for this course. You need a GPA of 3.0 or higher to register for the maximum credit hours.");
                return;
            }

            // Check for schedule conflicts
            if (hasScheduleConflict(selectedCourse)) {
                System.out.println("Cannot register for this course. It conflicts with your current schedule.");
                return;
            }
            
            // Register for the selected course
            if (registerForCourse(selectedCourse)) {
                System.out.println("Successfully registered for course: " + selectedCourse.getTitle());
                System.out.println("New total credit hours: " + (currentCreditHours + selectedCourse.getCreditHours()));
                displaySchedule(); // Show updated schedule after registration
            }
        }
    }

    public boolean dropCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (!enrolledCourses.contains(course)) {
            System.out.println("You are not enrolled in this course: " + course.getTitle());
            return false;
        }

        enrolledCourses.remove(course);
        course.removeStudent(this);
        System.out.println("Successfully dropped course: " + course.getTitle());
        return true;
    }

    public void viewGrade() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }

        System.out.println("\nYour Grades:");
        System.out.println("----------------------------------------");
        for (CourseGrade course : enrolledCourses) {
            System.out.printf("Course: %s\n", course.getTitle());
            System.out.printf("Numerical Grade: %.2f\n", course.getGrade());
            System.out.printf("Letter Grade: %s\n", course.getLetterGrade());
            System.out.println("----------------------------------------");
        }
    }
    public void getGPA(){
        System.out.println("Current GPA: " + String.format("%.2f", calculateGPA()));
    }

    private  double calculateGPA() {
        if (enrolledCourses.isEmpty()) {
            return 0.0;
        }
        
        double totalGradePoints = 0.0;
        int totalCredits = 0;
        
        for (CourseGrade course : enrolledCourses) {
            totalGradePoints += course.getGrade() * course.getCredits();
            totalCredits += course.getCredits();
        }
        
        if (totalCredits == 0) {
            return 0.0;
        }
        
        return totalGradePoints / totalCredits;
    }

    public void displayAcademicRecords() {
        System.out.println("\n--- Academic Records ---");
        System.out.println("Student Name: " + getName());
        System.out.println("Student ID: " + getStudentID());
        System.out.println("Admission Date: " + getAdmissionDate());
        System.out.println("Academic Status: " + getAcademicStatus());
        System.out.println("Current GPA: " + String.format("%.2f", calculateGPA()));
        System.out.println("----------------------------------------");

        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
            return;
        }

        System.out.println("\nCourse Grades:");
        System.out.println("----------------------------------------");
        for (CourseGrade course : enrolledCourses) {
            System.out.println("Course: " + course.getTitle());
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Credits: " + course.getCredits());
            System.out.println("Instructor: " + course.getInstructor());
            System.out.printf("Numerical Grade: %.2f\n", course.getGrade());
            System.out.println("Letter Grade: " + course.getLetterGrade());
            System.out.println("----------------------------------------");
        }

        // Display academic standing based on GPA
        System.out.println("\nAcademic Standing:");
        double gpa = calculateGPA();
        if (gpa >= 3.5) {
            System.out.println("Excellent Standing (Dean's List)");
        } else if (gpa >= 3.0) {
            System.out.println("Good Standing");
        } else if (gpa >= 2.0) {
            System.out.println("Satisfactory Standing");
        } else {
            System.out.println("Academic Probation");
        }

        // Display credit hours information
        int totalCredits = enrolledCourses.stream()
                .mapToInt(CourseGrade::getCredits)
                .sum();
        System.out.println("\nTotal Credit Hours: " + totalCredits);
        
        // Display grade distribution
        System.out.println("\nGrade Distribution:");
        long aPlus = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("A+"))
                .count();
        long a = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("A"))
                .count();
        long aMinus = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("A-"))
                .count();
        long bPlus = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("B+"))
                .count();
        long b = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("B"))
                .count();
        long bMinus = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("B-"))
                .count();
        long cPlus = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("C+"))
                .count();
        long c = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("C"))
                .count();
        long cMinus = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("C-"))
                .count();
        long d = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("D"))
                .count();
        long f = enrolledCourses.stream()
                .filter(course -> course.getLetterGrade().equals("F"))
                .count();

        System.out.println("A+: " + aPlus);
        System.out.println("A: " + a);
        System.out.println("A-: " + aMinus);
        System.out.println("B+: " + bPlus);
        System.out.println("B: " + b);
        System.out.println("B-: " + bMinus);
        System.out.println("C+: " + cPlus);
        System.out.println("C: " + c);
        System.out.println("C-: " + cMinus);
        System.out.println("D: " + d);
        System.out.println("F: " + f);
    }

    public void displayAvailableCourses(List<CourseGrade> allCourses) {
        if (allCourses == null || allCourses.isEmpty()) {
            System.out.println("No courses are currently available.");
            return;
        }

        System.out.println("\n--- Available Courses ---");
        System.out.println("----------------------------------------");
        
        boolean foundAvailable = false;
        for (CourseGrade course : allCourses) {
            // Skip if already enrolled
            if (enrolledCourses.contains(course)) {
                continue;
            }

            // Skip if course is full
            if (course.getAvailableSeats() <= 0) {
                continue;
            }

            // Check prerequisites
            if (!course.isPrerequisiteSatisfied(this)) {
                continue;
            }

            foundAvailable = true;
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Instructor: " + course.getInstructor());
            System.out.println("Schedule: " + course.getScheduele());
            System.out.println("Credit Hours: " + course.getCredits());
            System.out.println("Available Seats: " + course.getAvailableSeats());
            
            // Display prerequisites if any
            if (!course.getPreRequisites().isEmpty()) {
                System.out.println("Prerequisites: " + String.join(", ", course.getPreRequisites()));
            }
            
            System.out.println("----------------------------------------");
        }

        if (!foundAvailable) {
            System.out.println("No courses are currently available for registration.");
            System.out.println("This could be because:");
            System.out.println("- You are already enrolled in all available courses");
            System.out.println("- All courses are full");
            System.out.println("- You don't meet the prerequisites for available courses");
        }
    }

    // Method to display the student's current schedule
    public void displaySchedule() {
        System.out.println("\n=== Your Current Schedule ===");
        System.out.println("Student: " + getName() + " (ID: " + getStudentID() + ")");
        System.out.println("Admission Date: " + getAdmissionDate());
        System.out.println("Academic Status: " + getAcademicStatus());
        System.out.println("\nRegistered Courses:");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s %-15s %-10s\n", 
            "Course ID", "Title", "Days", "Time", "Location");
        System.out.println("------------------------------------------------------------");

        for (CourseGrade course : enrolledCourses) {
            System.out.printf("%-10s %-30s %-15s %-15s %-10s\n",
                course.getCourseId(),
                course.getTitle(),
                course.getDays(),
                course.getTimes(),
                course.getLocation());
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Total Courses: " + enrolledCourses.size());
        System.out.println("Total Credit Hours: " + calculateTotalCreditHours());
    }

    // Method to calculate total credit hours
    private int calculateTotalCreditHours() {
        return enrolledCourses.stream()
            .mapToInt(CourseGrade::getCreditHours)
            .sum();
    }

    // Method to add a notification
    public void addNotification(String message) {
        notifications.add(message);
        System.out.println("\n=== New Notification ===");
        System.out.println(message);
        System.out.println("=======================\n");
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

    // Getters and Setters
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
        setUserId(String.valueOf(studentID));
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }
}
