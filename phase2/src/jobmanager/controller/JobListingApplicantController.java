package jobmanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jobmanager.*;
import jobmanager.data.Applicant;
import jobmanager.data.Company;
import jobmanager.data.JobPosting;
import jobmanager.helper.AlertHelper;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobListingApplicantController {
    @FXML
    protected ListView<JobPosting> lstJobPostings;
    @FXML
    protected Label lblCompanyName, lblPositionStatus;
    @FXML
    protected Label lblPositionName, lblNumRefRequired;
    @FXML
    protected Label lblDateBegin, lblDateEnd, lblDateReferences;
    @FXML
    protected Label lblDescription;
    @FXML
    protected Button btnApply;

    private DatabaseManager databaseManager;
    private JobPosting selectedJobPosting;
    private Applicant applicant;

    @FXML
    public void populateFields() {
        this.lblCompanyName.setText(this.selectedJobPosting.getCompany().getName());
        this.lblPositionStatus.setText(this.selectedJobPosting.getStatus().toString());
        this.lblPositionName.setText(this.selectedJobPosting.getName());
        this.lblNumRefRequired.setText(Integer.toString(this.selectedJobPosting.getNumReferencesRequired()));
        this.lblDateBegin.setText(this.selectedJobPosting.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        this.lblDateEnd.setText(this.selectedJobPosting.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        this.lblDateReferences.setText(this.selectedJobPosting.getReferenceDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        this.lblDescription.setText(this.selectedJobPosting.getDescription());
    }

    @FXML
    protected void initialize() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    @FXML
    private void listClicked() {
        this.selectedJobPosting = this.lstJobPostings.getSelectionModel().getSelectedItem();
        this.populateFields();
    }

    /**
     * Sets the list of postings available based on the coordinator specified.
     *
     * @param a Applicant
     */
    public void setApplicant(Applicant a) {
        this.applicant = a;
        ObservableList<JobPosting> objects = FXCollections.observableArrayList();
        for (Company c : this.databaseManager.getCompanies()) {
            objects.addAll(c.getManager().getAllPostings());
        }
        this.lstJobPostings.setItems(objects);
    }

    /**
     * Figure out the postings with the tags as specified by the user.
     */
    @FXML
    private void filterPostings() {
        Set<String> tags = new HashSet<>();
        for (Company c : this.databaseManager.getCompanies()) {
            for (JobPosting p : c.getManager().getAllPostings()) {
                tags.addAll(p.getTags());
            }
        }

        Set<String> selectedTags = AlertHelper.showFilterPostingDialog(tags);

        List<JobPosting> resultList = new ArrayList<>();
        for (Company c : this.databaseManager.getCompanies()) {
            resultList.addAll(c.getManager().getFilteredPostings(selectedTags));
        }

        ObservableList<JobPosting> lstPosting = FXCollections.observableList(resultList);
        this.lstJobPostings.setItems(lstPosting);
    }

    @FXML
    private void resetFilter() {
        List<JobPosting> allPostings = new ArrayList<>();
        for (Company c : this.databaseManager.getCompanies())
            allPostings.addAll(c.getManager().getAllPostings());
        this.lstJobPostings.setItems(FXCollections.observableList(allPostings));
    }


    @FXML
    public void applyJob() {
        try {
            String fxmlDocPath = Main.class.getResource("/resources/apply_job_applicant.fxml").getFile();
            FXMLLoader loader = new FXMLLoader(new File(fxmlDocPath).toURL());
            Scene scene = new Scene(loader.load());
            ApplyController controller = loader.getController();
            controller.initialize(this.applicant, this.selectedJobPosting);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Submit Job Application");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}