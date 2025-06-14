package gamed.gamedtestproject;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import model.Review;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.HashMap;
import java.util.List;

//Controller to handle individual game scenes
public class IndividualGameController {

    @FXML private Button addToWishlistBtn;
    @FXML private Button addToFavoritesBtn;
    @FXML private Label gameTitle;
    @FXML private TextArea gameDescription;
    @FXML private HBox screenshotsContainer;
    @FXML private VBox reviewsContainer;
    @FXML private HBox ratingStarsContainer;
    @FXML private Button star1;
    @FXML private Button star2;
    @FXML private Button star3;
    @FXML private Button star4;
    @FXML private Button star5;
    @FXML private Label ratingValueLabel;
    @FXML private TextArea reviewTextField;
    @FXML private Button submitReviewBtn;

    private String currentGameId;
    private boolean isInWishlist = false;
    private boolean isInFavorites = false;
    private int currentRating = 0;
    private Button[] starButtons;


    public void setGameData(String gameId, String title) {
        this.currentGameId = gameId;
        int gameIdInt = Integer.parseInt(gameId);
        this.gameTitle.setText(title);
        System.out.println("Game ID: " + gameId);

        loadGameDetails(PrimaryController.handler.GetGameDescription(gameId));
        loadScreenshots(PrimaryController.handler.GetGameScreenshotURLS(gameId));
        loadReviews(PrimaryController.dbConnector.RetrieveGameReviews(gameIdInt));

         Tooltip favoritesTooltip = new Tooltip("Add this game to your favorites");
        Tooltip.install(addToFavoritesBtn, favoritesTooltip);

        // Initialize star rating buttons
        starButtons = new Button[]{star1, star2, star3, star4, star5};
        resetRating();
    }

    @FXML
    public void initialize() {
        // Set up tooltips for buttons
        Tooltip wishlistTooltip = new Tooltip("Add this game to your wishlist");
        Tooltip.install(addToWishlistBtn, wishlistTooltip);

        Tooltip favoritesTooltip = new Tooltip("Add this game to your favorites");
        Tooltip.install(addToFavoritesBtn, favoritesTooltip);

        // Initialize star rating buttons
        starButtons = new Button[]{star1, star2, star3, star4, star5};
        resetRating();
    }

    // Handle star rating
    @FXML
    private void setRating(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Determine which star was clicked
        int selectedRating = 0;
        for (int i = 0; i < starButtons.length; i++) {
            if (clickedButton == starButtons[i]) {
                selectedRating = i + 1;
                break;
            }
        }

        // If same star is clicked twice, reset rating
        if (currentRating == selectedRating) {
            resetRating();
        } else {
            // Set the new rating
            currentRating = selectedRating;
            updateStarDisplay();
        }
    }

    private void resetRating() {
        currentRating = 0;
        updateStarDisplay();
    }

    private void updateStarDisplay() {
        // Update stars display
        for (int i = 0; i < starButtons.length; i++) {
            if (i < currentRating) {
                starButtons[i].setText("★"); // Filled star
            } else {
                starButtons[i].setText("☆"); // Empty star
            }
        }

        // Update rating label
        ratingValueLabel.setText(currentRating + "/5");
    }

    private void loadGameDetails(String descText) {
        //load description from database
        gameDescription.setText(descText);
    }

