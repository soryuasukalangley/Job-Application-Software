package jobmanager.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Application implements Serializable {
    public enum ApplicationStatus {
        APPLIED("Applied"),

        SELECTED_FOR_INTERVIEW("Selected for Interview"),
        INTERVIEW_COMPLETED("Interview Completed"),
        JOB_OFFERED("Job Offered"),

        ACCEPTED("Accepted"),
        REJECTED("Rejected");

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
    private Material cv;
    private Material cl;
    private ArrayList<Interview> interviews;
    private ArrayList<Reference> references;

    public Application(Applicant app, JobPosting posting, Material cv, Material cl) throws InvalidStateException {
        if (cv == null || cl == null)
            throw new InvalidStateException("Please submit required documents");
        this.applicant = app;
        this.jobPosting = posting;
        this.cv = cv;
        this.cl = cl;
        this.interviews = new ArrayList<>();
        this.references = new ArrayList<>();
        this.status = ApplicationStatus.APPLIED;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Application)) return false;
        Application a = (Application) o;
        return applicant.equals(a.applicant) && jobPosting.equals(a.getJobPosting());
    }

    /**
     * @return return the applicant of current application
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * @param applicant set applicant for current application
     */
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    /**
     * @return the CV file for current applicantion
     */
    public Material getCv() {
        return cv;
    }

    /**
     * @return return the cover letter for current application.
     */
    public Material getCl() {
        return cl;
    }

    /**
     * @return return a list of reference for the application.
     */
    public ArrayList<Reference> getReferences() {
        return references;
    }

    /**
     * @return return the number of references that are already presented.
     */
    public int getCompletedReferencesCount() {
        int count = 0;
        for (Reference ref : this.references) {
            if (ref.isMaterialPresent())
                count++;
        }
        return count;
    }

    /**
     * @param ref add a new reference for the application.
     */
    public void addReference(Reference ref) {
        this.references.add(ref);
    }

    /**
     * @return the state of current application
     */
    public ApplicationStatus getStatus() {
        return this.status;
    }

    /**
     * @return the state of current application in String
     */
    public String getStatusString() {
        return this.status.toString();
    }

    /**
     * @return the jobposting of current application.
     */
    public JobPosting getJobPosting() {
        return this.jobPosting;
    }

    /**
     * Add an interview for current application.
     *
     * @param interview an interview
     * @throws InvalidStateException if the applicant are REJECTED or JOB_OFFERED, then they can not select for interview.
     */
    public void selectForInterview(Interview interview) throws InvalidStateException {
        if (this.status == ApplicationStatus.REJECTED ||
                this.status == ApplicationStatus.JOB_OFFERED
        ) throw new InvalidStateException();

        this.interviews.add(interview);
        this.status = ApplicationStatus.SELECTED_FOR_INTERVIEW;
    }

    /**
     * Change the application state from SELECTED_FOR_INTERVIEW to INTERVIEW_COMPLETED
     *
     * @throws InvalidStateException If the user are not selected for interview, then he/she can not complete interview.
     */
    public void completeInterview() throws InvalidStateException {
        if (this.status != ApplicationStatus.SELECTED_FOR_INTERVIEW) throw new InvalidStateException();
        this.status = ApplicationStatus.INTERVIEW_COMPLETED;
    }

    /**
     * Change the application state from JOB_OFFERED to ACCEPTED
     *
     * @throws InvalidStateException If the user is not yet offered a job, then he/she can not accept the offer.
     */
    public void acceptOffer() throws InvalidStateException {
        if (this.status != ApplicationStatus.JOB_OFFERED) throw new InvalidStateException();
        this.status = ApplicationStatus.ACCEPTED;
    }

    /**
     * Rejects the current application from consideration
     */
    public void reject() {
        this.status = ApplicationStatus.REJECTED;
    }

    /**
     * Change the application state from JOB_OFFERED to REJECTED
     *
     * @throws InvalidStateException If the user is not yet offered a job, then he/she can not reject the offer.
     */
    public void declineOffer() throws InvalidStateException {
        if (this.status != ApplicationStatus.JOB_OFFERED) throw new InvalidStateException();
        this.status = ApplicationStatus.REJECTED;
    }

    public void offerPosition() {
        this.status = ApplicationStatus.JOB_OFFERED;
    }

    public void withdrawApplication() {
        this.status = ApplicationStatus.REJECTED;
    }

    public String toString() {
        return String.format("%s - %s", this.applicant.getLegalName(), this.jobPosting.getName());
    }
}
