package jobmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jobmanager.data.Applicant;
import jobmanager.DatabaseManager;
import jobmanager.helper.AlertHelper;

import java.util.Date;

public class SignUpController {
    @FXML
    TextField txtLegalName, txtUserName, txtPhoneNumber, txtEmail;
    @FXML
    PasswordField txtPassword, txtPasswordRepeat;
    @FXML
    Button btnSubmitSignUp;

    private DatabaseManager databaseManager;
    private Stage stage;

    @FXML
    private void initialize() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    /**
     * Creat a new applicant according to the given information.
     * Show alert when missing any information/ user name already used.
     */
    @FXML
    public void signUp() {
        if (!txtPassword.getText().equals(txtPasswordRepeat.getText())) {
            AlertHelper.showErrorAlert("Passwords do not match.");
            return;
        }

        Applicant applicant = new Applicant(
                txtUserName.getText(),
                txtPhoneNumber.getText(),
                txtLegalName.getText(),
                txtEmail.getText(),
                txtPassword.getText(),
                new Date()
        );

        this.databaseManager.getUsers().add(applicant);
        AlertHelper.showInformationAlert("Registration is successful.");
        this.stage.close();
    }

    public void setStage(Stage st) {
        this.stage = st;
    }
}