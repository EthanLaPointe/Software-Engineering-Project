package gamed.gamedtestproject;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Connection connection; // Assume this is initialized somewhere

    @FXML
    private void handleRegister() {
        try {
            String username = usernameField.getText();
            String plainPassword = passwordField.getText();
            
            // Hash the password
            String hashedPassword = jbcrypt.hashPassword(plainPassword);

            // Save to database
            String sql = "INSERT INTO accounts (username, password) VALUES (?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();

            System.out.println("User registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}