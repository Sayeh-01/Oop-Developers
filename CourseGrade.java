import java.util.ArrayList;
import java.util.List;
public class CourseGrade {
    private String courseId;
    private int credits;
    private double grade; // Grade on a 100-point scale
    private String title;
    private int creditHours;
    private String days;        // Days of the week (e.g., "Monday,Wednesday")
    private String times;       // Class times (e.g., "10:00-11:30")
    private String location;    // Room/building location
    private String instructor; // Changed from Faculty to String to store instructor name
    private int maxSeats;
    private List<Student> enrolledStudents;
    private List<String> preRequisites;
    private String department; // Department offering the course
    private static List<CourseGrade> allCourses = new ArrayList<>(); // Track all courses for location conflict checking

    public CourseGrade(String courseId, String title, int creditHours, String days, String times, String location, String instructor, int maxSeats, String department) {
        this.courseId = courseId;
        this.title = title;
        this.creditHours = creditHours;
        this.days = days;
        this.times = times;
        this.location = location;
        this.instructor = instructor;
        this.maxSeats = maxSeats;
        this.department = department;
        this.enrolledStudents = new ArrayList<>();
        this.preRequisites = new ArrayList<>();
        
        // Check for location conflicts before adding to all courses
        if (hasLocationConflict()) {
            throw new IllegalArgumentException("Cannot create course: Location conflict detected with existing courses");
        }
        allCourses.add(this);
    }

    public CourseGrade(String courseId, int credits, double grade) {
        this.courseId = courseId;
        this.credits = credits;
        this.grade = grade;
    }

    public int getCredits() {
        return credits;
    }

    public double getGrade() {
        return grade;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public List<String> getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(List<String> preRequisites) {
        this.preRequisites = preRequisites;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getScheduele() {
        return days + "|" + times + "|" + location;
    }

    public void setScheduele(String scheduele) {
        String[] parts = scheduele.split("\\|");
        if (parts.length >= 3) {
            this.days = parts[0];
            this.times = parts[1];
            this.location = parts[2];
        } else {
            this.days = scheduele;
            this.times = "";
            this.location = "";
        }
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructorName(String instructorName) {
        this.instructor = instructorName;
    }

    public void addStudent(Student student) {
        if (getAvailableSeats() > 0) {
            enrolledStudents.add(student);
            System.out.println("Student added successfully to course: " + courseId);
        } else {
            System.out.println("Sorry, this course: " + courseId + " is full");
        }
    }

    public boolean removeStudent(Student student) {
        if (enrolledStudents.contains(student)) {
            enrolledStudents.remove(student);
            System.out.println("Student removed successfully from course: " + courseId);
            return true;
        } else {
            System.out.println("Student not found in course: " + courseId);
            return false;
        }
    }

    public boolean isPrerequisiteSatisfied(Student student) {
        if (preRequisites.isEmpty()) {
            return true; // No prerequisites required
        }

        // Get all completed courses by the student
        List<CourseGrade> completedCourses = student.getEnrolledCourses();
        
        // Check if student has completed all prerequisites
        for (String prerequisite : preRequisites) {
            boolean found = false;
            for (CourseGrade course : completedCourses) {
                if (course.getCourseId().equals(prerequisite)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Prerequisite course " + prerequisite + " not completed");
                return false;
            }
        }
        
        return true;
    }

    public int getAvailableSeats() {
        return maxSeats - enrolledStudents.size();
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0");
        }
        this.grade = grade;
    }

    public List<Student> getEnrolledStudents() {
        return new ArrayList<>(enrolledStudents);
    }

    public String getLetterGrade() {
        if (grade >= 95.0) return "A+";
        if (grade >= 90.0) return "A";
        if (grade >= 85.0) return "A-";
        if (grade >= 80.0) return "B+";
        if (grade >= 75.0) return "B";
        if (grade >= 70.0) return "B-";
        if (grade >= 65.0) return "C+";
        if (grade >= 60.0) return "C";
        if (grade >= 55.0) return "C-";
        if (grade >= 50.0) return "D";
        return "F";
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Method to check for location conflicts with other courses
    private boolean hasLocationConflict() {
        for (CourseGrade otherCourse : allCourses) {
            if (otherCourse != this && 
                otherCourse.getLocation().equals(this.location) && 
                otherCourse.getDays().equals(this.days) && 
                doTimeRangesOverlap(otherCourse.getTimes(), this.times)) {
                return true;
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
}
