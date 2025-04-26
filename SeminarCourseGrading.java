import java.util.List;

public class SeminarCourseGrading extends CourseGradingSystem {
    public SeminarCourseGrading(CourseGrade course, GradingSystem gradingStrategy) {
        super(course, gradingStrategy);
    }

    @Override
    public double calculateFinalGrade(List<AssignmentGrade> assignments) {
        double presentationGrade = calculatePresentationGrade(assignments);
        double participationGrade = calculateParticipationGrade(assignments);
        double paperGrade = calculatePaperGrade(assignments);
        
        return (presentationGrade * 0.4) + (participationGrade * 0.3) + (paperGrade * 0.3);
    }

    private double calculatePresentationGrade(List<AssignmentGrade> assignments) {
        double totalWeight = 0.0;
        double weightedSum = 0.0;

        for (AssignmentGrade ag : assignments) {
            if (ag.getAssignmentName().startsWith("Presentation")) {
                totalWeight += ag.getWeight();
                weightedSum += ag.getGrade() * ag.getWeight();
            }
        }

        return totalWeight > 0 ? weightedSum / totalWeight : 0.0;
    }

    private double calculateParticipationGrade(List<AssignmentGrade> assignments) {
        double totalWeight = 0.0;
        double weightedSum = 0.0;

        for (AssignmentGrade ag : assignments) {
            if (ag.getAssignmentName().startsWith("Participation")) {
                totalWeight += ag.getWeight();
                weightedSum += ag.getGrade() * ag.getWeight();
            }
        }

        return totalWeight > 0 ? weightedSum / totalWeight : 0.0;
    }

    private double calculatePaperGrade(List<AssignmentGrade> assignments) {
        double totalWeight = 0.0;
        double weightedSum = 0.0;

        for (AssignmentGrade ag : assignments) {
            if (ag.getAssignmentName().startsWith("Paper")) {
                totalWeight += ag.getWeight();
                weightedSum += ag.getGrade() * ag.getWeight();
            }
        }

        return totalWeight > 0 ? weightedSum / totalWeight : 0.0;
    }

    @Override
    public String getGradingPolicy() {
        return "Seminar course grading policy:\n" +
               "- Presentation: 40%\n" +
               "- Class Participation: 30%\n" +
               "- Final Paper: 30%";
    }
} 