package gamed.gamedtestproject;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

//Controller for the homepage
public class SecondaryController {
    
    @FXML private ImageView profileButton;
    @FXML private TextField searchField;
    @FXML private HBox featuredGamesContainer;
    @FXML private ComboBox<String> searchCriteria;
    @FXML private ImageView logoImage;
    
    @FXML
    public void initialize() {
        // Set profile image
        profileButton.setImage(new Image(getClass().getResourceAsStream("/default_profile.png")));

        logoImage.setImage((new Image(getClass().getResourceAsStream("/logo.png"))));

        searchCriteria.getItems().addAll("Name","Genre","ID","Platform");
        searchCriteria.setValue("Name");

        featuredGamesContainer.setStyle("-fx-background-color: #222222;");
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
        card.setPrefWidth(200);
        card.setStyle("-fx-background-color: #444444; -fx-padding: 10; -fx-background-radius: 5;");
        
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        } catch (Exception e) {
            // If image not found, use a placeholder
            imageView.setImage(new Image(getClass().getResourceAsStream("placeholder.png")));
        }

        imageView.setFitWidth(180);
        imageView.setFitHeight(240);
        imageView.setPreserveRatio(true);

        VBox.setVgrow(imageView,Priority.ALWAYS);
        card.setPrefWidth(Region.USE_COMPUTED_SIZE);
        
        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        card.getChildren().addAll(imageView, titleLabel);
        
        // Add click event
        card.setOnMouseClicked(event -> openGameDetails(title));

        //Make cards move when moused over
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: #555555; -fx-padding: 10; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        });
        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color: #444444; -fx-padding: 10; -fx-background-radius: 5;");
        });
        
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
        String query = searchField.getText();
        String criteria = searchCriteria.getValue();

        // Logic for searching by different criteria
        switch (criteria) {
            case "Name":
                // Search by name
                System.out.println("Searching by name: " + query);
                break;
            case "Genre":
                // Search by genre
                System.out.println("Searching by genre: " + query);
                break;
            case "ID":
                // Search by ID
                System.out.println("Searching by ID: " + query);
                break;
            case "Platform":
                // Search by platform
                System.out.println("Searching by platform: " + query);
                break;
            default:
                // Default search
                System.out.println("Default search: " + query);
                break;
        }
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