import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String USERS_FILE = "users.txt";
    private static final String STUDENTS_FILE = "students.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static final String ENROLLMENTS_FILE = "enrollments.txt";
    private static final String DEPARTMENTS_FILE = "departments.txt";
    private static final String DATA_DIR = "data";

    // Initialize data directory
    static {
        ensureDataDirectory();
    }

    // Save methods
    public static void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(DATA_DIR, USERS_FILE)))) {
            writer.println("username|password|role|name|email|contact");
            for (User user : users) {
                writer.println(String.format("%s|%s|%s|%s|%s|%s",
                    user.getUserName(),
                    user.getPassword(),
                    user.getClass().getSimpleName(),
                    user.getName(),
                    user.getEmail(),
                    user.getContactInfo()
                ));
            }
            System.out.println("Users data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving users data: " + e.getMessage());
        }
    }

    public static void saveStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(DATA_DIR, STUDENTS_FILE)))) {
            writer.println("studentID|academicStatus|admissionDate|name|email|contact");
            for (Student student : students) {
                writer.println(String.format("%d|%s|%s|%s|%s|%s",
                    student.getStudentID(),
                    student.getAcademicStatus(),
                    student.getAdmissionDate(),
                    student.getName(),
                    student.getEmail(),
                    student.getContactInfo()
                ));
            }
            System.out.println("Students data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving students data: " + e.getMessage());
        }
    }

    public static void saveCourses(List<CourseGrade> courses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(DATA_DIR, COURSES_FILE)))) {
            writer.println("courseId|title|creditHours|days|times|location|instructor|maxSeats|department");
            for (CourseGrade course : courses) {
                writer.println(String.format("%s|%s|%d|%s|%s|%s|%s|%d|%s",
                    course.getCourseId(),
                    course.getTitle(),
                    course.getCreditHours(),
                    course.getDays(),
                    course.getTimes(),
                    course.getLocation(),
                    course.getInstructor(),
                    course.getMaxSeats(),
                    course.getDepartment()
                ));
            }
            System.out.println("Courses data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving courses data: " + e.getMessage());
        }
    }

    public static void saveEnrollments(List<CourseGrade> courses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(DATA_DIR, ENROLLMENTS_FILE)))) {
            writer.println("courseId|studentId|grade");
            for (CourseGrade course : courses) {
                for (Student student : course.getEnrolledStudents()) {
                    writer.println(String.format("%s|%d|%.2f",
                        course.getCourseId(),
                        student.getStudentID(),
                        course.getGrade()
                    ));
                }
            }
            System.out.println("Enrollments data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving enrollments data: " + e.getMessage());
        }
    }

    public static void saveDepartments(List<String> departments) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(DATA_DIR, DEPARTMENTS_FILE)))) {
            writer.println("departmentName");
            for (String department : departments) {
                writer.println(department);
            }
            System.out.println("Departments data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving departments data: " + e.getMessage());
        }
    }

    // Load methods
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(DATA_DIR, USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String role = parts[2];
                    switch (role) {
                        case "Student":
                            users.add(new Student(
                                parts[1], // academicStatus
                                parts[2], // admissionDate
                                Integer.parseInt(parts[0]), // studentID
                                parts[5], // contact
                                parts[4], // email
                                parts[3], // name
                                parts[0]  // username
                            ));
                            break;
                        case "Faculty":
                            users.add(new Faculty(
                                parts[0], // facultyID
                                parts[1], // department
                                parts[2], // position
                                parts[5], // contact
                                parts[4], // email
                                parts[3], // name
                                parts[0]  // username
                            ));
                            break;
                        case "AdminStaff":
                            // Create a concrete implementation of AdminStaff with required parameters
                            users.add(new AdminStaff(
                                parts[0], // staffID
                                parts[1], // department
                                parts[2], // role
                                parts[3], // username
                                parts[4], // name
                                parts[5], // email
                                parts[6]  // contact
                            ) {
                                @Override
                                public void registerStudent(Student student) {
                                    // Implementation for registering students
                                }

                                @Override
                                public void createCourse(CourseGrade course) {
                                    // Implementation for creating courses
                                }

                                @Override
                                public void assignFacultyToCourse() {
                                    // Implementation for assigning faculty
                                }

                                @Override
                                public void generateReports() {
                                    // Implementation for generating reports
                                }
                            });
                            break;
                    }
                }
            }
            System.out.println("Users data loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading users data: " + e.getMessage());
        }
        return users;
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(DATA_DIR, STUDENTS_FILE);
        if (!file.exists()) return students;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    students.add(new Student(
                        parts[1], // academicStatus
                        parts[2], // admissionDate
                        Integer.parseInt(parts[0]), // studentID
                        parts[5], // contact
                        parts[4], // email
                        parts[3], // name
                        parts[0]  // username (using studentID as username)
                    ));
                }
            }
            System.out.println("Students data loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading students data: " + e.getMessage());
        }
        return students;
    }

    public static List<CourseGrade> loadCourses() {
        List<CourseGrade> courses = new ArrayList<>();
        File file = new File(DATA_DIR, COURSES_FILE);
        if (!file.exists()) return courses;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 9) {
                    courses.add(new CourseGrade(
                        parts[0], // courseId
                        parts[1], // title
                        Integer.parseInt(parts[2]), // creditHours
                        parts[3], // days
                        parts[4], // times
                        parts[5], // location
                        parts[6], // instructor
                        Integer.parseInt(parts[7]), // maxSeats
                        parts[8]  // department
                    ));
                }
            }
            System.out.println("Courses data loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading courses data: " + e.getMessage());
        }
        return courses;
    }

    public static List<String> loadDepartments() {
        List<String> departments = new ArrayList<>();
        File file = new File(DATA_DIR, DEPARTMENTS_FILE);
        if (!file.exists()) return departments;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                departments.add(line.trim());
            }
            System.out.println("Departments data loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading departments data: " + e.getMessage());
        }
        return departments;
    }

    // Helper method to ensure data directory exists
    private static void ensureDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            if (dataDir.mkdir()) {
                System.out.println("Data directory created successfully.");
            } else {
                System.err.println("Failed to create data directory.");
            }
        }
    }

    // Method to backup data
    public static void backupData() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String backupDir = DATA_DIR + "/backup_" + timestamp;
        new File(backupDir).mkdir();

        try {
            copyFile(new File(DATA_DIR, USERS_FILE), new File(backupDir, USERS_FILE));
            copyFile(new File(DATA_DIR, STUDENTS_FILE), new File(backupDir, STUDENTS_FILE));
            copyFile(new File(DATA_DIR, COURSES_FILE), new File(backupDir, COURSES_FILE));
            copyFile(new File(DATA_DIR, ENROLLMENTS_FILE), new File(backupDir, ENROLLMENTS_FILE));
            copyFile(new File(DATA_DIR, DEPARTMENTS_FILE), new File(backupDir, DEPARTMENTS_FILE));
            System.out.println("Data backup completed successfully.");
        } catch (IOException e) {
            System.err.println("Error during data backup: " + e.getMessage());
        }
    }

    // Helper method to copy files
    private static void copyFile(File source, File dest) throws IOException {
        if (!source.exists()) return;
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }
} 