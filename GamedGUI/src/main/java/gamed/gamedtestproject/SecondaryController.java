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
import javafx.scene.layout.*;

//Controller for the homepage
public class SecondaryController {
    
    @FXML private ImageView profileButton;
    @FXML private TextField searchField;
    @FXML private HBox featuredGamesContainer;
    @FXML private ComboBox<String> searchCriteria;
    @FXML private ImageView logoImage;
    @FXML private ComboBox<String> genreDropdown;
    @FXML private ComboBox<String> platformDropdown;
    @FXML private HBox searchInputContainer;
    
    @FXML
    public void initialize() {
        // Set profile image
        profileButton.setImage(new Image(getClass().getResourceAsStream("/default_profile.png")));

        logoImage.setImage((new Image(getClass().getResourceAsStream("/logo.png"))));

        searchCriteria.getItems().addAll("Name","Genre","ID","Platform");
        searchCriteria.setValue("Name");

        //Setup genre dropdown
        genreDropdown.getItems().addAll(
                "Adventure", "Indie", "Arcade", "Visual Novel", "Pinball",
                "Card & Board Game", "MOBA", "Point-and-Click", "Fighting",
                "Shooter", "Music", "Platform", "Puzzle", "Racing", "RTS",
                "RPG", "Simulator", "Sport", "Strategy", "Turn-Based Strategy",
                "Tactical", "Hack and Slash/Beat 'em up", "Quiz/Trivia"
        );

        //Setup platform dropdown
        platformDropdown.getItems().addAll(
                "PC", "PlayStation 5", "Xbox Series X|S", "Xbox One",
                "PlayStation 4", "Nintendo Switch", "PlayStation 3", "Xbox 360"
        );

        genreDropdown.setVisible(false);
        platformDropdown.setVisible(false);
        genreDropdown.setManaged(false);
        platformDropdown.setManaged(false);

        //Add listener for search criteria changes
        searchCriteria.valueProperty().addListener((obs, oldValue, newValue) -> {
            updateSearchInputs(newValue);
        });

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
            imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.png")));
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

    private void updateSearchInputs(String criteria) {
        //Hide all search inputs
        searchField.setVisible(false);
        searchField.setManaged(false);
        genreDropdown.setVisible(false);
        genreDropdown.setManaged(false);
        platformDropdown.setVisible(false);
        platformDropdown.setManaged(false);

        //Show appropriate input based on criteria
        switch (criteria) {
            case "Name":
            case "ID":
                searchField.setVisible(true);
                searchField.setManaged(true);
                break;
            case "Genre":
                genreDropdown.setVisible(true);
                genreDropdown.setManaged(true);
                break;
            case "Platform":
                platformDropdown.setVisible(true);
                platformDropdown.setManaged(true);
                break;
        }
    }
    
    @FXML
    private void openProfilePage() throws IOException {
        //TODO Add profile Page
        // Navigate to profile page
        App.setRoot("profile");
    }

    @FXML
    private void searchGames() {
        String criteria = searchCriteria.getValue();
        String query = "";

        //Get the appropriate query value based on criteria
        switch (criteria) {
            case "Name":
            case "ID":
                query = searchField.getText();
                break;
            case "Genre":
                query = genreDropdown.getValue();
                break;
            case "Platform":
                query = platformDropdown.getValue();
                break;
        }

        // Logic for searching by different criteria
        System.out.println("Searching by " + criteria + ": " + query);
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