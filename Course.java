import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private String title;
    private String creditHours;
    private String scheduele;
    private String instructor;
    private int maxSeats;
    private List<String> enrolledStudents;
    private List<String> preRequisites;

    public Course(String courseId, String title, String creditHours, String scheduele, String instructor, int maxSeats) {
        this.courseId = courseId;
        this.title = title;
        this.creditHours = creditHours;
        this.scheduele = scheduele;
        this.instructor = instructor;
        this.maxSeats= maxSeats;
        this.enrolledStudents =new ArrayList<>();
        this.preRequisites = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public List<String> getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(List<String> preRequisites) {
        this.preRequisites = preRequisites;
    }

    public String getScheduele() {
        return scheduele;
    }

    public void setScheduele(String scheduele) {
        this.scheduele = scheduele;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}

    public void addStudent(Student student){
        if (getAvailableSeats()>0){
            enrolledStudents.add(student);
            System.out.println(student.get);
        }
        else System.out.println("sorry this course:"+courseId);

    }

    public void removeStudent(){

    }
    public void isPrerequisiteSatisfied(){

    }
    public int getAvailableSeats(){
        return maxSeats-enrolledStudents.size();

    }

}
