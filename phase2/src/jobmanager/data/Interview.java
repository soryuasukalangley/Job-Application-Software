package jobmanager.data;

import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Interview {
    private Application application;
    private Interviewer interviewer;
    private Date date;

    private boolean completed;
    private boolean recommended;
    private String notes;

    public Interview(Application app) {
        this.application = app;
    }

    public Interview(Application app, Date interviewDate) {
        this.application = app;
        this.date = interviewDate;
    }

    /**
     *
     * @return returns the difference between the interview date and today's date. A negative number means the
     * interview dates have passed, and a positive one means the interview has not yet come.
     */
    public long daysUntil() {
        long diffInMill = this.getDate().getTime() - (new Date(
                new Date().getYear(), new Date().getMonth(), new Date().getDay()
        )).getTime();
        return TimeUnit.DAYS.convert(diffInMill, TimeUnit.MILLISECONDS);
    }

    // Getters and Setters

    /**
     * Gets the application of this interview
     * @return Returns the application of this interview
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets the application of this interview
     * @param application The application of this interview
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Gets the interviewer of this interview
     * @return The interviewer of this interview
     */
    public Interviewer getInterviewer() {
        return interviewer;
    }

    /**
     * Sets the interviewer of this interview
     * @param interviewer The interviewer of this interview
     */
    public void setInterviewer(Interviewer interviewer) {
        this.interviewer = interviewer;
    }

    /**
     * Gets the date of this interview
     * @return The date of this interview
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of this interview
     * @param date The date of this interview
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Checks if this interview is completed
     * @return True if this interview is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the status of this interview
     * @param completed The status of this interview
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Checks if applicant is recommended by the interviewer
     * @return True if applicant is recommended by the interviewer, false otherwise
     */
    public boolean isRecommended() {
        return recommended;
    }

    /**
     * Sets the status if applicant is recommended by the interviewer
     * @param recommended If applicant is recommended by the interviewer
     */
    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    /**
     * Gets the notes from this interview
     * @return The notes from this interview
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes from this interview
     * @param notes the notes from this interview
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Interview)) return false;
        Interview i = (Interview) o;

        if (this.getInterviewer() != null && !this.getInterviewer().equals(i.getInterviewer())) return false;
        return this.getApplication().equals(i.getApplication());
    }

    @Override
    public String toString() {
        String statusString = this.isCompleted() ? "Completed" : "Pending";
        if (this.isRecommended()) statusString += ", Recommended";

        return String.format("%s (%s)", this.application.getApplicant().getLegalName(), statusString);
    }
}

