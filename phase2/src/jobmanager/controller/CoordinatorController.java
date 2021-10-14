package jobmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import jobmanager.data.Company;
import jobmanager.data.Coordinator;
import jobmanager.data.User;

import java.util.Optional;


public class CoordinatorController extends WindowsController {
    @FXML
    private JobListingController pnlJobPostingController;
    @FXML
    private ApplicantInfoController pnlApplicantsController;

    @FXML
    private Label lblCurrentCompany;

    private Coordinator coordinator;

    @FXML
    public void initialize() {
    }

    /**
     * Sets the user to be displayed
     *
     * @param c the user that logged in
     */
    @Override
    public void setUser(User c) {
        this.coordinator = (Coordinator) c;
        this.setCurrentCompany(this.coordinator.getManagedCompanies().get(0));
    }

    private void setCurrentCompany(Company c) {
        this.pnlApplicantsController.setCompany(c);
        this.pnlJobPostingController.setCompany(c);
        this.lblCurrentCompany.setText(c.getName());
    }

    @FXML
    private void changeCurrentCompany() {
        ChoiceDialog<Company> dialog = new ChoiceDialog<>(this.coordinator.getManagedCompanies().get(0),
                this.coordinator.getManagedCompanies());
        dialog.setTitle("Select current company");
        dialog.setHeaderText("Select another company to manage");
        dialog.setContentText("Company:");

        Optional<Company> result = dialog.showAndWait();
        if (result.isPresent()){
            this.setCurrentCompany(result.get());
        }
    }
}
