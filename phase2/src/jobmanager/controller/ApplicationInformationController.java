package jobmanager.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import jobmanager.*;
import jobmanager.data.Application;
import jobmanager.data.Material;
import jobmanager.data.Reference;
import jobmanager.data.Referrer;
import jobmanager.helper.AlertHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicationInformationController {
    @FXML
    private Label lblApplicantName, lblInformation, lblApplicationStatus, lblApplicationAction;
    @FXML
    private Button btnAcceptOffer, btnRejectOffer, btnWithdrawApplication, btnInviteReferrer;

    @FXML
    private Label lblReferenceProgress;

    private DatabaseManager databaseManager;
    protected Application application;

    private String viewType;

    @FXML
    private void initialize() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    /**
     * Sets the application to be displayed
     *
     * @param a the aplication to be displayed
     */
    public void setApplication(Application a) {
        this.application = a;
        this.populateFields();
    }

    /**
     * Decides the button that's available to the user depending on his/her status.
     */
    private void updateButtonVisibility() {
        if (this.viewType.equals("applicant")) {
            btnAcceptOffer.setDisable(this.application.getStatus() != Application.ApplicationStatus.JOB_OFFERED);
            btnRejectOffer.setDisable(this.application.getStatus() != Application.ApplicationStatus.JOB_OFFERED);
            btnWithdrawApplication.setDisable(this.application.getStatus() == Application.ApplicationStatus.JOB_OFFERED
                    || this.application.getStatus() == Application.ApplicationStatus.ACCEPTED
                    || this.application.getStatus() == Application.ApplicationStatus.REJECTED);
            btnInviteReferrer.setDisable(this.application.getJobPosting().getNumReferencesRequired() <= 0
                    || (btnWithdrawApplication.isDisable()));
        } else {
            this.btnAcceptOffer.setVisible(false);
            this.btnRejectOffer.setVisible(false);
            this.btnInviteReferrer.setVisible(false);
            this.btnWithdrawApplication.setVisible(false);
            this.lblApplicationAction.setVisible(false);
        }
    }

    /**
     * Sets the type of view
     *
     * @param viewType the type of view
     */
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    /**
     * Fill all the empty labels with information from the selected application.
     */
    private void populateFields() {
        this.lblApplicantName.setText(this.application.getApplicant().getLegalName());
        this.lblApplicationStatus.setText(this.application.getStatusString());
        this.lblReferenceProgress.setText(String.format("%d out of %d (%d invited)",
                this.application.getCompletedReferencesCount(),
                this.application.getJobPosting().getNumReferencesRequired(),
                this.application.getReferences().size()
        ));

        this.updateButtonVisibility();
    }

    /**
     * Constructs new referrer with given legal name
     *
     * @param legalName
     * @return A pair containing the Referrer object along with the generated password
     */
    private Pair<Referrer, String> generateRandomReferrer(String legalName) {
        StringBuilder randomUsername = new StringBuilder("ref_");
        for (int i = 0; i < 4; i++) {
            randomUsername.append("0123456789abcdef".charAt((int) (Math.random() * 16)));
        }

        StringBuilder randomPassword = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            randomPassword.append("0123456789".charAt((int) (Math.random() * 10)));
        }

        Referrer newRef = new Referrer(randomUsername.toString(), null, legalName, null);
        newRef.setPassword(randomPassword.toString());

        return new Pair<>(newRef, randomPassword.toString());
    }


    /**
     * Applicant will request the reference from a referrer and a new account will be created for such referrer
     * with a randomly generated password. The applicant should manage to inform the of the account information.
     * Creates a dialog for applicant to invite a referrer.
     */
    @FXML
    private void inviteReferrer() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Invite referrer");
        dialog.setContentText("Referrer's Legal Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {

            // Create a new referrer with the given username
            String refLegalName = result.get();
            Pair<Referrer, String> refInfo = generateRandomReferrer(refLegalName);
            Referrer referrer = refInfo.getKey();

            // Create reference object
            Reference reference = new Reference(this.application, referrer);
            this.application.addReference(reference);
            referrer.addReferenceToComplete(reference);

            this.databaseManager.getUsers().add(referrer);

            // Inform user of referrer's username/password
            AlertHelper.showInformationAlert(String.format("Username: %s\nPassword: %s",
                    refInfo.getKey().getUsername(), refInfo.getValue()));
        }

        this.populateFields();
    }

    /**
     * Accepts the offer and notifys user.
     */
    @FXML
    private void acceptOffer() {
        try {
            this.application.acceptOffer();
        } catch (Application.InvalidStateException ex) {
            AlertHelper.showErrorAlert(ex.getLocalizedMessage());
        }

        this.populateFields();
    }

    /**
     * Accepts the offer and notifys user.
     */
    @FXML
    public void rejectOffer() {
        try {
            this.application.declineOffer();
        } catch (Application.InvalidStateException ex) {
            AlertHelper.showErrorAlert(ex.getLocalizedMessage());
        }

        this.populateFields();
    }

    /**
     * Withdraw an application.
     */
    @FXML
    public void withdrawApplication() {
        this.application.withdrawApplication();
        this.populateFields();
    }

    @FXML
    public void loadMaterial() {
        if (this.application == null) {
            AlertHelper.showErrorAlert("Please select an application first");
            return;
        }

        List<Material> availableMaterials = new ArrayList<>();
        availableMaterials.add(application.getCv());
        availableMaterials.add(application.getCl());

        if (this.viewType.equals("coordinator")) {
            for (Reference ref : this.application.getReferences()) {
                if (ref.isMaterialPresent())
                    availableMaterials.add(ref.getMaterial());
            }
        }

        ChoiceDialog<Material> dialog = new ChoiceDialog<>(availableMaterials.get(0), availableMaterials);
        dialog.setTitle("Filter by Posting");
        dialog.setHeaderText("Select the job posting to only view applicants who applied to it");
        dialog.setContentText("Choose your letter:");
        Optional<Material> result = dialog.showAndWait();

        if (!result.isPresent()) return;

        try {
            String fxmlDocPath = Main.class.getResource("/resources/text_view_windows.fxml").getFile();
            FXMLLoader loader = new FXMLLoader(new File(fxmlDocPath).toURL());
            Scene scene = new Scene(loader.load());
            TextAreaController controller = loader.getController();
            controller.viewFile(result.get());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(result.toString());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
