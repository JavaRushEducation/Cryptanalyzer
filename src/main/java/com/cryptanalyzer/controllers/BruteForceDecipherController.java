package com.cryptanalyzer.controllers;

import com.cryptanalyzer.infrastructure.CaesarCipherService;
import com.cryptanalyzer.infrastructure.FileService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

public class BruteForceDecipherController {

    private CaesarCipherService caesarCipherService;
    private FileService fileService;

    private Window window;
    private ArrayList<String> inputData;
    private File inputFile;

    @FXML
    private TextField fileNameTextField;

    @FXML
    private Label errorTitleLabel;

    @FXML
    private Label infoTitleLabel;

    @FXML
    protected void onFileChooser() {
        resetTitle();
        var file = fileService.fileChooser(window);
        if (file != null && file.canRead()) {
            fileNameTextField.setText(file.getName());
            readFile(file);
        }
    }

    @FXML
    protected void onStart() {
        if (inputFile == null) {
            errorTitleLabel.setText("Файл не выбран");
            return;
        }

        var outputData = new ArrayList<String>();

        for (String line : inputData) {
            outputData.add(caesarCipherService.bruteForceDecipher(line));
        }

        var path = inputFile.getParentFile().getPath() + "/brute_force_decipher_" + inputFile.getName();

        writeFile(path, outputData);
    }

    public void setStageAndSetupListeners(Stage stage) {
        window = stage;
        caesarCipherService = new CaesarCipherService();
        fileService = new FileService();
        fileNameTextField.setDisable(true);
    }

    private void readFile(File file) {
        try {
            inputData = fileService.readFile(file);
            inputFile = file;
        } catch (FileNotFoundException ex) {
            errorTitleLabel.setText("Файл не найден");
        } catch (IOException ex) {
            errorTitleLabel.setText("Ошибка чтения файла");
        }
    }

    private void writeFile(String path, ArrayList<String> outputData) {
        try {
            fileService.writeFile(path, outputData);
            infoTitleLabel.setText("Файл успешно создан");
        } catch (IOException e) {
            errorTitleLabel.setText("Ошибка записи файла");
        }
    }

    private void resetTitle() {
        errorTitleLabel.setText("");
        infoTitleLabel.setText("");
    }
}