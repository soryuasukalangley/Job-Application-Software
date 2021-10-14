import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Devin
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
     * Append new application to current applicant's application list.
     * @param app the application that the applicant applied.
     */
    void addApplication(Application app) {
        this.applications.add(app);
    }

    /**
     * The method returns all the applications that are not in state ACCEPTED and REJECTED.
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
     *The method returns all the applications that are in state ACCEPTED and REJECTED.
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
}
