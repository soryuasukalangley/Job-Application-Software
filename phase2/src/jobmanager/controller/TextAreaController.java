package jobmanager.controller;

import javafx.fxml.FXML;
import jobmanager.data.Material;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextAreaController {
    @FXML
    TextArea textArea;

    public void viewFile(Material material) {
        this.textArea.setEditable(false);
        this.textArea.setText(loadFIle(material.getFile()));
    }

    private String loadFIle(File f) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String currentLine = br.readLine();
            while (currentLine != null) {
                sb.append(currentLine + "\n");
                currentLine = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
