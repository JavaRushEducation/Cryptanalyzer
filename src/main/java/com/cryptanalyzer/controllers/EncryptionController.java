package com.cryptanalyzer.controllers;

import com.cryptanalyzer.infrastructure.CaesarCipherService;
import com.cryptanalyzer.infrastructure.FileService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

public class EncryptionController {

    private CaesarCipherService caesarCipherService;
    private FileService fileService;

    private Window window;
    private ArrayList<String> inputData;
    private File inputFile;

    @FXML
    private TextField fileNameTextField;

    @FXML
    private TextField keyTextField;

    @FXML
    private RadioButton encryption;

    @FXML
    private RadioButton decryption;

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

        if (keyTextField.getText() == "") {
            errorTitleLabel.setText("Не установлен ключ");
            return;
        }

        resetTitle();

        var outputData = new ArrayList<String>();

        for (String line : inputData) {
            outputData.add(
                    encryption.isSelected()
                            ? caesarCipherService.encrypt(line, Integer.parseInt(keyTextField.getText()))
                            : caesarCipherService.decipher(line, Integer.parseInt(keyTextField.getText()))
            );
        }

        var path = encryption.isSelected()
                ? inputFile.getParentFile().getPath() + "/encryption_" + inputFile.getName()
                : inputFile.getParentFile().getPath() + "/decryption_" + inputFile.getName();

        writeFile(path, outputData);
    }

    public void setStageAndSetupListeners(Stage stage) {
        window = stage;
        encryption.setSelected(true);
        caesarCipherService = new CaesarCipherService();
        fileService = new FileService();
        fileNameTextField.setDisable(true);

        keyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                keyTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        encryption.selectedProperty().addListener((observable, oldValue, newValue) -> {
            decryption.setSelected(oldValue);
        });

        decryption.selectedProperty().addListener((observable, oldValue, newValue) -> {
            encryption.setSelected(oldValue);
        });
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