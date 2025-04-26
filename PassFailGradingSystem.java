import java.util.List;

public class PassFailGradingSystem implements GradingSystem {
    @Override
    public double calculateGrade(List<AssignmentGrade> assignments) {
        if (assignments.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0.0;
        double weightedSum = 0.0;

        for (AssignmentGrade ag : assignments) {
            totalWeight += ag.getWeight();
            weightedSum += ag.getGrade() * ag.getWeight();
        }

        return totalWeight > 0 ? weightedSum / totalWeight : 0.0;
    }

    @Override
    public String getLetterGrade(double numericGrade) {
        return numericGrade >= 70.0 ? "P" : "F";
    }
} 