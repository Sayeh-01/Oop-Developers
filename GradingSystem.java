import java.util.List;

public interface GradingSystem {
    double calculateGrade(List<AssignmentGrade> assignments);
    String getLetterGrade(double numericGrade);
}

class AssignmentGrade {
    private String assignmentName;
    private double grade;
    private double weight;

    public AssignmentGrade(String assignmentName, double grade, double weight) {
        this.assignmentName = assignmentName;
        this.grade = grade;
        this.weight = weight;
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
} 