import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class FileViewerWindow extends JFrame {
    public FileViewerWindow(File f) {
        this.setSize(800, 600);
        this.setTitle(String.format("Viewing %s", f.getAbsolutePath()));

        String lines = "";
        try {
            Scanner sc = new Scanner(new FileReader(f));

            while(sc.hasNextLine()) {
                lines += sc.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            lines = "Error: " + e.getLocalizedMessage();
        }

        JTextArea txtFileContent = new JTextArea(lines);
        this.add(txtFileContent, BorderLayout.CENTER);

        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
