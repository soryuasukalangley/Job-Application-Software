package jobmanager.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import jobmanager.*;
import jobmanager.data.Applicant;
import jobmanager.data.Application;
import jobmanager.data.Company;
import jobmanager.data.JobPosting;
import jobmanager.helper.AlertHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicantInfoController {

    private Company company;
    private Applicant selectedApplicant;

    @FXML
    private ListView<Applicant> lstApplicants;

    @FXML
    private Label lblLegalName;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblPhoneNumber;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblCreationDate;

    @FXML
    private ListView<Application> lstApplications;

    /**
     * Sets the company that the current frame views
     *
     * @param c The company
     */
    public void setCompany(Company c) {
        this.company = c;

        ArrayList<Applicant> applicants = this.company.getManager().getApplicants();
        lstApplicants.setItems(FXCollections.observableList(applicants));

    }

    private void populateFields() {
        if (this.selectedApplicant == null) return;

        lblLegalName.setText(this.selectedApplicant.getLegalName());
        lblUsername.setText(this.selectedApplicant.getUsername());
        lblPhoneNumber.setText(this.selectedApplicant.getPhoneNumber());
        lblEmail.setText(this.selectedApplicant.getEmail());
        lblCreationDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(this.selectedApplicant.getAccountCreationDate()));
        lstApplications.setItems(FXCollections.observableList(this.selectedApplicant.getOngoingApplications()));
    }

    /**
     * Filters lstApplicants with a search query provided by the user.
     * The search function ignores letter cases, spaces, as well as allowing
     * the user to search for a part of the name (e.g. first)
     */
    @FXML
    private void searchByName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search by Name");
        dialog.setContentText("Search query:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String query = result.get();

            List<Applicant> resultsList = new ArrayList<>();
            for (Applicant app : this.company.getManager().getApplicants()) {
                if (app.getLegalName().replace(" ", "").toLowerCase().contains(
                        query.replace(" ", "").toLowerCase())) {

                    resultsList.add(app);
                }
            }

            this.lstApplicants.setItems(FXCollections.observableList(resultsList));
        }
    }

    /**
     * Filters applicants by job posting.
     */
    @FXML
    private void filterByPosting() {
        ChoiceDialog<JobPosting> dialog = new ChoiceDialog<>(this.company.getManager().getAllPostings().get(0),
                this.company.getManager().getAllPostings());
        dialog.setTitle("Filter by Posting");
        dialog.setHeaderText("Select the job posting to only view applicants who applied to it");
        dialog.setContentText("Choose your letter:");

        Optional<JobPosting> result = dialog.showAndWait();
        result.ifPresent(jobPosting -> this.lstApplicants.setItems(FXCollections.observableList(jobPosting.getApplicants())));
    }

    /**
     * Update the fields when user clicks on an item in the list
     */
    @FXML
    private void listClicked() {
        this.selectedApplicant = lstApplicants.getSelectionModel().getSelectedItem();
        this.populateFields();
    }

    @FXML
    private void viewSelectedApplication() {
        try {
            String fxmlDocPath = Main.class.getResource("/resources/application_information.fxml").getFile();
            FXMLLoader loader = new FXMLLoader(new File(fxmlDocPath).toURL());
            Scene scene = new Scene(loader.load());
            ApplicationInformationController controller = loader.getController();
            controller.setViewType("coordinator");
            controller.setApplication(this.lstApplications.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Application");
            stage.show();
        } catch (Exception e) {
            AlertHelper.showInformationAlert("No application is selected.");
        }
    }

    /**
     * Resets the view to show all applicants.
     */
    @FXML
    private void showAllApplicants() {
        this.lstApplicants.setItems(FXCollections.observableList(this.company.getManager().getApplicants()));
    }
}
