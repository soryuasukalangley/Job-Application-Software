package jobmanager.helper;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AlertHelper {

    public static void showErrorAlert(String errorMessage) {
        showErrorAlert(null, errorMessage);
    }

    public static void showErrorAlert(String headerMessage, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(headerMessage);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public static void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showConfirmationAlert(String headerMessage, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(headerMessage);
        alert.setContentText(contentMessage);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static Set<String> showFilterPostingDialog(Set<String> tags) {
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Filter Job Postings");
        dialog.setHeaderText("Select the tags you would like to filter.");

        dialog.getDialogPane().getButtonTypes().addAll(
                new ButtonType("Filter", ButtonBar.ButtonData.OK_DONE),
                ButtonType.CANCEL
        );

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ArrayList<String> tagsArray = new ArrayList<>(tags);
        ArrayList<CheckBox> checkboxes = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            CheckBox c = new CheckBox(tagsArray.get(i));
            checkboxes.add(c);
            grid.add(c, 1, i);
        }

        dialog.getDialogPane().setContent(grid);
        Optional bt = dialog.showAndWait();

        if (bt.isPresent() && bt.get() != ButtonType.CANCEL) {
            Set<String> selectedTags = new HashSet<>();
            for (int i = 0; i < tags.size(); i++) {
                if (checkboxes.get(i).isSelected())
                    selectedTags.add(tagsArray.get(i));
            }

            return selectedTags;
        }

        return null;
    }
}
