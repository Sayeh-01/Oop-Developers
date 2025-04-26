
public class AdminReport implements ReportGenerator {
    private AdminStaff admin;
    private University university;

    public AdminReport(AdminStaff admin, University university) {
        this.admin = admin;
        this.university = university;
    }

    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("Administrative Report\n" +
                                  "Name: %s\n" +
                                  "Department: %s\n" +
                                  "Role: %s\n\n" +
                                  "Department Statistics:\n",
                                  admin.getName(),
                                  admin.getDepartment(),
                                  admin.getRole()));

        // Add department-specific statistics
        report.append(String.format("Total Students: %d\n" +
                                  "Total Faculty: %d\n" +
                                  "Total Courses: %d\n",
                                  AdminStaff.getRegisteredStudents().size(),
                                  AdminStaff.getAllFaculty().size(),
                                  AdminStaff.getAvailableCourses().size()));

        return report.toString();
    }
} 