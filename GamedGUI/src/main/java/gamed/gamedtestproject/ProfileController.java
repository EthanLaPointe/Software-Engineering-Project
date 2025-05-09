package gamed.gamedtestproject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert; // Ensure Label is imported
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.Account;
import model.Review;
import proto.Game;
import model.SessionManager;
import java.sql.Connection;
import javafx.fxml.FXMLLoader;
import java.io.File;

public class ProfileController {

    @FXML private VBox wishlistContainer;
    @FXML private VBox favoritesContainer;
    @FXML private VBox reviewsContainer;
    @FXML private VBox profileDetailsContainer;
    @FXML private ImageView logoImage;
    @FXML private ImageView profileImage;
    @FXML private Label usernameLabel;
    @FXML private Label dateCreatedLabel;
    

    Statement statement;
    ResultSet resultSet;
    


    public void initialize() {
        logoImage.setImage((new Image(getClass().getResourceAsStream("/logo.png"))));

        try {
            if(PrimaryController.dbConnector.getImagePath(PrimaryController.accountID) != null) {
                profileImage.setImage(new Image(PrimaryController.dbConnector.getImagePath(PrimaryController.accountID)));
            } else
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
    private void changeProfilePicture() throws IOException {

        String imagePath = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(profileImage.getScene().getWindow());

        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            profileImage.setImage(new Image(imagePath));
            try {
            PrimaryController.dbConnector.UpdateUserImagePath(selectedFile.toURI().toString(), PrimaryController.accountID);
            } catch (SQLException e) {
                System.err.println("Error updating user image path: " + e.getMessage());
                showAlert("Database Error", "Failed to update profile picture. Please try again.");
            }
            
        } else {
            showAlert("No file selected", "Please select a valid image file.");
        }
    }
  

    private void loadWishlist() { 
        List<Game> wishlist = new ArrayList<>();
        try {
            // Fetch wishlist data from the database
            DBConnectionManager.getConnection();
            List<String> wishlistIDs = PrimaryController.dbConnector.retrieveUserWishlist(PrimaryController.accountID);
            wishlist = PrimaryController.handler.RetrieveWishlist(wishlistIDs);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
       
        if(wishlist == null || wishlist.isEmpty()) {
            Label noWishlistLabel = new Label("No games in wishlist.");
            noWishlistLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            wishlistContainer.getChildren().add(noWishlistLabel);
            return;
        }

        for (Game game : wishlist) {
            Long gameID = game.getId();
            String imageURL = PrimaryController.handler.GetGameCoverImageURL(gameID.toString());

            if(imageURL == null || imageURL.isEmpty()) {
                imageURL = "placeholder.png"; // Use a default image if none is found
            }
            wishlistContainer.getChildren().add(createGameCard(gameID.toString(), imageURL));
        }
    }

    private void loadFavorites() {
        List<Game> favorites = new ArrayList<>();
        try {
            // Fetch wishlist data from the database
            DBConnectionManager.getConnection();
            List<String> favoriteIDs = PrimaryController.dbConnector.retrieveUserFavorites(PrimaryController.accountID);
            favorites = PrimaryController.handler.RetrieveWishlist(favoriteIDs);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
       
        if(favorites == null || favorites.isEmpty()) {
            Label noWishlistLabel = new Label("No games in favorites.");
            noWishlistLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            wishlistContainer.getChildren().add(noWishlistLabel);
            return;
        }

        for (Game game : favorites) {
            Long gameID = game.getId();
            String imageURL = PrimaryController.handler.GetGameCoverImageURL(gameID.toString());

            if(imageURL == null || imageURL.isEmpty()) {
                imageURL = "placeholder.png"; // Use a default image if none is found
            }
            favoritesContainer.getChildren().add(createGameCard(gameID.toString(), imageURL));
        }
    }

    private void loadReviews() {
        try {
            // Fetch reviews from the database
            List<Review> reviews = PrimaryController.dbConnector.retrieveUserReviews(PrimaryController.accountID);
            for (Review review : reviews) {
                Integer id = review.getGameId();
                String gameID = id.toString();
                String gameTitle = PrimaryController.handler.RetrieveGameByID(gameID).getName();
                System.out.println("game title: " + gameTitle);
                addReview(gameID, gameTitle, review.getRating(), review.getContents());
            }
        } catch (Exception e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
        
    }
     private void loadProfileData() {
        
        try{
            DBConnectionManager.getConnection();
            String currentUserID = String.valueOf(PrimaryController.accountID);
            String sql = "SELECT username, dateCreated FROM Accounts WHERE account_id = ?";
            PreparedStatement preparedStatement = DBConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, currentUserID);  
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String dateCreated = resultSet.getString("dateCreated");
                 usernameLabel.setText("Username: " + username);
                dateCreatedLabel.setText("Date Created: " + dateCreated);
                createProfileData(username, dateCreated);
            } else {
                System.err.println("No user data found.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error loading profile data: " + e.getMessage());
        }
    } 

    private VBox createGameCard(String id, String imagePath) {
        VBox card = new VBox(10);
        card.setPrefWidth(120);

        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(imagePath));
        } catch (Exception e) {
            // If image not found, use a placeholder
            imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.jpg")));
        }

        imageView.setFitWidth(120);
        imageView.setFitHeight(160);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(PrimaryController.handler.RetrieveGameByID(id).getName());
        titleLabel.setWrapText(true);

        card.getChildren().addAll(imageView, titleLabel);

        // Add click event
        card.setOnMouseClicked(event -> openGameDetails(id));

        return card;
    }

    private void addReview(String gameID, String gameTitle, int rating, String comment) {
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
        viewGameLink.setOnMouseClicked(event -> openGameDetails(gameID));

        reviewBox.getChildren().addAll(titleLabel, starsBox, commentLabel, viewGameLink);
        reviewsContainer.getChildren().add(reviewBox);
    }

    private VBox createProfileData(String username, String dateCreated) {

        Account currentUser  = SessionManager.getCurrentUser();

        //just in case user logins in without username and password
        if (currentUser == null) {
            System.err.println("No user is currently logged in.");
            return new VBox(new Label("No user data available"));
        }
        username = currentUser.getUsername();
        dateCreated = currentUser.getDateCreated();
        //user needs to be able to change this

        VBox profileBox = new VBox(10);
        profileBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-background-radius: 5;"); 

        Label usernameLabel = new Label("Username: " + username);
        Label dateCreatedLabel = new Label("Date Created: " + dateCreated);

        profileBox.getChildren().addAll(usernameLabel, dateCreatedLabel);
        return profileBox;
    }

    private void openGameDetails(String gameId) {
        try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent gameView = loader.load();

            // Get the controller
            IndividualGameController controller = loader.getController();

            // Set the game data (in a real app, you would pass an ID and/or more data)
            controller.setGameData(gameId, PrimaryController.handler.RetrieveGameByID(gameId).getName());

            // Set the new scene
            Scene scene = App.getScene();
            scene.setRoot(gameView);
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