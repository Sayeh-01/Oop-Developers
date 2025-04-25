import java.util.ArrayList;

public class Student extends User {
   private int studentID;
   private String admissionDate;
   private String academicStatus;
   private ArrayList<CourseGrade> enrolledCourses = new ArrayList<>();
    @Override
    public boolean login() {
        return false;
    }

    public ArrayList<CourseGrade> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(ArrayList<CourseGrade> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
    public boolean registerForCourse(){
        return true;
    }
    public boolean dropCourse(){
        return false;
    }
    public void viewGrade(){
        // Implementation for viewing grades
    }
    public double calculateGPA(){
        if (enrolledCourses.isEmpty()) {
            return 0.0;
        }
        
        double totalGradePoints = 0.0;
        int totalCredits = 0;
        
        for (CourseGrade course : enrolledCourses) {
            totalGradePoints += course.getGrade() * course.getCredits();
            totalCredits += course.getCredits();
        }
        
        if (totalCredits == 0) {
            return 0.0;
        }
        
        return totalGradePoints / totalCredits;
    }
}
