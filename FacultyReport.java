import java.util.List;

public class FacultyReport implements ReportGenerator {
    private Faculty faculty;
    private University university;

    public FacultyReport(Faculty faculty, University university) {
        this.faculty = faculty;
        this.university = university;
    }

    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("Faculty Teaching Report\n" +
                                  "Name: %s\n" +
                                  "Department: %s\n" +
                                  "Position: %s\n\n" +
                                  "Teaching Schedule:\n",
                                  faculty.getName(),
                                  faculty.getDepartment(),
                                  faculty.getPosition()));

        List<CourseGrade> courses = faculty.getTeachingCourses();
        for (CourseGrade course : courses) {
            report.append(String.format("- %s: %s %s\n",
                                      course.getTitle(),
                                      course.getDays(),
                                      course.getTimes()));
        }

        return report.toString();
    }
} 