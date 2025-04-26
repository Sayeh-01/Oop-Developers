import java.util.List;

public class StandardGradingSystem implements GradingSystem {
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
        if (numericGrade >= 95.0) return "A+";
        if (numericGrade >= 90.0) return "A";
        if (numericGrade >= 85.0) return "A-";
        if (numericGrade >= 80.0) return "B+";
        if (numericGrade >= 75.0) return "B";
        if (numericGrade >= 70.0) return "B-";
        if (numericGrade >= 65.0) return "C+";
        if (numericGrade >= 60.0) return "C";
        if (numericGrade >= 55.0) return "C-";
        if (numericGrade >= 50.0) return "D";
        return "F";
    }
} 