package jobmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import jobmanager.data.Applicant;
import jobmanager.data.User;

public class ApplicantWindowsController extends WindowsController {
    @FXML
    ApplicationListingController applicationListingApplicantController;
    @FXML
    JobListingApplicantController jobPostingApplicantController;
    @FXML
    Tab tabJobPosting, tabApplicationListing;

    Applicant applicant;

    /**
     * Sets the current user.
     *
     * @param s
     */
    public void setUser(User s) {
        this.applicant = (Applicant) s;
        this.applicationListingApplicantController.setApplicant(applicant);
        this.jobPostingApplicantController.setApplicant(applicant);
        this.initializeTabs();

    }

    /**
     * set the applicant of current windows.
     *
     * @param a an the applicant that is using the windows
     */
    public void setApplicant(Applicant a) {
        this.applicationListingApplicantController.setApplicant(a);
        this.jobPostingApplicantController.setApplicant(a);
        this.applicant = a;


    }

    /**
     * Initialize the action of tabs.
     */
    public void initializeTabs() {
        this.tabApplicationListing.setOnSelectionChanged(event -> {
            if (this.tabApplicationListing.isSelected()) {
                this.applicationListingApplicantController.setApplicant(this.applicant);
            }
        });
        this.tabJobPosting.setOnSelectionChanged(event -> {
            if (this.tabJobPosting.isSelected()) {
                this.applicationListingApplicantController.setApplicant(this.applicant);
            }
        });

    }

    @FXML
    public void initialize() {
        this.initializeTabs();
    }
}
