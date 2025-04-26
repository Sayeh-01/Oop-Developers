import java.util.HashMap;
import java.util.Map;

public class Assignment {
    private String assignmentId;
    private String title;
    private String type; // LAB, HOMEWORK, PROJECT, or ASSIGNMENT
    private double maxPoints;
    private String dueDate;
    private String description;
    private Map<Student, Double> studentGrades; // Map to store student grades

    public Assignment(String assignmentId, String title, String type, double maxPoints, String dueDate, String description) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.type = type.toUpperCase();
        this.maxPoints = maxPoints;
        this.dueDate = dueDate;
        this.description = description;
        this.studentGrades = new HashMap<>();
    }

    public void gradeStudent(Student student, double points) {
        if (points < 0 || points > maxPoints) {
            throw new IllegalArgumentException("Points must be between 0 and " + maxPoints);
        }
        studentGrades.put(student, points);
    }

    public double getStudentGrade(Student student) {
        return studentGrades.getOrDefault(student, 0.0);
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public double getMaxPoints() {
        return maxPoints;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public Map<Student, Double> getStudentGrades() {
        return new HashMap<>(studentGrades);
    }

    @Override
    public String toString() {
        return String.format("Assignment ID: %s\nTitle: %s\nType: %s\nMax Points: %.2f\nDue Date: %s\nDescription: %s",
            assignmentId, title, type, maxPoints, dueDate, description);
    }
} 