import java.util.List;

public class CurvedGradingSystem implements GradingSystem {
    private double mean;
    private double standardDeviation;

    public CurvedGradingSystem(double mean, double standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

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

        double rawGrade = totalWeight > 0 ? weightedSum / totalWeight : 0.0;
        
        // Apply curve based on standard deviation
        double zScore = (rawGrade - mean) / standardDeviation;
        return mean + (zScore * standardDeviation);
    }

    @Override
    public String getLetterGrade(double numericGrade) {
        if (numericGrade >= mean + (1.5 * standardDeviation)) return "A";
        if (numericGrade >= mean + (0.5 * standardDeviation)) return "B";
        if (numericGrade >= mean - (0.5 * standardDeviation)) return "C";
        if (numericGrade >= mean - (1.5 * standardDeviation)) return "D";
        return "F";
    }
} 