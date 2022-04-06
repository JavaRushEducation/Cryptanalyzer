package com.cryptanalyzer.infrastructure;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

public class FileService {
    public File fileChooser(Window window) {
        var fileChooser = new FileChooser();
        var extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(window);
    }

    public ArrayList<String> readFile(File file) throws IOException {
        var result = new ArrayList<String>();

        var bufferedReader = new BufferedReader(new FileReader(file));
        String text;
        while ((text = bufferedReader.readLine()) != null) {
            result.add(text);
        }

        return result;
    }

    public void writeFile(String path, ArrayList<String> outputData) throws IOException {
        var myWriter = new FileWriter(path);

        for (String line : outputData) {
            myWriter.write(line);
            myWriter.write("\n");
        }

        myWriter.close();
    }
}
