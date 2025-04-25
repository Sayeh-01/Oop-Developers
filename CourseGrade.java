import java.util.ArrayList;
import java.util.List;
public class CourseGrade {
    private String courseId;
    private int credits;
    private double grade; // Grade on a 4.0 scale
    private String title;
    private String creditHours;
    private String scheduele;
    private String instructor;
    private int maxSeats;
    private List<Student> enrolledStudents;
    private List<String> preRequisites;

    public CourseGrade(String courseId, String title, String creditHours, String scheduele, String instructor, int maxSeats) {
        this.courseId = courseId;
        this.title = title;
        this.creditHours = creditHours;
        this.scheduele = scheduele;
        this.instructor = instructor;
        this.maxSeats = maxSeats;
        this.enrolledStudents = new ArrayList<>();
        this.preRequisites = new ArrayList<>();
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

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public List<String> getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(List<String> preRequisites) {
        this.preRequisites = preRequisites;
    }

    public String getScheduele() {
        return scheduele;
    }

    public void setScheduele(String scheduele) {
        this.scheduele = scheduele;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
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
}
