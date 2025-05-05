package model;

public class SessionManager {
    private static Account currentUser;

    public static void setCurrentUser(Account user) {
        currentUser = user;
    }

    public static Account getCurrentUser() {
        return currentUser;
    }
}
