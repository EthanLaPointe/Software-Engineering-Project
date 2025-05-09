package model;

public class UserSession {
    private static Account loggedInAccount;

    public static void setLoggedInAccount(Account account) {
        loggedInAccount = account;
    }

    public static Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public static void clearSession() {
        loggedInAccount = null;
    }
}
