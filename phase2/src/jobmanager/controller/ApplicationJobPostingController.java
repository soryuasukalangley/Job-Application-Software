package jobmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jobmanager.data.JobPosting;
import java.time.ZoneId;

public class ApplicationJobPostingController {
    @FXML
    protected Label lblCompanyName, lblPositionStatus;
    @FXML
    protected Label lblPositionName, lblNumRefRequired;
    @FXML
    protected Label lblDateBegin, lblDateEnd, lblDateReferences;
    @FXML
    protected Label lblDescription;

    private JobPosting selectedJobPosting;

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

    }


    public void setSelectedJobPosting(JobPosting p) {
        this.selectedJobPosting = p;
        this.populateFields();
    }
}
