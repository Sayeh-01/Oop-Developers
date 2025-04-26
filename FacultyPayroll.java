public class FacultyPayroll implements PayrollSystem {
    private Faculty faculty;
    private double baseSalary;

    public FacultyPayroll(Faculty faculty, double baseSalary) {
        this.faculty = faculty;
        this.baseSalary = baseSalary;
    }

    @Override
    public double calculatePayroll() {
        return baseSalary;
    }

    @Override
    public String generatePayrollReport() {
        return String.format("Faculty Payroll Report\nName: %s\nBase Salary: $%.2f\nTotal: $%.2f",
            faculty.getName(), baseSalary, calculatePayroll());
    }
} 