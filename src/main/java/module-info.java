module com.cryptanalyzer.cryptanalyzer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cryptanalyzer to javafx.fxml;
    exports com.cryptanalyzer;
    exports com.cryptanalyzer.controllers;
    opens com.cryptanalyzer.controllers to javafx.fxml;
}