package gamed.gamedtestproject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox; // Ensure Label is imported
import model.Account;
import model.UserSession;

public class ProfileController {

    @FXML private VBox wishlistContainer;
    @FXML private VBox favoritesContainer;
    @FXML private VBox reviewsContainer;
    @FXML private VBox profileContainer;
    @FXML private ImageView logoImage;
    @FXML private ImageView profileImage;
    @FXML private Label usernameLabel;
    @FXML private Label dateCreatedLabel;

    private String loggedInUsername;

    Statement statement;
    ResultSet resultSet;
    
    public void setUsername(String username) {
        this.loggedInUsername = username;
        loadProfileData();
    }

    public void initialize() {
        logoImage.setImage((new Image(getClass().getResourceAsStream("/logo.png"))));

        try {
            profileImage.setImage(new Image(getClass().getResourceAsStream("/default_profile.png")));
        } catch (Exception e) {
            System.err.println("Error loading default profile image: " + e.getMessage());
        }
        loadWishlist();
        loadFavorites();
        loadReviews();
        loadProfileData();
    }

    @FXML
    private void logout() throws IOException {
        //TODO add logic for actually logging out user
        App.setRoot("primary");
    }

    @FXML
    private void changeProfilePicture() throws IOException{
        //TODO add logic for uploading images to database
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
       
        for (int i = 0; i < wishlistContainer.getChildren().size(); i++) {
            wishlistContainer.getChildren().add(createGameCard("Game Title", "/path/to/image.jpg"));
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
private void loadProfileData() {
     //Account account = AccountDAO.getAccountByUsername(loggedInUsername);

     Account loggedInUser = UserSession.getLoggedInAccount();

    if (loggedInUser == null) {
        System.out.println("No user is logged in.");
        return;
    }

    String date = loggedInUser.getDateCreated().toString();
    System.out.println("Image path from user: " + loggedInUser.getImagePath());
    VBox profileDataBox = createProfileData(
    loggedInUser.getUsername(),
    date,
    loggedInUser.getImagePath() // assuming this method exists
);

    profileContainer.getChildren().add(profileDataBox);
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

private VBox createProfileData(String username, String dateCreated, String profileImagePath) {
    VBox profileBox = new VBox(10);
    profileBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-background-radius: 5;");

    Label usernameLabel = new Label("Username: " + username);
    Label dateCreatedLabel = new Label("Date Created: " + dateCreated);
    ImageView profileImageView = new ImageView(new Image(getClass().getResourceAsStream(profileImagePath)));

    InputStream stream = getClass().getResourceAsStream("/placeholder.png");
    System.out.println("Stream: " + stream);
    if (stream == null) {
    throw new RuntimeException("Image not found: /placeholder.png");
}
    Image image = new Image(stream);

     //Image image;
    if (profileImagePath == null || getClass().getResourceAsStream(profileImagePath) == null) {
        System.out.println("Profile image path is invalid or missing: " + profileImagePath);
        image = new Image(getClass().getResourceAsStream("/placeholder.png"));
    } else {
        image = new Image(getClass().getResourceAsStream(profileImagePath));
    }

    //ImageView profileImageView = new ImageView(image);

    profileBox.getChildren().addAll(usernameLabel, dateCreatedLabel, profileImageView);
    return profileBox;
}

// Removed duplicate and misplaced code block

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}