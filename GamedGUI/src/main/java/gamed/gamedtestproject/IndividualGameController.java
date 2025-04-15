package gamed.gamedtestproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;

//Controller to handle individual game scenes
public class IndividualGameController {

    @FXML private Label gameTitle;
    @FXML private TextArea gameDescription;
    @FXML private HBox screenshotsContainer;
    @FXML private VBox reviewsContainer;

    private String currentGameId;

    public void setGameData(String gameId, String title) {
        this.currentGameId = gameId;
        this.gameTitle.setText(title);

        // Load game data from database
        loadGameDetails();
        //loadScreenshots();
        //loadReviews();
    }

    @FXML
    public void initialize() {
    }

    private void loadGameDetails() {
        //load description from database
        gameDescription.setText("This is a sample description for the game.");
    }

    private void loadScreenshots() {
        //Get screenshots from database
        String[] screenshots = {};

        for (String screenshotPath : screenshots) {
            try {
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(getClass().getResourceAsStream(screenshotPath)));
                imageView.setFitHeight(180);
                imageView.setFitWidth(320);
                imageView.setPreserveRatio(true);

                screenshotsContainer.getChildren().add(imageView);
            } catch (Exception e) {
                System.out.println("Error loading screenshot: " + screenshotPath);
                // Use a placeholder image instead
                ImageView placeholder = new ImageView();
                placeholder.setImage(new Image(getClass().getResourceAsStream("/placeholder.jpg")));
                placeholder.setFitHeight(180);
                placeholder.setFitWidth(320);

                screenshotsContainer.getChildren().add(placeholder);
            }
        }
    }

    private void loadReviews() {
        //Get reviews from database, check if the user has a review and place that one at the top of the container
    }

    private void addReview(String username, int rating, String comment) {
        VBox reviewBox = new VBox(5);
        reviewBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-background-radius: 5;");

        // Username and star rating in an HBox
        HBox header = new HBox(10);
        Label usernameLabel = new Label(username);
        usernameLabel.setStyle("-fx-font-weight: bold;");

        // Create star rating
        HBox starsBox = new HBox(2);
        for (int i = 1; i <= 5; i++) {
            Label star = new Label(i <= rating ? "★" : "☆");
            star.setStyle("-fx-text-fill: " + (i <= rating ? "gold" : "gray") + "; -fx-font-size: 16;");
            starsBox.getChildren().add(star);
        }

        header.getChildren().addAll(usernameLabel, starsBox);

        // Comment
        Label commentLabel = new Label(comment);
        commentLabel.setWrapText(true);

        reviewBox.getChildren().addAll(header, commentLabel);
        reviewsContainer.getChildren().add(reviewBox);
    }

    @FXML
    private void goBackToHomepage() throws IOException {
        App.setRoot("secondary");
    }
}