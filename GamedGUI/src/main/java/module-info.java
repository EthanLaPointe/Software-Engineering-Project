module gamed.gamedtestproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // for database stuff
    requires bcrypt;
    requires igdb.api.jvm;

    opens gamed.gamedtestproject to javafx.fxml;
    exports gamed.gamedtestproject;

}
