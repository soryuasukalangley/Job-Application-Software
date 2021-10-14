package jobmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import jobmanager.Main;
import jobmanager.data.User;

public abstract class WindowsController {
    @FXML
    protected Button BtnLogout;
    private Stage stage;

    /**
     * Set stage for current windows.
     *
     * @param stage current stage
     */
    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Close current windows and show login dialog
     */
    @FXML
    public void logout() {
        this.stage.close();
        Main.showLoginWindow(stage);
    }

    public void setUser(User u) {
    }
}