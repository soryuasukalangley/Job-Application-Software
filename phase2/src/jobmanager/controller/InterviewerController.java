package jobmanager.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jobmanager.Main;
import jobmanager.data.Application;
import jobmanager.data.Interview;
import jobmanager.data.Interviewer;
import jobmanager.data.User;
import jobmanager.helper.AlertHelper;

import java.io.File;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class InterviewerController extends WindowsController {

    @FXML
    private CheckBox chkRecommended;

    @FXML
    private ListView<Interview> listInterview;

    @FXML
    private Button btnComplete;

    @FXML
    private TextArea txtNote;

    @FXML
    private Label lblUser;
    @FXML
    private Label lblPosition;
    @FXML
    private Label lblName;
    @FXML
    private Label lblMobile;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPositionApp;
    @FXML
    private Label lblInterviewType;
    @FXML
    private Label lblInterviewDate;
    @FXML
    private Label lblCompleted;

    private Interviewer interviewer;

    @Override
    public void setUser(User u) {
        this.interviewer = (Interviewer) u;
        populateUserInfo();
        populateListInterview(interviewer.getPendingInterviews());
    }

    @FXML
    private void populateUserInfo() {
        this.lblUser.setText("User: " + this.interviewer.getLegalName());
        this.lblPosition.setText("Position: Interviewer");
    }

    @FXML
    private void populateInterviewInfo(Interview interview) {
        if (interview == null) return;

        Application currentApplication = interview.getApplication();

        this.lblName.setText(currentApplication.getApplicant().getLegalName());
        this.lblMobile.setText(currentApplication.getApplicant().getPhoneNumber());
        this.lblEmail.setText(currentApplication.getApplicant().getEmail());
        this.lblPositionApp.setText(currentApplication.getJobPosting().getName());
        this.lblInterviewType.setText(interview.getClass().getSimpleName());
        this.lblInterviewDate.setText(interview.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        this.chkRecommended.setSelected(interview.isRecommended());
        this.lblCompleted.setText(interview.isCompleted() ? "Yes" : "No");
        this.txtNote.setText(interview.getNotes());

        // Update button states
        this.txtNote.setEditable(!interview.isCompleted());
        this.btnComplete.setDisable(interview.isCompleted());
        this.chkRecommended.setDisable(interview.isCompleted());
    }

    @FXML
    private void populateListInterview(ArrayList<Interview> interviews) {
        this.listInterview.setItems(FXCollections.observableList(interviews));
    }

    /**
     * When an interview is clicked, the buttons will be automatically disabled or enabled based on the condition
     * of the current interview, and the information of other fields will be populated.
     */
    @FXML
    public void interviewClicked() {
        try {
            Interview selectedInterview = this.listInterview.getSelectionModel().getSelectedItem();
            if (selectedInterview != null)
                this.populateInterviewInfo(selectedInterview);
        } catch (Exception ex) {
            AlertHelper.showErrorAlert("Unable to load interview: " + ex.getLocalizedMessage());
        }
    }

    /**
     * When ths "today" radio button is selected, the listview will be populated with
     * the interviews on the date of operation.
     */
    @FXML
    public void todaySelected() {
        ArrayList<Interview> todayInterviews = new ArrayList<>();
        for (Interview it : this.interviewer.getPendingInterviews()) {
            Date itDate = it.getDate();
            if (itDate.getYear() == new Date().getYear() &&
                    itDate.getMonth() == new Date().getMonth() &&
                    itDate.getDay() == new Date().getDay()) {
                todayInterviews.add(it);
            }
        }
        this.populateListInterview(todayInterviews);
    }

    /**
     * When the "all" radio button is selected, the list view will be populated with
     * all interviews
     */
    @FXML
    public void allSelected() {
        this.populateListInterview(this.interviewer.getPendingInterviews());
    }

    /**
     * Complete the current interview if it is not yet completed.
     */
    @FXML
    public void completeCurrentInterview() {
        try {
            Interview selectedInterview = this.listInterview.getSelectionModel().getSelectedItem();
            selectedInterview.setCompleted(true);
            selectedInterview.getApplication().completeInterview();
            this.interviewClicked();

            AlertHelper.showInformationAlert("This interview has been completed.");

            // Reset view
            this.populateListInterview(interviewer.getPendingInterviews());
            this.populateInterviewInfo(selectedInterview);
        } catch (Exception ex) {
            AlertHelper.showErrorAlert("Unable to complete interview: " + ex.getLocalizedMessage());
        }
    }

    /**
     * Save the notes of the current interview for further consideration.
     */
    @FXML
    public void saveNotes() {
        try {
            Interview selectedInterview = this.listInterview.getSelectionModel().getSelectedItem();
            selectedInterview.setNotes(txtNote.getText());
            selectedInterview.setRecommended(chkRecommended.isSelected());

            AlertHelper.showInformationAlert("Interview information have been saved.");

            // Reset view
            this.populateInterviewInfo(selectedInterview);
        } catch (Exception ex) {
            AlertHelper.showErrorAlert("Unable to save notes: " + ex.getLocalizedMessage());
        }
    }

    @FXML
    private void viewApplication() {
        Interview selectedInterview = this.listInterview.getSelectionModel().getSelectedItem();
        if (selectedInterview == null) return;

        Application app = selectedInterview.getApplication();

        try {
            String fxmlDocPath = Main.class.getResource("/resources/application_information.fxml").getFile();
            FXMLLoader loader = new FXMLLoader(new File(fxmlDocPath).toURL());
            Scene scene = new Scene(loader.load());
            ApplicationInformationController controller = loader.getController();
            controller.setViewType("coordinator");
            controller.setApplication(app);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Application");
            stage.show();
        } catch (Exception e) {
            AlertHelper.showInformationAlert("No application is selected.");
        }
    }
}
