package jobmanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jobmanager.data.Company;
import jobmanager.data.JobPosting;
import jobmanager.Main;
import jobmanager.helper.AlertHelper;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.*;

public class JobListingController {
    @FXML
    protected ListView<JobPosting> lstJobPostings;
    @FXML
    protected Label lblCompanyName, lblPositionStatus;
    @FXML
    protected TextField txtPositionName, txtNumRefRequired, txtNumPositionsOffered, txtTags;
    @FXML
    protected DatePicker dateBegin, dateEnd, dateReferences;
    @FXML
    protected TextArea txtDescription;

    private Company company;
    private JobPosting selectedJobPosting;

    @FXML
    private void populateFields() {
        this.lblCompanyName.setText(this.selectedJobPosting.getCompany().getName());
        this.lblPositionStatus.setText(this.selectedJobPosting.getStatus().toString());
        this.txtPositionName.setText(this.selectedJobPosting.getName());
        this.txtNumRefRequired.setText(Integer.toString(this.selectedJobPosting.getNumReferencesRequired()));
        this.txtNumPositionsOffered.setText(Integer.toString(this.selectedJobPosting.getNumPositionsOffered()));
        this.dateBegin.setValue(this.selectedJobPosting.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
        this.dateEnd.setValue(this.selectedJobPosting.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
        this.dateReferences.setValue(this.selectedJobPosting.getReferenceDeadline().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
        this.txtTags.setText(String.join(",", this.selectedJobPosting.getTags()));
        this.txtDescription.setText(this.selectedJobPosting.getDescription());
    }

    @FXML
    private void updateValuesFromFields() {
        try {
            this.selectedJobPosting.setName(this.txtPositionName.getText());
            this.selectedJobPosting.setNumReferencesRequired(Integer.parseInt(this.txtNumRefRequired.getText()));
            this.selectedJobPosting.setNumPositionsOffered(Integer.parseInt(this.txtNumPositionsOffered.getText()));
            this.selectedJobPosting.setStartDate(Date.from(this.dateBegin.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault()).toInstant()));
            this.selectedJobPosting.setEndDate(Date.from(this.dateEnd.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault()).toInstant()));
            this.selectedJobPosting.setReferenceDeadline(Date.from(this.dateReferences.getValue()
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            this.selectedJobPosting.setTags(new HashSet<>(Arrays.asList(this.txtTags.getText().split(","))));
            this.selectedJobPosting.setDescription(this.txtDescription.getText());

            AlertHelper.showInformationAlert("The modified fields are successfully updated.");
        } catch (Exception ex) {
            AlertHelper.showErrorAlert("One or more fields contain values with an incorrect format",
                    ex.getLocalizedMessage());
        }
    }

    /**
     * Sets the list of postings available based on the coordinator specified.
     *
     * @param c Company
     */
    public void setCompany(Company c) {
        this.company = c;
        ObservableList<JobPosting> objects = FXCollections.observableArrayList();
        objects.addAll(this.company.getManager().getAllPostings());
        lstJobPostings.setItems(objects);
    }

    @FXML
    protected void initialize() {
    }

    @FXML
    private void listClicked() {
        this.selectedJobPosting = this.lstJobPostings.getSelectionModel().getSelectedItems().get(0);
        populateFields();
    }

    @FXML
    private void viewInterviewRounds() {
        try {
            String fxmlDocPath = Main.class.getResource("/resources/interview_rounds.fxml").getFile();
            FXMLLoader loader = new FXMLLoader(new File(fxmlDocPath).toURL());
            Scene scene = new Scene(loader.load());
            InterviewRoundController controller = loader.getController();
            controller.setPosting(this.selectedJobPosting);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Interview Rounds");
            stage.show();
        } catch (Exception ex) {
            AlertHelper.showErrorAlert(ex.getLocalizedMessage());
        }
    }

    /**
     * Figure out the postings with the tags as specified by the user.
     */
    @FXML
    private void filterPostingsByTag() {
        Set<String> tags = new HashSet<>();
        for (JobPosting p : this.company.getManager().getAllPostings()) {
            tags.addAll(p.getTags());
        }

        Set<String> selectedTags = AlertHelper.showFilterPostingDialog(tags);

        ObservableList<JobPosting> lstPosting = FXCollections.observableList(
                new ArrayList<>(this.company.getManager().getFilteredPostings(selectedTags)));
        this.lstJobPostings.setItems(lstPosting);
    }

    @FXML
    private void resetFilter() {
        ObservableList<JobPosting> lstPosting = FXCollections.observableList(this.company.getManager().getAllPostings());
        this.lstJobPostings.setItems(lstPosting);
    }

    @FXML
    private void createNewPosting() {
        this.resetFilter();
        this.company.getManager().addJobPosting(new JobPosting(this.company));
        // Reload list
        this.setCompany(this.company);
        this.lstJobPostings.getSelectionModel().selectLast();
    }
}
