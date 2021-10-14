package jobmanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import jobmanager.data.Application;
import jobmanager.data.Interview;
import jobmanager.data.InterviewRound;
import jobmanager.data.JobPosting;
import jobmanager.helper.AlertHelper;
import jobmanager.data.InterviewFactory;

import java.time.ZoneOffset;
import java.util.*;

public class InterviewRoundController {
    private JobPosting posting;
    private List<Application> nextRound;

    @FXML
    private Label lblCurrentRound;

    @FXML
    private ListView<Interview> lstCurrentRound;

    @FXML
    private ListView<Application> lstNextRound;

    /**
     * Set the posting that the current view is displaying
     *
     * @param p Posting object
     */
    public void setPosting(JobPosting p) throws Application.InvalidStateException {
        this.posting = p;
        InterviewFactory interviewFactory = new InterviewFactory();

        // Populate current round list
        if (this.posting.getLastInterviewRound() == null) {
            // There is no interview rounds yet. Let's create a new one.
            Pair<String, Date> interviewRoundInfo = showNewInterviewRoundDialog();
            if (interviewRoundInfo == null) {
                AlertHelper.showErrorAlert("You must first create an initial interview round.");
                return;
            }

            if (this.posting.getEligibleApplications().size() == 0) {
                AlertHelper.showErrorAlert("You cannot create an interview round with no applicants.");
                return;
            }

            String interviewType = interviewRoundInfo.getKey();
            Date interviewDate = interviewRoundInfo.getValue();
            List<Interview> interviews = new ArrayList<>();

            for (Application app : this.posting.getEligibleApplications()) {
                Interview i = interviewFactory.constructInterview(interviewType, app, interviewDate);
                if (i == null) {
                    AlertHelper.showErrorAlert("Invalid interview type.");
                    return;
                }
                app.selectForInterview(i);
                interviews.add(i);
            }

            this.posting.addInterviewRound(new InterviewRound(this.posting, interviews));
        }

        InterviewRound lastRound = this.posting.getLastInterviewRound();
        for (Interview it : lastRound.getInterviews()) {
            Interview it_ = it;
            it.getApplication().selectForInterview(it_);
        }
        ObservableList<Interview> lst = FXCollections.observableList(lastRound.getInterviews());
        this.lstCurrentRound.setItems(lst);

        // Initialize next round list
        this.nextRound = new ArrayList<>();
        lstNextRound.setItems(FXCollections.observableList(this.nextRound));

        // Populate Label
        lblCurrentRound.setText(String.format("Current Round (#%d, %s)",
                this.posting.getInterviewRoundCount(),
                interviewFactory.getInterviewTypeString(this.posting.getLastInterviewRound().getInterviews().get(0))));
    }

    /**
     * Add a selected interview to the next round if it is not null.
     */
    @FXML
    private void addSelectedToNextRound() {
        Interview it = this.lstCurrentRound.getSelectionModel().getSelectedItem();
        if (it == null) {
            AlertHelper.showErrorAlert("You must select an application from the left to add to the next round.");
            return;
        }

        if (!it.isCompleted()) {
            AlertHelper.showErrorAlert("The selected interview is still pending. " +
                    "You must conclude the interview before adding it to the next round.");
            return;
        }

        this.nextRound.add(it.getApplication());
        this.lstNextRound.setItems(FXCollections.observableList(this.nextRound));
    }

    /**
     * Remove a selected interview from the next round list if it is not null.
     */
    @FXML
    private void removeFromNextRound() {
        Application app = this.lstNextRound.getSelectionModel().getSelectedItem();
        if (app == null) {
            AlertHelper.showErrorAlert("You must select an application from the right to remove.");
            return;
        }

        this.nextRound.remove(app);
        this.lstNextRound.setItems(FXCollections.observableList(this.nextRound));
    }

    /**
     * Constructs a new interview round
     *
     * @return Return a pair with a specified string of interview type as the key
     * and a date as its value for the newly created interview round.
     */
    private Pair<String, Date> showNewInterviewRoundDialog() {
        Dialog<Pair<String, Date>> dialog = new Dialog<>();
        dialog.setTitle("New Interview Round");
        dialog.setHeaderText("Please enter details about the new interview round.");

        ButtonType loginButtonType = new ButtonType("Create Interview Round", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        InterviewFactory interviewFactory = new InterviewFactory();
        ChoiceBox<String> cmbInterviewType = new ChoiceBox<>();
        cmbInterviewType.setItems(FXCollections.observableList(interviewFactory.getAvailableInterviewTypes()));

        DatePicker dateInterview = new DatePicker();

        grid.add(new Label("Interview Type:"), 0, 0);
        grid.add(cmbInterviewType, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(dateInterview, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(
                        cmbInterviewType.getValue(),
                        Date.from(dateInterview.getValue().atStartOfDay().toInstant(ZoneOffset.UTC))
                );
            }
            return null;
        });

        Optional<Pair<String, Date>> result = dialog.showAndWait();
        if (result.isPresent())
            return new Pair<>(result.get().getKey(), result.get().getValue());

        return null;
    }

    /**
     * If there is no applicant selected for the next round, the posting will be terminated without anyone being hired.
     * If there is only 1 applicant for the next round, he/she is automatically offered the position.
     * If there are more than 1 applicant for the next round, a new interview round is meant to be
     * created for further selection.
     */
    @FXML
    private void finalizeNextRound() {
        if (this.lstNextRound.getItems().size() == 0) {
            if (AlertHelper.showConfirmationAlert("No applicant selected for the next round",
                    "Clicking OK would terminate the posting with no applicants selected.")) {
                this.posting.terminatePosting();
                AlertHelper.showInformationAlert("This position has been terminated.");
            }
        } else if (this.lstNextRound.getItems().size() <= this.posting.getNumPositionsOffered()) {
            if (AlertHelper.showConfirmationAlert("Number of selected applicants is less than or equal to positions offered.",
                    "Clicking OK would hire the selected applicant(s)")) {
                for (Application app : this.lstNextRound.getItems()) {
                    app.offerPosition();
                }

                AlertHelper.showInformationAlert("The position has been offered to the selected applicant(s).\n" +
                        "Currently waiting for their response(s).");
            }
        } else {
            Pair<String, Date> interviewRoundInfo = showNewInterviewRoundDialog();
            if (interviewRoundInfo == null) return;

            String interviewType = interviewRoundInfo.getKey();
            Date interviewDate = interviewRoundInfo.getValue();

            try {
                InterviewRound ir = this.posting.getLastInterviewRound().getNextRound(nextRound, interviewType, interviewDate);
                this.posting.addInterviewRound(ir);

                AlertHelper.showInformationAlert("The new interview round has been added.");

                this.setPosting(posting);
            } catch (Exception ex) {
                AlertHelper.showErrorAlert(ex.getLocalizedMessage());
            }
        }
    }
}
