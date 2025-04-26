import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class University {
    private String name;
    private List<Department> departments;
    private List<User> users;
    private List<CourseGrade> courses;
    private List<CourseEnrollment> enrollments;
    private AcademicCalendar academicCalendar;
    private Map<String, List<CourseGrade>> departmentCourses;
    private Map<String, List<Faculty>> departmentFaculty;
    private Map<String, List<Student>> departmentStudents;
    private Map<Student, List<CourseEnrollment>> studentEnrollments;
    private Map<CourseGrade, List<CourseEnrollment>> courseEnrollments;

    public University(String name) {
        this.name = name;
        this.departments = new ArrayList<>();
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.academicCalendar = new AcademicCalendar();
        this.departmentCourses = new HashMap<>();
        this.departmentFaculty = new HashMap<>();
        this.departmentStudents = new HashMap<>();
        this.studentEnrollments = new HashMap<>();
        this.courseEnrollments = new HashMap<>();
    }

    // Department Management
    public void createDepartment(String name, String code, String description) {
        Department department = new Department(name, code, description);
        departments.add(department);
        departmentCourses.put(code, new ArrayList<>());
        departmentFaculty.put(code, new ArrayList<>());
        departmentStudents.put(code, new ArrayList<>());
        FileManager.saveDepartments(getDepartmentNames());
    }

    public List<String> getDepartmentNames() {
        List<String> names = new ArrayList<>();
        for (Department dept : departments) {
            names.add(dept.getName());
        }
        return names;
    }

    public Department getDepartment(String code) {
        for (Department dept : departments) {
            if (dept.getCode().equals(code)) {
                return dept;
            }
        }
        return null;
    }

    // Course Management
    public void offerCourse(CourseGrade course, String departmentCode, Faculty instructor) {
        if (!courses.contains(course)) {
            courses.add(course);
            departmentCourses.get(departmentCode).add(course);
            course.setInstructorName(instructor.getName());
            courseEnrollments.put(course, new ArrayList<>());
            FileManager.saveCourses(courses);
        }
    }

    public void enrollStudentInCourse(Student student, CourseGrade course) {
        if (!studentEnrollments.containsKey(student)) {
            studentEnrollments.put(student, new ArrayList<>());
        }

        CourseEnrollment enrollment = new CourseEnrollment(student, course);
        studentEnrollments.get(student).add(enrollment);
        courseEnrollments.get(course).add(enrollment);
        enrollments.add(enrollment);
    }

    public void dropStudentFromCourse(Student student, CourseGrade course) {
        List<CourseEnrollment> studentEnrolls = studentEnrollments.get(student);
        List<CourseEnrollment> courseEnrolls = courseEnrollments.get(course);

        if (studentEnrolls != null && courseEnrolls != null) {
            CourseEnrollment enrollment = studentEnrolls.stream()
                .filter(e -> e.getCourse().equals(course))
                .findFirst()
                .orElse(null);

            if (enrollment != null) {
                enrollment.dropCourse();
                studentEnrolls.remove(enrollment);
                courseEnrolls.remove(enrollment);
                enrollments.remove(enrollment);
            }
        }
    }

    // User Management
    public void registerStudent(Student student, String departmentCode) {
        if (!users.contains(student)) {
            users.add(student);
            departmentStudents.get(departmentCode).add(student);
            studentEnrollments.put(student, new ArrayList<>());
            FileManager.saveUsers(users);
            FileManager.saveStudents(departmentStudents.get(departmentCode));
        }
    }

    public void hireFaculty(Faculty faculty, String departmentCode) {
        if (!users.contains(faculty)) {
            users.add(faculty);
            departmentFaculty.get(departmentCode).add(faculty);
            FileManager.saveUsers(users);
        }
    }

    public void addAdminStaff(AdminStaff admin) {
        if (!users.contains(admin)) {
            users.add(admin);
            FileManager.saveUsers(users);
        }
    }

    // Getters
    public List<CourseEnrollment> getStudentEnrollments(Student student) {
        return new ArrayList<>(studentEnrollments.getOrDefault(student, new ArrayList<>()));
    }

    public List<CourseEnrollment> getCourseEnrollments(CourseGrade course) {
        return new ArrayList<>(courseEnrollments.getOrDefault(course, new ArrayList<>()));
    }

    public List<Student> getEnrolledStudents(CourseGrade course) {
        List<Student> students = new ArrayList<>();
        for (CourseEnrollment enrollment : courseEnrollments.getOrDefault(course, new ArrayList<>())) {
            students.add(enrollment.getStudent());
        }
        return students;
    }

    public List<CourseGrade> getStudentCourses(Student student) {
        List<CourseGrade> courses = new ArrayList<>();
        for (CourseEnrollment enrollment : studentEnrollments.getOrDefault(student, new ArrayList<>())) {
            courses.add(enrollment.getCourse());
        }
        return courses;
    }

    public List<CourseGrade> getDepartmentCourses(String departmentCode) {
        return departmentCourses.getOrDefault(departmentCode, new ArrayList<>());
    }

    public List<Faculty> getDepartmentFaculty(String departmentCode) {
        return departmentFaculty.getOrDefault(departmentCode, new ArrayList<>());
    }

    public List<Student> getDepartmentStudents(String departmentCode) {
        return departmentStudents.getOrDefault(departmentCode, new ArrayList<>());
    }

    // Academic Calendar Management
    public void setAcademicCalendar(AcademicCalendar calendar) {
        this.academicCalendar = calendar;
    }

    public AcademicCalendar getAcademicCalendar() {
        return academicCalendar;
    }

    // Data Loading
    public void loadData() {
        // Load departments
        List<String> deptNames = FileManager.loadDepartments();
        for (String name : deptNames) {
            createDepartment(name, name.substring(0, 3).toUpperCase(), name + " Department");
        }

        // Load users
        users = FileManager.loadUsers();
        for (User user : users) {
            if (user instanceof Student) {
                Student student = (Student) user;
                // Use the department code from the student's enrolled courses
                for (CourseGrade course : student.getEnrolledCourses()) {
                    String deptCode = course.getDepartment();
                    if (deptCode != null && departmentStudents.containsKey(deptCode)) {
                        departmentStudents.get(deptCode).add(student);
                        break;
                    }
                }
            } else if (user instanceof Faculty) {
                Faculty faculty = (Faculty) user;
                // Use the department from the faculty's teaching courses
                for (CourseGrade course : faculty.getTeachingCourses()) {
                    String deptCode = course.getDepartment();
                    if (deptCode != null && departmentFaculty.containsKey(deptCode)) {
                        departmentFaculty.get(deptCode).add(faculty);
                        break;
                    }
                }
            }
        }

        // Load courses
        courses = FileManager.loadCourses();
        for (CourseGrade course : courses) {
            String deptCode = course.getDepartment();
            if (deptCode != null && departmentCourses.containsKey(deptCode)) {
                departmentCourses.get(deptCode).add(course);
            }
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<CourseGrade> getCourses() {
        return courses;
    }

    // Helper class for Department
    private static class Department {
        private String name;
        private String code;
        private String description;

        public Department(String name, String code, String description) {
            this.name = name;
            this.code = code;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}
