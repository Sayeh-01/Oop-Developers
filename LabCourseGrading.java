import java.util.List;

public class LabCourseGrading extends CourseGradingSystem {
    public LabCourseGrading(CourseGrade course, GradingSystem gradingStrategy) {
        super(course, gradingStrategy);
    }

    @Override
    public double calculateFinalGrade(List<AssignmentGrade> assignments) {
        double theoryGrade = gradingStrategy.calculateGrade(assignments);
        double labGrade = calculateLabGrade(assignments);
        return (theoryGrade * 0.6) + (labGrade * 0.4);
    }

    private double calculateLabGrade(List<AssignmentGrade> assignments) {
        double totalLabWeight = 0.0;
        double weightedLabSum = 0.0;

        for (AssignmentGrade ag : assignments) {
            if (ag.getAssignmentName().startsWith("Lab")) {
                totalLabWeight += ag.getWeight();
                weightedLabSum += ag.getGrade() * ag.getWeight();
            }
        }

        return totalLabWeight > 0 ? weightedLabSum / totalLabWeight : 0.0;
    }

    @Override
    public String getGradingPolicy() {
        return "Lab course grading policy:\n" +
               "- Theory Component: 60%\n" +
               "  * Assignments: 20%\n" +
               "  * Midterm: 20%\n" +
               "  * Final Exam: 20%\n" +
               "- Lab Component: 40%\n" +
               "  * Lab Reports: 20%\n" +
               "  * Lab Exams: 20%";
    }
} 