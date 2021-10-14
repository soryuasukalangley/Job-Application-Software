package jobmanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import jobmanager.data.Applicant;
import jobmanager.data.Application;

public class ApplicationListingController {
    @FXML
    ApplicationInformationController applicationInformationController;
    @FXML
    ApplicationJobPostingController jobInformationApplicantController;
    @FXML
    protected ListView<Application> lstApplications;
    @FXML
    private Application selectedApplication;
    @FXML
    private Applicant selectedApplicant;
    @FXML
    public void initialize(){

    }

    /**
     * Selects the application to be displayed
     *
     * @param a the application to be displayed
     */
    public void setApplication(Application a) {
        this.selectedApplication = a;
        this.selectedApplicant = a.getApplicant();
    }

    /**
     Selects the applicant to be displayed
     * @param a the applicant to be displayed
     */
    public void setApplicant(Applicant a) {
        this.selectedApplicant = a;
        ObservableList<Application> objects = FXCollections.observableArrayList(a.getOngoingApplications());
        lstApplications.setItems(objects);
        applicationInformationController.setViewType("applicant");
    }

    /**
     * Fills the empty labels to display the information of the selected posting and application.
     */
    @FXML
    private void populateFields() {
        applicationInformationController.setApplication(this.selectedApplication);
        jobInformationApplicantController.setSelectedJobPosting(this.selectedApplication.getJobPosting());
    }

    /**
     * Display the appropriate information when an item is clicked
     */
    @FXML
    private void listClicked() {
        this.selectedApplication = (Application) this.lstApplications.getSelectionModel().getSelectedItems().get(0);
        this.populateFields();

    }

}
