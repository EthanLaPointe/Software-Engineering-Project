package gamed.gamedtestproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

//Controller for the homepage
public class SecondaryController {
    
    @FXML private ImageView profileButton;
    @FXML private TextField searchField;
    @FXML private HBox featuredGamesContainer;
    
    @FXML
    public void initialize() {
        // Set profile image
        profileButton.setImage(new Image(getClass().getResourceAsStream("/default_profile.png")));
        
        // Create game cards for each carousel
        populateFeaturedGames();
    }
    
    private void populateFeaturedGames() {
        //TODO Retreive this stuff from database
        String[] games = {"Game 1", "Game 2", "Game 3", "Game 4", "Game 5"};
        String[] images = {"/game1.png", "/game2.jpg", "/game3.jpg", "/game4.jpg", "/game5.jpg"};
        
        for (int i = 0; i < games.length; i++) {
            featuredGamesContainer.getChildren().add(createGameCard(games[i], images[i]));
        }
    }
    
    
    private VBox createGameCard(String title, String imagePath) {
        VBox card = new VBox(10);
        card.setPrefWidth(150);
        
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        } catch (Exception e) {
            // If image not found, use a placeholder
            imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.png")));
        }

        imageView.setFitWidth(150);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        VBox.setVgrow(imageView,Priority.ALWAYS);
        card.setPrefWidth(Region.USE_COMPUTED_SIZE);
        
        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label(title);
        
        card.getChildren().addAll(imageView, titleLabel);
        
        // Add click event
        card.setOnMouseClicked(event -> openGameDetails(title));
        
        return card;
    }
    
    @FXML
    private void openProfilePage() throws IOException {
        //TODO Add profile Page
        // Navigate to profile page
        App.setRoot("profile");
    }
    
    @FXML
    private void searchGames() {
        //TODO add logic for searching and switching to game specific page
        String query = searchField.getText();
    }
    
    private void openGameDetails(String gameTitle) {
        try {
            // Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent gameView = loader.load();

            // Get the controller
            IndividualGameController controller = loader.getController();

            // Set the game data (in a real app, you would pass an ID and/or more data)
            controller.setGameData("game123", gameTitle);

            // Set the new scene
            Scene scene = App.getScene();
            scene.setRoot(gameView);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading game view: " + e.getMessage());
        }
    }
}