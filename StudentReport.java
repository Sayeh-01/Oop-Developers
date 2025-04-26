import java.util.List;

public class StudentReport implements ReportGenerator {
    private Student student;
    private University university;

    public StudentReport(Student student, University university) {
        this.student = student;
        this.university = university;
    }

    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("Student Academic Report\n" +
                                  "Name: %s\n" +
                                  "ID: %s\n" +
                                  "Department: %s\n\n" +
                                  "Current Courses:\n",
                                  student.getName(),
                                  student.getStudentID(),
                                  student.getAcademicStatus()));

        List<CourseGrade> enrollments = student.getEnrolledCourses();
        for (CourseGrade course : enrollments) {
            report.append(String.format("- %s: %s (Grade: %.2f)\n",
                                      course.getTitle(),
                                      course.getScheduele(),
                                      course.getGrade()));
        }

        return report.toString();
    }
} 