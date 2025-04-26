import java.util.List;

public abstract class CourseGradingSystem {
    private CourseGrade course;
    protected GradingSystem gradingStrategy;

    public CourseGradingSystem(CourseGrade course, GradingSystem gradingStrategy) {
        this.course = course;
        this.gradingStrategy = gradingStrategy;
    }

    protected CourseGrade getCourse() {
        return course;
    }

    protected GradingSystem getGradingStrategy() {
        return gradingStrategy;
    }

    public abstract double calculateFinalGrade(List<AssignmentGrade> assignments);
    public abstract String getGradingPolicy();
} 