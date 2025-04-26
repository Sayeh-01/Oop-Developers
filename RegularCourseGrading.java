import java.util.List;

public class RegularCourseGrading extends CourseGradingSystem {
    private double midtermWeight;
    private double finalWeight;
    private double assignmentWeight;

    public RegularCourseGrading(CourseGrade course, GradingSystem gradingStrategy, 
                              double midtermWeight, double finalWeight, double assignmentWeight) {
        super(course, gradingStrategy);
        this.midtermWeight = midtermWeight;
        this.finalWeight = finalWeight;
        this.assignmentWeight = assignmentWeight;
    }

    protected double getMidtermWeight() {
        return midtermWeight;
    }

    protected double getFinalWeight() {
        return finalWeight;
    }

    protected double getAssignmentWeight() {
        return assignmentWeight;
    }

    @Override
    public double calculateFinalGrade(List<AssignmentGrade> assignments) {
        double midtermGrade = 0.0;
        double finalGrade = 0.0;
        double assignmentAverage = 0.0;

        for (AssignmentGrade assignment : assignments) {
            if (assignment.getAssignmentName().startsWith("MIDTERM")) {
                midtermGrade = assignment.getGrade();
            } else if (assignment.getAssignmentName().startsWith("FINAL")) {
                finalGrade = assignment.getGrade();
            } else {
                assignmentAverage += assignment.getGrade();
            }
        }

        if (assignments.size() > 2) {
            assignmentAverage /= (assignments.size() - 2);
        }

        return (midtermGrade * midtermWeight) + 
               (finalGrade * finalWeight) + 
               (assignmentAverage * assignmentWeight);
    }

    @Override
    public String getGradingPolicy() {
        return String.format("Regular Course Grading Policy:\n" +
                           "Midterm Exam: %.1f%%\n" +
                           "Final Exam: %.1f%%\n" +
                           "Assignments: %.1f%%",
                           midtermWeight * 100,
                           finalWeight * 100,
                           assignmentWeight * 100);
    }
} 