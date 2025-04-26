import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseEnrollment {
    private Student student;
    private CourseGrade course;
    private LocalDate enrollmentDate;
    private String status; // "ENROLLED", "DROPPED", "COMPLETED"
    private double grade;
    private List<AssignmentGrade> assignmentGrades;

    public CourseEnrollment(Student student, CourseGrade course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.status = "ENROLLED";
        this.grade = 0.0;
        this.assignmentGrades = new ArrayList<>();
    }

    public void dropCourse() {
        this.status = "DROPPED";
    }

    public void completeCourse(double finalGrade) {
        this.status = "COMPLETED";
        this.grade = finalGrade;
    }

    public void addAssignmentGrade(AssignmentGrade assignmentGrade) {
        assignmentGrades.add(assignmentGrade);
        updateOverallGrade();
    }

    private void updateOverallGrade() {
        if (assignmentGrades.isEmpty()) {
            this.grade = 0.0;
            return;
        }

        double totalWeight = 0.0;
        double weightedSum = 0.0;

        for (AssignmentGrade ag : assignmentGrades) {
            totalWeight += ag.getWeight();
            weightedSum += ag.getGrade() * ag.getWeight();
        }

        if (totalWeight > 0) {
            this.grade = weightedSum / totalWeight;
        }
    }

    // Getters
    public Student getStudent() {
        return student;
    }

    public CourseGrade getCourse() {
        return course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public double getGrade() {
        return grade;
    }

    public List<AssignmentGrade> getAssignmentGrades() {
        return new ArrayList<>(assignmentGrades);
    }

    // Helper class for AssignmentGrade
    public static class AssignmentGrade {
        private String assignmentName;
        private double grade;
        private double weight;
        private LocalDate submissionDate;

        public AssignmentGrade(String assignmentName, double grade, double weight) {
            this.assignmentName = assignmentName;
            this.grade = grade;
            this.weight = weight;
            this.submissionDate = LocalDate.now();
        }

        public String getAssignmentName() {
            return assignmentName;
        }

        public double getGrade() {
            return grade;
        }

        public double getWeight() {
            return weight;
        }

        public LocalDate getSubmissionDate() {
            return submissionDate;
        }
    }
} 