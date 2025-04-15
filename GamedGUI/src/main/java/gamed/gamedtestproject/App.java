package gamed.gamedtestproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        Screen screen = Screen.getPrimary();
        double dpi = screen.getDpi();
        double scaleFactor = dpi/96.0;
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
        double width = screenWidth*0.8;
        double height = screenHeight*0.8;

        StackPane root = new StackPane();
        Image backgroundImage = new Image("background.jpg");
        
        //Load background image
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true));
        
        //Add Background Image
        root.setBackground(new Background(bgImage));
        
        //Add Logo
        ImageView logoView = new ImageView(new Image(getClass().getResourceAsStream("/logo.png")));
        logoView.setFitWidth(screenWidth*0.4);
        logoView.setPreserveRatio(true);
        
        //Position Logo at top of screen
        StackPane.setAlignment(logoView,Pos.TOP_CENTER);
        StackPane.setMargin(logoView, new Insets(50,0,0,0));
        root.getChildren().add(logoView);
        
        //Add login field
        Parent loginUI = loadFXML("primary");
        root.getChildren().add(loginUI);

        scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Game'd");
        stage.setMinHeight(800);
        stage.setMaxHeight(600);
        stage.show();

        if(scaleFactor>1){
            scene.getRoot().setStyle("-fx-font-size: " + (12 * scaleFactor) + "px;");
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Scene getScene(){
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }

}