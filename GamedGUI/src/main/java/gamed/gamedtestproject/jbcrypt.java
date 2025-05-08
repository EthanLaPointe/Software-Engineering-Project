package gamed.gamedtestproject;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class jbcrypt{

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.withDefaults().hashToString(12, plainTextPassword.toCharArray());
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword).verified;
    }


//used for testing
    /* public static void main(String[] args) {
        // Example usage
        String password = "mySuperSecretPassword";
        String hashedPassword = hashPassword(password);

        System.out.println("Hashed password: " + hashedPassword);

        // Check if the entered password matches the hashed password
        boolean isPasswordValid = checkPassword(password, hashedPassword);
        System.out.println("Password valid: " + isPasswordValid);
    } */
}