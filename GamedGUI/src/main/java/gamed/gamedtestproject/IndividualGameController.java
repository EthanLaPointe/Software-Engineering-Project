package gamed.gamedtestproject;

import java.io.IO;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import model.Review;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;

import java.util.HashMap;
import java.util.List;

//Controller to handle individual game scenes
public class IndividualGameController {

    @FXML private Label gameTitle;
    @FXML private TextArea gameDescription;
    @FXML private HBox screenshotsContainer;
    @FXML private VBox reviewsContainer;
    @FXML private Button addToWishlistBtn;
    @FXML private Button addToFavoritesBtn;

    private String currentGameId;
    private boolean isInWishlist = false;
    private boolean isInFavorites = false;

    public void setGameData(String gameId, String title) {
        this.currentGameId = gameId;
        int gameIdInt = Integer.parseInt(gameId);
        this.gameTitle.setText(title);
        System.out.println("Game ID: " + gameId);

        loadGameDetails(PrimaryController.handler.GetGameDescription(gameId));
        loadScreenshots(PrimaryController.handler.GetGameScreenshotURLS(gameId));
        loadReviews(PrimaryController.dbConnector.RetrieveGameReviews(gameIdInt));

        // Check if game is in user's wishlist or favorites
        checkWishlistStatus();
        checkFavoritesStatus();
    }

    @FXML
    public void initialize() {
        // Set up tooltips for buttons
        Tooltip wishlistTooltip = new Tooltip("Add this game to your wishlist");
        Tooltip.install(addToWishlistBtn, wishlistTooltip);

        Tooltip favoritesTooltip = new Tooltip("Add this game to your favorites");
        Tooltip.install(addToFavoritesBtn, favoritesTooltip);
    }

    private void loadGameDetails(String descText) {
        //load description from database
        gameDescription.setText(descText);
    }

    @FXML
    private void handleSubmitReview()
    {

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
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

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

    @FXML
    public void addToWishlist() {
        // Toggle wishlist status
        isInWishlist = !isInWishlist;

        // TODO: Actually add/remove from database
        // if (isInWishlist) {
        //     PrimaryController.dbConnector.addToWishlist(currentGameId, userId);
        // } else {
        //     PrimaryController.dbConnector.removeFromWishlist(currentGameId, userId);
        // }

        updateWishlistButton();
    }

    @FXML
    public void addToFavorites() {
        // Toggle favorites status
        isInFavorites = !isInFavorites;

        // TODO: Actually add/remove from database
        // if (isInFavorites) {
        //     PrimaryController.dbConnector.addToFavorites(currentGameId, userId);
        // } else {
        //     PrimaryController.dbConnector.removeFromFavorites(currentGameId, userId);
        // }

        updateFavoritesButton();
    }
}