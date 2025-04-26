import java.util.ArrayList;

public class Enrollment {
    private Student student;
    private CourseGrade course;
    private String enrollmentDate;
    private double grade;
    private String status;
    private ArrayList<CourseGrade> enrolledCourses;
    private ArrayList<Student> enrolledStudents;

    public Enrollment(Student student, CourseGrade course) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        this.student = student;
        this.course = course;
        this.enrollmentDate = java.time.LocalDate.now().toString();
        this.grade = 0.0;
        this.status = "Enrolled";
        this.enrolledCourses = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    public void assignGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0");
        }

        if (!enrolledCourses.contains(course)) {
            throw new IllegalStateException("Cannot assign grade: Student is not enrolled in this course");
        }

        this.grade = grade;
        this.status = "Completed";
        
        CourseGrade updatedCourse = new CourseGrade(course.getCourseId(), course.getCredits(), grade);
        enrolledCourses.set(enrolledCourses.indexOf(course), updatedCourse);
        
        System.out.println("Grade " + grade + " has been assigned to " + student.getName() + 
                          " for course " + course.getTitle());
    }

    public void enrollCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (!enrolledCourses.contains(course)) {
            if (course.getAvailableSeats() > 0) {
                enrolledCourses.add(course);
                course.addStudent(student);
                System.out.println(student.getName() + " has been enrolled in " + course.getTitle());
            } else {
                System.out.println("Course " + course.getTitle() + " is full");
            }
        } else {
            System.out.println(student.getName() + " is already enrolled in " + course.getTitle());
        }
    }

    public String getStatus() {
        return status;
    }

    public void withdrawCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            course.removeStudent(student);
            System.out.println(student.getName() + " has been withdrawn from " + course.getTitle());
        } else {
            System.out.println(student.getName() + " is not enrolled in " + course.getTitle());
        }
    }

    // Getters
    public Student getStudent() {
        return student;
    }

    public CourseGrade getCourse() {
        return course;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public double getGrade() {
        return grade;
    }

    public ArrayList<CourseGrade> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }

    public ArrayList<Student> getEnrolledStudents() {
        return new ArrayList<>(enrolledStudents);
    }
}
