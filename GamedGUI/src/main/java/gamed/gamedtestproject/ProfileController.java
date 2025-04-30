package gamed.gamedtestproject;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Label; // Ensure Label is imported
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileController {

    @FXML private VBox wishlistContainer;
    @FXML private VBox favoritesContainer;
    @FXML private VBox reviewsContainer;
    @FXML private VBox profileContainer;
    Statement statement;
    ResultSet resultSet;


    public void initialize() {
        loadWishlist();
        loadFavorites();
        loadReviews();
    }
    private void loadWishlist() { 
        try {
            // Fetch wishlist data from the database
            DBConnectionManager.getConnection();
            statement = DBConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM WishLists");
            while (resultSet.next()) {
                String gameTitle = resultSet.getString("game_title");
                String imagePath = resultSet.getString("image_path");
                wishlistContainer.getChildren().add(createGameCard(gameTitle, imagePath));
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
       
    }

    private void loadFavorites() {
        try {
            // Fetch favorite games from the database
            DBConnectionManager.getConnection();
            statement = DBConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM FavGames");
            while (resultSet.next()) {
                String gameTitle = resultSet.getString("game_title");
                String imagePath = resultSet.getString("image_path");
                favoritesContainer.getChildren().add(createGameCard(gameTitle, imagePath));
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }

    private void loadReviews() {
        try {
            // Fetch reviews from the database
            DBConnectionManager.getConnection();
            statement = DBConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Reviews");
            while (resultSet.next()) {
                String gameTitle = resultSet.getString("game_title");
                int rating = resultSet.getInt("rating");
                String comment = resultSet.getString("comment");
                addReview(gameTitle, rating, comment);
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
        
    }
    private void getProfileData() {
        try {
            // Fetch profile data from the database
            DBConnectionManager.getConnection();
            statement = DBConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Accounts");
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String DateCreated = resultSet.getString("dateCreated");
                String profileImagePath = resultSet.getString("profile_image_path");

                // Set profile data in the UI
                Label usernameLabel = new Label(username);
                Label emailLabel = new Label(email);
                ImageView profileImageView = new ImageView(new Image(getClass().getResourceAsStream(profileImagePath)));

                profileContainer.getChildren().addAll(usernameLabel, emailLabel, profileImageView);
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    
        
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