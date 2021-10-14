
import javax.print.attribute.standard.JobOriginatingUserName;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

// Devin
public class Application implements Serializable {
    enum ApplicationStatus {
        APPLIED ("Applied"),

        RECEIVED ("Received"),
        SELECTED_FOR_INTERVIEW ("Selected for Interview"),
        INTERVIEW_COMPLETED ("Interview Completed"),
        JOB_OFFERED ("Job Offered"),

        MORE_INFORMATION_REQUESTED ("More information requested"),
        INFORMATION_SUBMITTED ("Information submitted"),
        ACCEPTED ("Accepted"),
        REJECTED ("Rejected");

        private final String name;
        private ApplicationStatus(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public static class InvalidStateException extends Exception {
        public InvalidStateException() {
            super("Invalid State");
        }

        public InvalidStateException(String message) {
            super(message);
        }
    }

    private Applicant applicant;
    private JobPosting jobPosting;
    private ApplicationStatus status;
    private File cv;
    private File cl;
    private ArrayList<Interview> interviews;
    private ArrayList<Reference> references;
    private String information;

    public Application(Applicant app, JobPosting posting, File cv, File cl) {
        this.applicant = app;
        this.jobPosting = posting;
        this.cv = cv;
        this.cl = cl;
        this.interviews = new ArrayList<>();
        this.references = new ArrayList<>();
        this.status = ApplicationStatus.APPLIED;
        this.information = "";
        app.addApplication(this);
    }

    /**
     *
     * @return return the applicant of current application
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     *
     * @param applicant set applicant for current application
     */

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    /**
     *
     * @return the CV file for current applicantion
     */
    public File getCv() {
        return cv;
    }

    /**
     *
     * @param cv set the cv file for current application
     */
    public void setCv(File cv) {
        this.cv = cv;
    }

    /**
     *
     * @return return the cover letter for current application.
     */
    public File getCl() {
        return cl;
    }

    /**
     *
     * @param cl set the cover letter file for current application
     */
    public void setCl(File cl) {
        this.cl = cl;
    }

    /**
     *
     * @return return a list of interview that to be finish by the current user.
     */
    public ArrayList<Interview> getInterviews() {
        return interviews;
    }

//    public void setInterviews(ArrayList<Interview> interviews) {
//        this.interviews = interviews;
//    }

    /**
     *
     * @return return a list of reference for the application.
     */
    public ArrayList<Reference> getReferences() {
        return references;
    }

//    public void setReferences(ArrayList<Reference> references) {
//        this.references = references;
//    }

    /**
     *
     * @param ref add a new reference for the application.
     */
    public void addReference(Reference ref) {
        this.references.add(ref);
    }

    /**
     *
     * @return the state of current application
     */
    ApplicationStatus getStatus() {
        return status;
    }

    /**
     *
     * @return the jobposting of current application.
     */
    public JobPosting getJobPosting() {
        return this.jobPosting;
    }
//
//    public void setStatus(ApplicationStatus status) {
//        this.status = status;
//    }

    // getters and setters
//    public Interview getCurrentInterview() {
//        // Returns the first interview scheduled in the future
//        // If none exists, return null
//        return null;
//    }

    // Employer Actions
//    public void acknowledgeReceive() throws InvalidStateException {
//        if (this.status != ApplicationStatus.APPLIED) throw new InvalidStateException();
//
//        this.status = ApplicationStatus.RECEIVED;
//    }

    /**
     * Add an interview for current application.
     * @param interview an interview
     * @throws InvalidStateException if the applicant are REJECTED or JOB_OFFERED, then they can not select for interview.
     */
    public void selectForInterview(Interview interview) throws InvalidStateException {
        if (this.status == ApplicationStatus.REJECTED ||
            this.status == ApplicationStatus.JOB_OFFERED
        ) throw new InvalidStateException();

//        if (this.interview != null) throw new InvalidStateException("Interview already exists");

        this.interviews.add(interview);
        this.status = ApplicationStatus.SELECTED_FOR_INTERVIEW;
    }

    /**
     * Ask the user to submit more information.
     * @param information Description of the information required
     * @throws InvalidStateException If the application state is rejected, then they can not request for more information.
     */
    public void requestMoreInformation(String information) throws InvalidStateException {
        //TODO: Allow specifying information content
        if (this.status == ApplicationStatus.REJECTED) throw new InvalidStateException();
        this.information = information;//update the information
        this.status = ApplicationStatus.MORE_INFORMATION_REQUESTED;
    }

    // Applicant Actions

    /**
     *
     * @param f File that has been submitted by the user.
     */
    public void submitInformation(File f) {
        //if (this.status != ApplicationStatus.MORE_INFORMATION_REQUESTED) throw new InvalidStateException();
        // TODO: Actually submit material: How? Discuss during next meeting
        this.status = ApplicationStatus.INFORMATION_SUBMITTED;
    }

    /**
     * Change the application state from SELECTED_FOR_INTERVIEW to INTERVIEW_COMPLETED
     * @throws InvalidStateException If the user are not selected for interview, then he/she can not complete interview.
     */
    public void completeInterview() throws InvalidStateException{
        if(this.status != ApplicationStatus.SELECTED_FOR_INTERVIEW) throw new InvalidStateException();
        this.status = ApplicationStatus.INTERVIEW_COMPLETED;
    }

    public void acceptOffer() throws InvalidStateException {
        if(this.status != ApplicationStatus.JOB_OFFERED) throw new InvalidStateException();
        this.information = null;
        this.status = ApplicationStatus.ACCEPTED;
    }

    public void declineOffer() throws InvalidStateException {
        if(this.status != ApplicationStatus.JOB_OFFERED) throw new InvalidStateException();
        this.information = null;
        this.status = ApplicationStatus.REJECTED;
    }

    public void withdrawApplication() {
        this.information = null;
        this.status = ApplicationStatus.REJECTED;
    }

    public String toString() {
        return String.format("%s - %s", this.applicant.getLegalName(), this.jobPosting.getName());
    }
}
