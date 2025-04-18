package gamed.gamedtestproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

//Controller for the login screen
public class PrimaryController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
