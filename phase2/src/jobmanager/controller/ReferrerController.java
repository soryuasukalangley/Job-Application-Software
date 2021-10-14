package jobmanager.controller;

import javafx.collections.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import jobmanager.data.Material;
import jobmanager.data.Reference;
import jobmanager.data.Referrer;
import jobmanager.data.User;

import java.io.*;

public class ReferrerController extends WindowsController {
    @FXML
    protected ListView<Reference> lstCompletedReferences, lstIncompleteReferences;
    @FXML
    protected Label lblCompanyName, lblPosition, lblApplicantName, lblStatus, lblDeadline;
    @FXML
    protected Button btnSubmitReference;

    private Reference selectedReference;
    private Referrer selectedReferrer;

    /**
     * Fills up all the information of the reference selected.
     *
     * @param completed The status of the reference of this reference.
     */
    @FXML
    public void populateFields(boolean completed) {
        // TODO: Overdelegation?
        this.lblCompanyName.setText(selectedReference.getApplication().getJobPosting().getCompany().getName());
        this.lblPosition.setText(selectedReference.getApplication().getJobPosting().getName());
        this.lblApplicantName.setText(selectedReference.getApplication().getApplicant().getLegalName());
        if (!completed)
            this.lblStatus.setText("Incomplete");
        else
            this.lblStatus.setText("Completed");
        this.lblDeadline.setText(selectedReference.getApplication().getJobPosting().getEndDate().toString());
    }

    /**
     * Fills up labels when an reference is selected from the list of completed references.
     */
    @FXML
    private void listClickedCompleted() {
        this.selectedReference = this.lstCompletedReferences.getSelectionModel().getSelectedItems().get(0);
        this.populateFields(true);
    }

    /**
     * Fills up labels when an reference is selected from the list of incomplete references.
     */
    @FXML
    private void listClickedIncomplete() {
        this.selectedReference = this.lstIncompleteReferences.getSelectionModel().getSelectedItem();
        this.populateFields(false);
    }

    /**
     * Chooses a txt file to be uploaded as a reference.
     */
    @FXML
    public void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File fileSelected = fileChooser.showOpenDialog(btnSubmitReference.getScene().getWindow());
        if (fileSelected != null) {
            Material material = new Material(Material.MaterialType.REFERENCE, fileSelected);
            material.setName(this.selectedReferrer.getLegalName());
            this.selectedReference.addMaterial(material);
        }

        this.setReferences();
    }

    /**
     * Sets the current user who logged in
     *
     * @param u the user that logged in
     */
    @Override
    public void setUser(User u) {
        this.selectedReferrer = (Referrer) u;
        setReferences();
    }

    /**
     * Adds all the references to the left accordion
     */
    public void setReferences() {
        this.lstCompletedReferences.setItems(FXCollections.observableArrayList(this.selectedReferrer.getCompletedReferences()));
        this.lstIncompleteReferences.setItems(FXCollections.observableArrayList(this.selectedReferrer.getIncompleteReferences()));
    }
}
