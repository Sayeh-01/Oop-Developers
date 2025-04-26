import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentId;
    private String name;
    private ArrayList<Faculty> facultyMembers;
    private ArrayList<CourseGrade> offeredCourses;

    public Department(String departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
        this.facultyMembers = new ArrayList<>();
        this.offeredCourses = new ArrayList<>();
    }

    public void addFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty cannot be null");
        }

        if (facultyMembers.contains(faculty)) {
            System.out.println(faculty.getName() + " is already a member of this department.");
            return;
        }

        facultyMembers.add(faculty);
        faculty.setDepartment(name);
        System.out.println(faculty.getName() + " added to the " + name + " department.");
    }

    public void removeFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty cannot be null");
        }

        if (!facultyMembers.contains(faculty)) {
            System.out.println(faculty.getName() + " is not a member of this department.");
            return;
        }

        facultyMembers.remove(faculty);
        faculty.setDepartment("");
        System.out.println(faculty.getName() + " removed from the " + name + " department.");
    }

    public void addCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (offeredCourses.contains(course)) {
            System.out.println("Course " + course.getTitle() + " is already offered by this department.");
            return;
        }

        offeredCourses.add(course);
        System.out.println("Course " + course.getTitle() + " added to the department's offerings.");
    }

    public void removeCourse(CourseGrade course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (!offeredCourses.contains(course)) {
            System.out.println("Course " + course.getTitle() + " is not offered by this department.");
            return;
        }

        offeredCourses.remove(course);
        System.out.println("Course " + course.getTitle() + " removed from the department's offerings.");
    }

    public void displayDepartmentInfo() {
        System.out.println("\n--- Department Information ---");
        System.out.println("Department ID: " + departmentId);
        System.out.println("Name: " + name);
        System.out.println("Number of Faculty: " + facultyMembers.size());
        System.out.println("Number of Courses Offered: " + offeredCourses.size());
        System.out.println("------------------------------");
    }

    // Getters and Setters
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Faculty> getFacultyMembers() {
        return new ArrayList<>(facultyMembers);
    }

    public void setFacultyMembers(ArrayList<Faculty> facultyMembers) {
        if (facultyMembers == null) {
            throw new IllegalArgumentException("Faculty members list cannot be null");
        }
        this.facultyMembers = new ArrayList<>(facultyMembers);
    }

    public List<CourseGrade> getOfferedCourses() {
        return new ArrayList<>(offeredCourses);
    }

    public void setOfferedCourses(ArrayList<CourseGrade> offeredCourses) {
        if (offeredCourses == null) {
            throw new IllegalArgumentException("Offered courses list cannot be null");
        }
        this.offeredCourses = new ArrayList<>(offeredCourses);
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId='" + departmentId + '\'' +
                ", name='" + name + '\'' +
                ", facultyMembers=" + facultyMembers.size() +
                ", offeredCourses=" + offeredCourses.size() +
                '}';
    }
}
