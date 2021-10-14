package jobmanager.data;

import java.io.Serializable;
import java.util.*;

public class JobPosting implements Serializable {
    public enum JobPostingStatus {
        NOT_YET_BEGUN("Not yet begun"),
        OPEN("Open to apply"),
        PROCESSING("In Progress"),
        FILLED("Position filled"),
        CLOSED("Posting terminated");

        private final String name;

        private JobPostingStatus(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    private Company company;
    private String name;
    private String description;
    private int numReferencesRequired;


    private int numPositionsOffered;
    private Date endDate;
    private Date startDate;
    private Date referenceDeadline;
    private List<Application> applications;
    private List<InterviewRound> interviewRounds;

    private boolean postingTerminated;

    private Set<String> tags;

    public JobPosting() {
        this.applications = new ArrayList<>();
        this.interviewRounds = new ArrayList<>();
        this.tags = new HashSet<>();
    }

    public JobPosting(Company company) {
        this.applications = new ArrayList<>();
        this.interviewRounds = new ArrayList<>();
        this.tags = new HashSet<>();
        this.company = company;

        this.name = "New Posting";
        this.description = "New Description";
        this.startDate = new Date(0L);
        this.endDate = new Date(Long.MAX_VALUE);
        this.referenceDeadline = new Date(Long.MAX_VALUE);
    }

    /**
     * Checks the status of the posting
     * @return The status of the posting
     */
    public JobPostingStatus getStatus() {
        if (this.postingTerminated) return JobPostingStatus.CLOSED;

        if (this.interviewRounds.size() > 0) {
            for (Interview it : this.getLastInterviewRound().getInterviews()) {
                if (it.getApplication().getStatus() == Application.ApplicationStatus.ACCEPTED)
                    return JobPostingStatus.FILLED;
            }
        }

        if (new Date().before(this.startDate)) return JobPostingStatus.NOT_YET_BEGUN;
        if (new Date().before(this.endDate)) return JobPostingStatus.OPEN;
        if (this.interviewRounds.size() > 0 &&
                this.interviewRounds.get(this.interviewRounds.size() - 1).getInterviewCount() <= 1)
            return JobPostingStatus.FILLED;

        return JobPostingStatus.PROCESSING;
    }

    /**
     * Finds the last found of interview this posting currently has.
     * @return The last round, or null if no interview rounds has been created.
     */
    public InterviewRound getLastInterviewRound() {
        if (this.interviewRounds.size() == 0) return null;
        return this.interviewRounds.get(this.interviewRounds.size() - 1);
    }

    /**
     * Finds the total number of interview rounds
     * @return The total number of interview rounds
     */
    public int getInterviewRoundCount() {
        return this.interviewRounds.size();
    }

    /**
     * Adds a round of interview
     * @param ir The new round of interview
     */
    public void addInterviewRound(InterviewRound ir) {
        this.interviewRounds.add(ir);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JobPosting)) return false;
        JobPosting jp = (JobPosting) o;
        return this.name.equals(jp.getName()) &&
                this.company.equals(jp.getCompany()) &&
                this.getStatus() == jp.getStatus();
    }

    /**
     * Gets the company that created this posting
     * @return The company that created this posting
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company that created this posting
     * @param company The company that created this posting
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Gets the name of current posting
     * @return The name of current posting
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of current posting
     * @param name The name of current posting
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of current posting
     * @return The description of current posting
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of current posting
     * @param description The description of current posting
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the number of reference required for applying to this posting
     * @return The number of reference required for applying to this posting
     */
    public int getNumReferencesRequired() {
        return numReferencesRequired;
    }

    /**
     * Sets he number of reference required for applying to this posting
     * @param numReferencesRequired The number of reference required for applying to this posting
     * @throws Exception When the number of reference required for applying to this posting is negative
     */
    public void setNumReferencesRequired(int numReferencesRequired) throws Exception {
        if (!(numReferencesRequired >= 0))
            throw new Exception("Number of reference required cannot be negative.");
        this.numReferencesRequired = numReferencesRequired;
    }

    /**
     * Gets the number of positions offered for this posting
     * @return The number of positions offered for this posting
     */
    public int getNumPositionsOffered() {
        return numPositionsOffered;
    }

    /**
     * Sets the number of positions offered for this posting
     * @param numPositionsOffered The number of positions offered for this posting
     * @throws Exception When the number of positions offered for this posting is negative
     */
    public void setNumPositionsOffered(int numPositionsOffered) throws Exception {
        if (!(numPositionsOffered >= 0))
            throw new Exception("Number of positions offered cannot be negative.");
        this.numPositionsOffered = numPositionsOffered;
    }

    /**
     * Gets the end date of this posting
     * @return The end date of this posting
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of this posting
     * @param endDate The end date of this posting
     * @throws Exception When the end date is before the start date
     */
    public void setEndDate(Date endDate) throws Exception {
        if (!this.startDate.before(endDate)) throw new Exception("End date cannot be before start date.");
        this.endDate = endDate;
    }

    /**
     * Gets the start date of this posting
     * @return The start date of this posting
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of this posting
     * @param startDate The start date of this posting
     * @throws Exception When the start date is after the end date
     */
    public void setStartDate(Date startDate) throws Exception {
        if (!this.startDate.before(endDate)) throw new Exception("Start date cannot be after end date.");
        this.startDate = startDate;
    }

    /**
     * Gets the reference deadline of this posting
     * @return The reference deadline of this posting
     */
    public Date getReferenceDeadline() {
        return referenceDeadline;
    }

    /**
     * Sets the reference deadline of this posting
     * @param referenceDeadline The reference deadline of this posting
     * @throws Exception When the reference deadline is before start date
     */
    public void setReferenceDeadline(Date referenceDeadline) throws Exception {
        if (!this.startDate.before(referenceDeadline))
            throw new Exception("Reference deadline cannot be before start date.");
        this.referenceDeadline = referenceDeadline;
    }

    /**
     * Gets all the applications that has been submitted to this posting
     * @return All the applications that has been submitted to this posting
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Returns a list of eligible applications. One of the requirements is having a sufficient
     * number of references.
     * @return The list of applications
     */
    public List<Application> getEligibleApplications() {
        List<Application> apps = new ArrayList<>();
        for (Application a : this.applications) {
            if (a.getReferences().size() >= a.getJobPosting().getNumReferencesRequired()) {
                apps.add(a);
            }
        }

        return apps;
    }

    /**
     * Gets a list of all the applicants of this posting
     * @return The list of all the applicants of this posting
     */
    public ArrayList<Applicant> getApplicants() {
        ArrayList<Applicant> ret = new ArrayList<>();
        for (Application a : this.getApplications())
            ret.add(a.getApplicant());

        return ret;
    }

    /**
     * Sets the list of all the applicants of this posting
     * @param applications The list of all the applicants of this posting
     */
    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    /**
     * Add an application to this posting
     * @param app The application to be added
     */
    public void addApplication(Application app) {
        this.applications.add(app);
    }

    /**
     * Ends the posting
     */
    public void terminatePosting() {
        this.postingTerminated = true;
    }


    /**
     * Gets all the tags of this posting
     * @return All the tags of this posting
     */
    public Set<String> getTags() {
        return this.tags;
    }

    /**
     * Sets all the tags of this posting
     * @param tags All the tags of this posting
     */
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String toString() {
        return String.format("%s (%s)", this.getName(), this.getCompany().getName());
    }
}
