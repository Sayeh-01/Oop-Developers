import java.util.Scanner;

public class MenuSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String LINE = "================================================";
    private static final String DOUBLE_LINE = "================================================";

    // Main menu for all users
    public static void displayMainMenu() {
        System.out.println(DOUBLE_LINE);
        System.out.println("          COURSE MANAGEMENT SYSTEM");
        System.out.println(DOUBLE_LINE);
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println(LINE);
    }

    // Student menu
    public static void displayStudentMenu(Student student) {
        System.out.println(DOUBLE_LINE);
        System.out.println("          STUDENT MENU - " + student.getName());
        System.out.println(DOUBLE_LINE);
        System.out.println("1. View Available Courses");
        System.out.println("2. Register for Course");
        System.out.println("3. Drop Course");
        System.out.println("4. View Schedule");
        System.out.println("5. View Grades");
        System.out.println("6. View Academic Records");
        System.out.println("7. View Notifications");
        System.out.println("8. Logout");
        System.out.println(LINE);
    }

    // Faculty menu
    public static void displayFacultyMenu(Faculty faculty) {
        System.out.println(DOUBLE_LINE);
        System.out.println("          FACULTY MENU - " + faculty.getName());
        System.out.println(DOUBLE_LINE);
        System.out.println("1. View Teaching Schedule");
        System.out.println("2. Manage Course");
        System.out.println("3. View Student Roster");
        System.out.println("4. Create Assignment");
        System.out.println("5. Enter Grades");
        System.out.println("6. View Workload Summary");
        System.out.println("7. View Notifications");
        System.out.println("8. Logout");
        System.out.println(LINE);
    }

    // Admin menu
    public static void displayAdminMenu(AdminStaff admin) {
        System.out.println(DOUBLE_LINE);
        System.out.println("          ADMIN MENU - " + admin.getName());
        System.out.println(DOUBLE_LINE);
        System.out.println("1. Register Student");
        System.out.println("2. Create Course");
        System.out.println("3. Assign Faculty to Course");
        System.out.println("4. Generate Reports");
        System.out.println("5. View All Courses");
        System.out.println("6. View All Faculty");
        System.out.println("7. View All Students");
        System.out.println("8. Logout");
        System.out.println(LINE);
    }

    // Method to display a formatted table header
    public static void displayTableHeader(String[] headers) {
        System.out.println(DOUBLE_LINE);
        for (String header : headers) {
            System.out.printf("%-20s", header);
        }
        System.out.println();
        System.out.println(LINE);
    }

    // Method to display a formatted table row
    public static void displayTableRow(String[] values) {
        for (String value : values) {
            System.out.printf("%-20s", value);
        }
        System.out.println();
    }

    // Method to display a formatted table footer
    public static void displayTableFooter() {
        System.out.println(DOUBLE_LINE);
    }

    // Method to display a success message
    public static void displaySuccessMessage(String message) {
        System.out.println("\n✅ " + message + "\n");
    }

    // Method to display an error message
    public static void displayErrorMessage(String message) {
        System.out.println("\n❌ " + message + "\n");
    }

    // Method to display an info message
    public static void displayInfoMessage(String message) {
        System.out.println("\nℹ️ " + message + "\n");
    }

    // Method to get user input with prompt
    public static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Method to get integer input with prompt
    public static int getIntegerInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayErrorMessage("Please enter a valid number.");
            }
        }
    }

    // Method to get double input with prompt
    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayErrorMessage("Please enter a valid number.");
            }
        }
    }

    // Method to display a confirmation prompt
    public static boolean getConfirmation(String prompt) {
        while (true) {
            String input = getUserInput(prompt + " (y/n): ").toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            displayErrorMessage("Please enter 'y' or 'n'.");
        }
    }

    // Method to display a loading message
    public static void displayLoading(String message) {
        System.out.print("\r" + message + " ");
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    // Method to clear the console
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print some newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
} 