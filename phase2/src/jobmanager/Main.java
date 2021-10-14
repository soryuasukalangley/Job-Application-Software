package jobmanager;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import jobmanager.controller.SignUpController;
import jobmanager.controller.WindowsController;
import jobmanager.data.User;
import jobmanager.helper.AlertHelper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends javafx.application.Application {

    private static final String SERIALIZATION_PATH = "appstate.ser";
    private static DatabaseManager databaseManager;

    public static void showLoginWindow(Stage stage) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please enter your credentials.");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        Button signUp = new Button("Sign Up as Applicant");
        signUp.setOnAction(actionEvent -> {
            try {
                Stage st = new Stage();
                File f = new File(Main.class.getResource("/resources/sign_up.fxml").getFile());
                FXMLLoader loader = new FXMLLoader(f.toURI().toURL());
                Scene scene = new Scene(loader.load());
                ((SignUpController) loader.getController()).setStage(st);
                st.setScene(scene);
                st.setTitle("Sign Up");
                st.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(signUp, 1, 3);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            for (User u : databaseManager.getUsers()) {
                if (u.getUsername().equals(result.get().getKey()) &&
                        u.validatePassword(result.get().getValue())) {
                    openUserWindow(stage, u);

                    return;
                }
            }

            AlertHelper.showErrorAlert("Invalid username or password.");
            showLoginWindow(stage);
        });
    }

    public static void openUserWindow(Stage stage, User u) {
        try {
            File f = new File(Main.class.getResource("/resources/" + u.getFileString() + ".fxml").getFile());
            FXMLLoader loader = new FXMLLoader(f.toURI().toURL());
            Scene scene = new Scene(loader.load());
            WindowsController controller = loader.getController();
            controller.setStage(stage);
            controller.setUser(u);
            stage.setScene(scene);
            stage.setTitle(u.getFileString());
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static Date getDateOffset(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, offset);

        return cal.getTime();
    }

    public static void main(String[] args) {
        databaseManager = DatabaseManager.getInstance();

        File serializationFile = new File(Main.class.getResource("/").getFile() + SERIALIZATION_PATH);
        databaseManager.setSerializedFile(serializationFile);

        if (serializationFile.exists()) {
            try {
                databaseManager.loadFromFile();
            } catch (Exception ex) {
                databaseManager.loadDemoData();
            }
        } else {
            databaseManager.loadDemoData();
        }

        javafx.application.Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        showLoginWindow(stage);
    }

    @Override
    public void stop() {
        try {
            databaseManager.saveToFile();
            System.out.println("App state saved!");
        } catch (IOException e) {
            AlertHelper.showErrorAlert("Cannot save to serialization file. Changes will not be retained.");
        }
    }
}
