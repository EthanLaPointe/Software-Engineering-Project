module gamed.gamedtestproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // for database stuff
    requires bcrypt; // Removed as bcrypt is not a valid module
    opens gamed.gamedtestproject to javafx.fxml;
    exports gamed.gamedtestproject;

}
