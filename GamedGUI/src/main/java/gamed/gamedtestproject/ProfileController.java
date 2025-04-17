package gamed.gamedtestproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileController {

    @FXML private VBox wishlistContainer;
    @FXML private VBox favoritesContainer;
    @FXML private VBox reviewsContainer;

    @FXML
    public void initialize() {

        //loadWishlist();
        //loadFavorites();
        //loadReviews();
    }

    private void loadWishlist() {
    }

    private void loadFavorites() {
    }

    private void loadReviews() {
    }

    private VBox createGameCard(String title, String imagePath) {
        VBox card = new VBox(10);
        card.setPrefWidth(120);

        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        } catch (Exception e) {
            // If image not found, use a placeholder
            imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.jpg")));
        }

        imageView.setFitWidth(120);
        imageView.setFitHeight(160);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);

        card.getChildren().addAll(imageView, titleLabel);

        // Add click event
        card.setOnMouseClicked(event -> openGameDetails(title));

        return card;
    }

    private void addReview(String gameTitle, int rating, String comment) {
        VBox reviewBox = new VBox(5);
        reviewBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-background-radius: 5;");

        // Game title
        Label titleLabel = new Label(gameTitle);
        titleLabel.setStyle("-fx-font-weight: bold;");

        // Star rating
        HBox starsBox = new HBox(2);
        for (int i = 1; i <= 5; i++) {
            Label star = new Label(i <= rating ? "★" : "☆");
            star.setStyle("-fx-text-fill: " + (i <= rating ? "gold" : "gray") + "; -fx-font-size: 16;");
            starsBox.getChildren().add(star);
        }

        // Comment
        Label commentLabel = new Label(comment);
        commentLabel.setWrapText(true);

        // Add a link to view the game
        Label viewGameLink = new Label("View Game >");
        viewGameLink.setStyle("-fx-text-fill: blue; -fx-cursor: hand;");
        viewGameLink.setOnMouseClicked(event -> openGameDetails(gameTitle));

        reviewBox.getChildren().addAll(titleLabel, starsBox, commentLabel, viewGameLink);
        reviewsContainer.getChildren().add(reviewBox);
    }

    private void openGameDetails(String gameTitle) {
        try {
            App.setRoot("game");
        } catch (IOException e) {
            System.err.println("Error loading game view: " + e.getMessage());
        }
    }

    @FXML
    private void goBackToHomepage() throws IOException {
        App.setRoot("secondary");
    }
}