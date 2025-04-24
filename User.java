import java.util.ArrayList;
import java.util.Scanner;

public abstract class User {
    protected String userId;
    protected ArrayList <String> userName=new ArrayList<>();
    private ArrayList <String> password=new ArrayList<>();
    protected String name;
    protected String email;
    protected String contactInfo;
    private static String currentUser = null;
    public int count;


    public abstract boolean login();

    protected static void logout() {
        currentUser = null;
    }
    public  void updateProfile(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("No user is logged in!");
            return;
        }

        System.out.println("Profile Update for: " + currentUser);
        System.out.println("1. Update Username");
        System.out.println("2. Update Password");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty()) {
                // Check if new username already exists
                boolean usernameExists = false;
                for (String username : userName) {
                    if (username.equals(newUsername)) {
                        usernameExists = true;
                        break;
                    }
                }
                if (!usernameExists) {
                    userName.set(count,newUsername);
                    currentUser = newUsername; // Update currentUser
                    System.out.println("Username updated successfully!");
                } else {
                    System.out.println("Username already taken!");
                }
            } else {
                System.out.println("Username cannot be empty!");
            }
        } else if (choice.equals("2")) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                password.set(count, newPassword);
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Password cannot be empty!");
            }
        } else {
            System.out.println("Invalid option!");
        }
    }

    public ArrayList<String> getPassword() {
        return password;
    }

    public void setPassword(ArrayList<String> password) {
        this.password = password;
    }
    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        User.currentUser = currentUser;
    }

}


