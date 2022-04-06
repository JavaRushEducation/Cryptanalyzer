package com.cryptanalyzer;

import com.cryptanalyzer.controllers.BruteForceDecipherController;
import com.cryptanalyzer.controllers.EncryptionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CryptanalysisApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var encryptionView = InitEncryptionView(stage);
        var bruteForceDecipherView = InitBruteForceDecipherView(stage);

        var tabPane = new TabPane();
        var encryptionTab = new Tab("Шифрование / Расшифровка", encryptionView);
        var bruteForceDecipherTab = new Tab("Криптоанализ методом brute force", bruteForceDecipherView);

        tabPane.getTabs().add(encryptionTab);
        tabPane.getTabs().add(bruteForceDecipherTab);

        var vBox = new VBox(tabPane);
        var scene = new Scene(vBox, 580, 250);

        stage.setScene(scene);
        stage.setTitle("Криптоанализатор");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Parent InitEncryptionView(Stage stage) throws IOException {
        var loader = new FXMLLoader(CryptanalysisApplication.class.getResource("encryption-view.fxml"));
        Parent root = loader.load();
        EncryptionController controller = loader.getController();
        controller.setStageAndSetupListeners(stage);

        return root;
    }

    public Parent InitBruteForceDecipherView(Stage stage) throws IOException {
        var loader = new FXMLLoader(CryptanalysisApplication.class.getResource("brute-force-decipher-view.fxml"));
        Parent root = loader.load();
        BruteForceDecipherController controller = loader.getController();
        controller.setStageAndSetupListeners(stage);

        return root;
    }
}