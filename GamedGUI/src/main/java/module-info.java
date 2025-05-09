module gamed.gamedtestproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // for database stuff
    requires bcrypt;
    requires igdb.api.jvm;
    requires java.net.http;
    requires javafx.base;

    opens gamed.gamedtestproject to javafx.fxml;
    exports gamed.gamedtestproject;

}
