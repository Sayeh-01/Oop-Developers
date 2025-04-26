import java.util.ArrayList;
import java.util.Scanner;

public class SystemAdmin extends User {

    protected String adminID;
    protected int securityLevel;

    private static ArrayList<User> systemUsers = new ArrayList<>();
    private static ArrayList<Student> systemStudents = new ArrayList<>();
    private static ArrayList<CourseGrade> systemCourses = new ArrayList<>();

    public SystemAdmin(String adminID, int securityLevel, String userId, String name, String email, String contactInfo) {
        super(contactInfo, email, name, userId);
        this.adminID = adminID;
        this.securityLevel = securityLevel;
    }

    @Override
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (getUserName().equals(username) && getPassword().equals(password)) {
            setCurrentUser(username);
            System.out.println("Login successful! Welcome, " + getName());
            return true;
        }
        System.out.println("Invalid username or password!");
        return false;
    }

    public void createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (systemUsers.contains(user)) {
            System.out.println("User " + user.getName() + " already exists in the system.");
            return;
        }

        systemUsers.add(user);
        System.out.println("User " + user.getName() + " has been created successfully.");
    }

    public void modifySettings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- System Settings ---");
        System.out.println("1. Change Security Level");
        System.out.println("2. Update System Configuration");
        System.out.println("3. Manage User Permissions");
        System.out.println("4. Return to Main Menu");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                modifySecurityLevel(scanner);
                break;
            case 2:
                updateSystemConfig(scanner);
                break;
            case 3:
                manageUserPermissions(scanner);
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void modifySecurityLevel(Scanner scanner) {
        System.out.print("Enter new security level (1-5): ");
        int newLevel = scanner.nextInt();
        if (newLevel >= 1 && newLevel <= 5) {
            this.securityLevel = newLevel;
            System.out.println("Security level updated to: " + newLevel);
        } else {
            System.out.println("Invalid security level! Must be between 1 and 5.");
        }
    }

    private void updateSystemConfig(Scanner scanner) {
        System.out.println("\n--- System Configuration ---");
        System.out.println("1. Update System Name");
        System.out.println("2. Update System Description");
        System.out.println("3. Update System Contact");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter new system name: ");
                String name = scanner.nextLine();
                System.out.println("System name updated to: " + name);
                break;
            case 2:
                System.out.print("Enter new system description: ");
                String desc = scanner.nextLine();
                System.out.println("System description updated.");
                break;
            case 3:
                System.out.print("Enter new system contact: ");
                String contact = scanner.nextLine();
                System.out.println("System contact updated to: " + contact);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void manageUserPermissions(Scanner scanner) {
        System.out.println("\n--- User Permissions Management ---");
        System.out.print("Enter username to manage: ");
        String username = scanner.nextLine();
        
        User user = findUser(username);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("1. Grant Admin Access");
        System.out.println("2. Revoke Admin Access");
        System.out.println("3. Set User Role");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Admin access granted to " + username);
                break;
            case 2:
                System.out.println("Admin access revoked from " + username);
                break;
            case 3:
                System.out.print("Enter new role: ");
                String role = scanner.nextLine();
                System.out.println("Role updated to: " + role);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private User findUser(String username) {
        for (User user : systemUsers) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void backupData() {
        System.out.println("\n--- System Backup ---");
        System.out.println("Backing up user data...");
        for (User user : systemUsers) {
            System.out.println("User: " + user.getName() + " (ID: " + user.getUserId() + ")");
        }

        System.out.println("\nBacking up student data...");
        for (Student student : systemStudents) {
            System.out.println("Student: " + student.getName() + " (ID: " + student.getStudentID() + ")");
        }

        System.out.println("\nBacking up course data...");
        for (CourseGrade course : systemCourses) {
            System.out.println("Course: " + course.getTitle() + " (ID: " + course.getCourseId() + ")");
        }

        System.out.println("\nBackup completed successfully!");
    }

    public void managePermissions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Permission Management ---");
        System.out.println("1. View User Permissions");
        System.out.println("2. Modify User Permissions");
        System.out.println("3. Set Default Permissions");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                viewUserPermissions();
                break;
            case 2:
                modifyUserPermissions(scanner);
                break;
            case 3:
                setDefaultPermissions();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void viewUserPermissions() {
        System.out.println("\n--- User Permissions ---");
        for (User user : systemUsers) {
            System.out.println("User: " + user.getName());
            System.out.println("Role: " + (user instanceof SystemAdmin ? "System Admin" : 
                                        user instanceof AdminStaff ? "Admin Staff" : 
                                        user instanceof Faculty ? "Faculty" : "Student"));
            System.out.println("------------------------------");
        }
    }

    private void modifyUserPermissions(Scanner scanner) {
        System.out.print("Enter username to modify: ");
        String username = scanner.nextLine();
        
        User user = findUser(username);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("1. Grant Full Access");
        System.out.println("2. Grant Read-Only Access");
        System.out.println("3. Revoke Access");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Full access granted to " + username);
                break;
            case 2:
                System.out.println("Read-only access granted to " + username);
                break;
            case 3:
                System.out.println("Access revoked from " + username);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void setDefaultPermissions() {
        System.out.println("Setting default permissions for all users...");
        System.out.println("Default permissions have been set successfully.");
    }

    // Getters and Setters
    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        if (securityLevel < 1 || securityLevel > 5) {
            throw new IllegalArgumentException("Security level must be between 1 and 5");
        }
        this.securityLevel = securityLevel;
    }

    public static ArrayList<User> getSystemUsers() {
        return new ArrayList<>(systemUsers);
    }

    public static ArrayList<Student> getSystemStudents() {
        return new ArrayList<>(systemStudents);
    }

    public static ArrayList<CourseGrade> getSystemCourses() {
        return new ArrayList<>(systemCourses);
    }
}

