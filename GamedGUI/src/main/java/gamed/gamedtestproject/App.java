package gamed.gamedtestproject;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

        Parent root = loadFXML("primary");

        scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Game'd");
        stage.setMinHeight(800);
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