import java.util.Scanner;

public abstract class User {
    // Fields
    private String userId;
    private String userName;
    private String password;
    private String name;
    private String email;
    private String contactInfo;
    private static String currentUser = null;
    protected static int count;

    public User(String contactInfo, String email, String name, String userId) {
        this.contactInfo = contactInfo;
        this.email = email;
        this.name = name;
        this.userId = userId;
    }


    // Abstract methods
    public abstract boolean login();

    // Authentication methods
    protected static boolean logout() {
        if (currentUser == null) {
            System.out.println("No user is currently logged in!");
            return false;
        }
        System.out.println("Logging out user: " + currentUser);
        currentUser = null;
        return true;
    }

    // Profile management
    public void updateProfile(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("No user is logged in!");
            return;
        }

        while (true) {
            System.out.println("\nProfile Update Menu for: " + currentUser);
            System.out.println("1. Update Username");
            System.out.println("2. Update Password");
            System.out.println("3. Update Name");
            System.out.println("4. Update Email");
            System.out.println("5. Update Contact Information");
            System.out.println("6. Return to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    updateUsername(scanner);
                    break;
                case "2":
                    updatePassword(scanner);
                    break;
                case "3":
                    updateName(scanner);
                    break;
                case "4":
                    updateEmail(scanner);
                    break;
                case "5":
                    updateContactInfo(scanner);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Private helper methods for profile updates
    private void updateUsername(Scanner scanner) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine().trim();
        
        // Check if username is empty
        if (newUsername.isEmpty()) {
            System.out.println("Username cannot be empty!");
            return;
        }

        // Check if username is the same as current username
        if (newUsername.equals(currentUser)) {
            System.out.println("This is already your current username!");
            return;
        }

        // Update the username
        this.userName = newUsername;
        currentUser = newUsername;
        System.out.println("Username updated successfully!");
    }

    private void updatePassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        
        if (newPassword.isEmpty()) {
            System.out.println("Password cannot be empty!");
            return;
        }

        if (!isValidPassword(newPassword)) {
            System.out.println("Password must meet the following requirements:");
            System.out.println("- At least 6 characters long");
            System.out.println("- At least one capital letter");
            System.out.println("- At least one number");
            System.out.println("- At least one symbol");
            return;
        }

        this.password = newPassword;
        System.out.println("Password updated successfully!");
    }

    private boolean isValidPassword(String password) {
        // Check length first
        if (password.length() < 6) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSymbol = true;
            }
        }

        return hasCapital && hasNumber && hasSymbol;
    }

    private void updateName(Scanner scanner) {
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }
        this.name = newName;
        System.out.println("Name updated successfully!");
    }

    private void updateEmail(Scanner scanner) {
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine().trim();
        if (newEmail.isEmpty()) {
            System.out.println("Email cannot be empty!");
            return;
        }
        if (!isValidEmail(newEmail)) {
            System.out.println("Invalid email format!");
            return;
        }
        this.email = newEmail;
        System.out.println("Email updated successfully!");
    }

    private void updateContactInfo(Scanner scanner) {
        System.out.print("Enter new contact information: ");
        String newContactInfo = scanner.nextLine().trim();
        if (newContactInfo.isEmpty()) {
            System.out.println("Contact information cannot be empty!");
            return;
        }
        this.contactInfo = newContactInfo;
        System.out.println("Contact information updated successfully!");
    }

    // Validation methods
    private boolean isUsernameTaken(String username) {
        return username.equals(userName);
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z.-]+\\.[A-Za-z]{2,6}$");
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        User.currentUser = currentUser;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        User.count = count;
    }
}


