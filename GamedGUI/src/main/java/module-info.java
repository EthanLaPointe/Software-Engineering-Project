module gamed.gamedtestproject {
    requires javafx.controls;
    requires javafx.fxml;

    opens gamed.gamedtestproject to javafx.fxml;
    exports gamed.gamedtestproject;
}
