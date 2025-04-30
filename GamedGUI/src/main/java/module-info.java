module gamed.gamedtestproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // for database stuff
    opens gamed.gamedtestproject to javafx.fxml;
    exports gamed.gamedtestproject;

}