    @FXML
    private void handleSubmitReview() {
        String reviewText = reviewTextField.getText().trim();

        if (currentRating == 0) {
            showAlert("Rating Required", "Please select a rating before submitting your review.");
            return;
        }

        if (reviewText.isEmpty()) {
            showAlert("Review Required", "Please write a review before submitting.");
            return;
        }

        try {
            // Get current user ID
            int accountId = PrimaryController.accountID;
            int gameId = Integer.parseInt(currentGameId);

            // Submit review to database
            boolean success = PrimaryController.dbConnector.AddReview(accountId, gameId, currentRating, reviewText);

            if (success) {
                // Show success message
                showAlert("Review Submitted", "Your review has been submitted successfully!");

                // Clear the form
                resetRating();
                reviewTextField.clear();

                // Refresh reviews
                loadReviews(PrimaryController.dbConnector.RetrieveGameReviews(gameId));
            } else {
                showAlert("Error", "Failed to submit review. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error submitting review: " + e.getMessage());
            showAlert("Error", "An error occurred while submitting your review: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void addToFavorites() throws IOException, SQLException
    {
        try
        {
            PrimaryController.dbConnector.AddToFavorites(PrimaryController.accountID, currentGameId);
        }
        catch (SQLException e)
        {
            System.out.println("Error adding to favorites: " + e.getMessage());
        }
    }

    @FXML
    private void addToWishlist() throws IOException, SQLException
    {
        try
        {
            PrimaryController.dbConnector.AddToWishlist(PrimaryController.accountID, currentGameId);
        }
        catch (SQLException e)
        {
            System.out.println("Error adding to wishlist: " + e.getMessage());
        }
    }

    private void loadScreenshots(List<String> screenshots) {
        screenshotsContainer.getChildren().clear();

        //Get screenshots from database
        if(screenshots == null || screenshots.isEmpty()) {
            Label noScreenshotsLabel = new Label("No screenshots available.");
            noScreenshotsLabel.setTextAlignment(TextAlignment.CENTER);
            noScreenshotsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            screenshotsContainer.getChildren().add(noScreenshotsLabel);
            return;
        }

        for (String screenshotPath : screenshots) {
            try {
                VBox imageContainer = new VBox();
                imageContainer.setAlignment(Pos.CENTER);

                ImageView imageView = new ImageView();
                imageView.setImage(new Image(screenshotPath));
                imageView.setFitHeight(180);
                imageView.setFitWidth(320);
                imageView.setPreserveRatio(true);
                imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

                imageContainer.getChildren().add(imageView);
                screenshotsContainer.getChildren().add(imageContainer);
            } catch (Exception e) {
                System.out.println("Error loading screenshot: " + screenshotPath);
                System.out.println(e.getMessage());
                // Use a placeholder image instead
                ImageView placeholder = new ImageView();
                placeholder.setImage(new Image(getClass().getResourceAsStream("/placeholder.png")));
                placeholder.setFitHeight(180);
                placeholder.setFitWidth(320);

                screenshotsContainer.getChildren().add(placeholder);
            }
        }
    }

    private void loadReviews(List<Review> reviews) {
        reviewsContainer.getChildren().clear();

        //Add user review to the top of the container
        if (reviews == null || reviews.isEmpty()) {
            Label noReviewsLabel = new Label("No reviews available.");
            noReviewsLabel.setTextAlignment(TextAlignment.CENTER);
            noReviewsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            reviewsContainer.getChildren().add(noReviewsLabel);
            return;
        }

        for (Review review : reviews) {
            try {
                String username = PrimaryController.dbConnector.GetUsernameFromID(review.getAccountID());
                addReview(username, review.getRating(), review.getContents());
            } catch (Exception e) {
                System.out.println("Error loading review: " + e.getMessage());
            }
        }
    }

    private void addReview(String username, int rating, String comment) {
        VBox reviewBox = new VBox(8);
        reviewBox.setStyle("-fx-background-color: #444444; -fx-padding: 15; -fx-background-radius: 5;");

        // Username and star rating in an HBox
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label usernameLabel = new Label(username);
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: gray;");

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
        commentLabel.setStyle("-fx-font-size: 14; -fx-text-fill: black;");
        commentLabel.setWrapText(true);
        commentLabel.setStyle("-fx-text-fill: white;");

        reviewBox.getChildren().addAll(header, commentLabel);
        reviewsContainer.getChildren().add(reviewBox);
    }

    @FXML
    private void goBackToHomepage() throws IOException {
        App.setRoot("secondary");
    }

    // Check if game is in user's wishlist
    private void checkWishlistStatus() {
        updateWishlistButton();
    }

    // Check if game is in user's favorites
    private void checkFavoritesStatus() {
        updateFavoritesButton();
    }

    // Update wishlist button text based on status
    private void updateWishlistButton() {
        if (isInWishlist) {
            addToWishlistBtn.setText("Remove from Wishlist");
        } else {
            addToWishlistBtn.setText("Add to Wishlist");
        }
    }

    // Update favorites button text based on status
    private void updateFavoritesButton() {
        if (isInFavorites) {
            addToFavoritesBtn.setText("Remove from Favorites");
        } else {
            addToFavoritesBtn.setText("Add to Favorites");
        }
    }
}