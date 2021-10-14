package jobmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jobmanager.data.Applicant;
import jobmanager.data.Application;
import jobmanager.data.JobPosting;
import jobmanager.data.Material;
import jobmanager.helper.AlertHelper;

import java.io.File;

public class ApplyController {
    @FXML
    Button btnSelectCV, btnSelectCL, btnFinish;
    @FXML
    Label lblCV, lblCL;
    @FXML

    private Material cv, cl;
    private String type;
    private Applicant applicant;
    private JobPosting jobPosting;

    public void initialize(Applicant a, JobPosting j) {
        this.applicant = a;
        this.jobPosting = j;
    }

    /**
     * Helps user selecting CV
     */
    @FXML
    public void selectCV() {
        this.type = "CV";
        File f = selectFile();
        if (f != null) {
            this.btnSelectCV.setText(f.getName());
            this.cv = new Material(Material.MaterialType.CURRICULUM_VITAE, f);
        }
    }

    /**
     * Helps user selecting CL
     */
    @FXML
    public void selectCL() {
        this.type = "CL";
        File f = selectFile();
        if (f != null) {
            this.btnSelectCL.setText(f.getName());
            this.cl = new Material(Material.MaterialType.COVER_LETTER, f);
        }
    }

    /**
     * Opens a dialog for user to select a file
     *
     * @return the file selected, or null if user didn't select one
     */
    @FXML
    public File selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".txt", "*.txt"));
        if (this.type.equals("CV"))
            return fileChooser.showOpenDialog(this.btnSelectCV.getScene().getWindow());
        else if (this.type.equals("CL"))
            return fileChooser.showOpenDialog(this.btnSelectCL.getScene().getWindow());
        return null;
    }

    /**
     * Creates an application and notifies user
     */
    @FXML
    public void finishApply() {
        try {
            Application app = new Application(this.applicant, this.jobPosting, this.cv, this.cl);
            this.applicant.apply(app);
            Stage stage = (Stage) this.btnFinish.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertHelper.showErrorAlert(ex.getLocalizedMessage());
        }
    }
}
