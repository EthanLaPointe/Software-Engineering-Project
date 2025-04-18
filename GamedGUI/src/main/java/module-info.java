module gamed.gamedtestproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 

    opens gamed.gamedtestproject to javafx.fxml;
    exports gamed.gamedtestproject;
}
