package gamed.gamedtestproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
public class PrimaryController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ImageView logo;
    @FXML private ImageView background;
    @FXML private StackPane rootPane;

    // Create Account Dialog Components
    @FXML private VBox createAccountDialog;
    @FXML private TextField newUsernameField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorMessageLabel;

    @FXML
    public void initialize() {
        try {
            // Load background image with error handling
            Image backgroundImage = new Image(getClass().getResourceAsStream("/background.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException());
            } else {
                background.setImage(backgroundImage);
            }

            // Load logo image with error handling
            Image logoImage = new Image(getClass().getResourceAsStream("/logo.png"));
            if (logoImage.isError()) {
                System.err.println("Error loading logo image: " + logoImage.getException());
            } else {
                logo.setImage(logoImage);
            }

            background.fitWidthProperty().bind(rootPane.widthProperty());
            background.fitHeightProperty().bind(rootPane.heightProperty());
            background.setPreserveRatio(false);

        } catch (Exception e) {
            System.err.println("Error initializing controller: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void showCreateAccountDialog() {
        // Clear previous inputs
        newUsernameField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
        errorMessageLabel.setText("");

        // Show the dialog with fade-in animation
        createAccountDialog.setOpacity(0);
        createAccountDialog.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), createAccountDialog);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @FXML
    private void cancelCreateAccount() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), createAccountDialog);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> createAccountDialog.setVisible(false));
        fadeOut.play();
    }

    @FXML
    private void createAccount() {
        // Get the input values
        String username = newUsernameField.getText().trim();
        String password = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        Connection connection = null;

        // Validate the input
        if (username.isEmpty()) {
            errorMessageLabel.setText("Username cannot be empty");
            return;
        }
        if (password.isEmpty()) {
            errorMessageLabel.setText("Password cannot be empty");
            return;
        }
        if (!password.equals(confirmPassword)) {
            errorMessageLabel.setText("Passwords do not match");
            return;
        }

        if (password.length() < 8 && !containsSpecialCharacter(password) && !containsUpperCase(password)){
            errorMessageLabel.setText("Passwords must contain a special character(!,@,#,$,%,&,*), " +
                    "be at least 8 characters in length, and contain an uppercase letter");
            return;
        }


        // TODO: Add code to actually create the account in a database or file
        connection = DBConnectionManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select * from Accounts where username = '"  + username + "'");
            if (rs.next()){
                errorMessageLabel.setText("An account with that username already exists");
                return;
            }
            String insertString = "insert into accounts (username, password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            statement.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Account created for user: " + username);

        // Close the dialog
        cancelCreateAccount();
    }

    public boolean containsSpecialCharacter(String str){
        return str.contains("*") || str.contains("!")
                || str.contains("@") || str.contains("#")
                || str.contains("$") || str.contains("%")
                || str.contains("&");
    }

    public boolean containsUpperCase(String str){
        boolean contains = false;
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if(Character.isUpperCase(ch)){
                contains = true;
                break;
            }
        }
        return contains;
    }
}