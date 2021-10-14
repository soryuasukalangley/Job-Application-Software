package jobmanager.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Applicant extends User implements Serializable {
    private Date accountCreationDate;
    private List<Application> applications;

    public Applicant(String username, String phoneNumber, String legalName, String email, String password, Date today) {
        super(username, phoneNumber, legalName, email, password);
        this.applications = new ArrayList<>();
        this.accountCreationDate = today;
    }

    /**
     * @return the account creation date of current account
     */
    public Date getAccountCreationDate() {
        return this.accountCreationDate;
    }

    /**
     * Apply as the current user given some application object
     *
     * @param app The application object
     */
    public void apply(Application app) throws Exception {
        // Ensure that the current user has not applied for the same job yet.
        for (Application a : this.applications)
            if (app.getJobPosting().equals(a.getJobPosting()))
                throw new Exception("You cannot apply for a job more than once.");

        // Ensure the posting is still active
        if (app.getJobPosting().getStatus() != JobPosting.JobPostingStatus.OPEN)
            throw new Exception("The position you are attempting to apply for is not currently active.");

        this.applications.add(app);
        app.getJobPosting().addApplication(app);
    }

    /**
     * The method returns all the applications that are not in state ACCEPTED and REJECTED.
     *
     * @return A list of current applicant's application that are not finish yet.
     */
    public List<Application> getOngoingApplications() {
        List<Application> app = new ArrayList<>();
        for (Application a : this.applications) {
            if (!a.getStatus().equals(Application.ApplicationStatus.ACCEPTED) &&
                    !a.getStatus().equals(Application.ApplicationStatus.REJECTED)) { //If not accepted or rejected, then it is ongoing
                app.add(a);
            }
        }
        return app;
    }

    /**
     * The method returns all the applications that are in state ACCEPTED and REJECTED.
     *
     * @return A list of current applicant's application that are finished.
     */
    public List<Application> getCompletedApplications() {
        List<Application> app = new ArrayList<>();
        for (Application a : this.applications) {
            if (a.getStatus().equals(Application.ApplicationStatus.ACCEPTED) &&
                    a.getStatus().equals(Application.ApplicationStatus.REJECTED)) {
                app.add(a);
            }
        }
        return app;
    }

    @Override
    public String getFileString() {
        return "applicant";
    }

}
