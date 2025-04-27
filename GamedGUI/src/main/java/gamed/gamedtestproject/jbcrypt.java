package gamed.gamedtestproject;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class jbcrypt{
    public static String hashPassword(String plainTextPassword) {
        // Hash the password
        return BCrypt.withDefaults().hashToString(12, plainTextPassword.toCharArray());
    }

    // Method to check if the password matches the hashed password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword);
        return result.verified;
    }

    public static void main(String[] args) {
        // Example usage
        String password = "mySuperSecretPassword";
        String hashedPassword = hashPassword(password);

        System.out.println("Hashed password: " + hashedPassword);

        // Check if the entered password matches the hashed password
        boolean isPasswordValid = checkPassword(password, hashedPassword);
        System.out.println("Password valid: " + isPasswordValid);
    }
}