package gamed.gamedtestproject;

import java.io.IOException;
<<<<<<< Updated upstream
=======
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
>>>>>>> Stashed changes

import dao.AccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    @FXML private ImageView logoImage;
    @FXML private ImageView profileImage;
    @FXML private Label usernameLabel;
    @FXML private Label dateCreatedLabel;

    Statement statement;
    ResultSet resultSet;
    


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
<<<<<<< Updated upstream
    private void loadProfileData() {
        try {
=======
      private void loadProfileData() {
        Account currentUser = SessionManager.getCurrentUser();
    if (currentUser == null) {
        System.err.println("No user is currently logged in.");
        return;
    }

    // Optionally update the user with full DB data, like profile image
    Account fullAccount = AccountDAO.getAccountByUsername(currentUser.getUsername());
    if (fullAccount == null) {
        System.err.println("User data could not be found in database.");
        return;
    }

    VBox profileData = createProfileData(
        fullAccount.getUsername(),
        fullAccount.getDateCreated()
        //fullAccount.getProfileImagePath()
    );

    profileContainer.getChildren().clear();
    profileContainer.getChildren().add(profileData);
        
        /*try {
>>>>>>> Stashed changes
            // Fetch profile data from the database
            DBConnectionManager.getConnection();
            statement = DBConnectionManager.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Accounts");
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                usernameLabel.setText(username);
                String dateCreated = resultSet.getString("dateCreated");
                dateCreatedLabel.setText(dateCreated);
                String profileImagePath = resultSet.getString("profile_image_path");
                profileImage.setImage(new Image(getClass().getResourceAsStream(profileImagePath)));
                profileContainer.getChildren().add(createProfileData(username, dateCreated, profileImagePath));
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
        */
        
<<<<<<< Updated upstream
        
    }
=======
    }  
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
    private VBox createProfileData(String username, String dateCreated, String profileImagePath) {
=======
    private VBox createProfileDataString username, String dateCreated, String profileImagePath) {

        

        /* Account currentUser  = SessionManager.getCurrentUser();

        //just in case user logins in without username and password
        if (currentUser == null) {
            System.err.println("No user is currently logged in.");
            return new VBox(new Label("No user data available"));
        }
        String username = currentUser.getUsername();
        LocalDate dateCreated = currentUser.getDateCreated();
        //user needs to be able to change this
>>>>>>> Stashed changes

        VBox profileBox = new VBox(10);
        profileBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-background-radius: 5;"); 

        Label usernameLabel = new Label("Username: " + username);
        Label dateCreatedLabel = new Label("Date Created: " + dateCreated);
        ImageView profileImageView = new ImageView(new Image(getClass().getResourceAsStream(profileImagePath))); 

<<<<<<< Updated upstream
        profileBox.getChildren().addAll(usernameLabel, dateCreatedLabel, profileImageView);
        return profileBox;
=======
        profileBox.getChildren().addAll(usernameLabel, dateCreatedLabel);
        return profileBox; */
>>>>>>> Stashed changes
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}