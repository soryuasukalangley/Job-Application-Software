import java.io.Serializable;
import java.util.*;

// M
public class Company implements Serializable {
    private String name;
//    private HashMap<Integer, JobPosting> jobs;
    private ArrayList<JobPosting> jobs;
    private HashMap<String, Coordinator> coordinators;
    private HashMap<String, Interviewer> interviewers;

    public Company(String name) {
        this.name = name;
//        this.jobs = new HashMap<>();
        this.jobs = new ArrayList<>();
        this.coordinators = new HashMap<>();
        this.interviewers = new HashMap<>();
    }

    /**
     * Add the a new job posting for current company.
     * @param p new job posting
     */
    public void addJobPosting(JobPosting p) {
        jobs.add(p);
//        jobs.put(p.getNumReferencesRequired(), p);
    }

    /**
     * Remove job posting p from company.
     * @param p exists job posting
     */
    public void removeJobPosting(JobPosting p) {
        jobs.remove(p);
    }

    /**
     * Return all job posting that end date > current date and start date < current date.
     * @return job postings that are not end yet.
     */
    public ArrayList<JobPosting> getOngoingPostings() {
        ArrayList<JobPosting> results = new ArrayList<>();
        Date today = Calendar.getInstance().getTime();
        jobs.forEach(value -> {
            if (today.after(value.getStartDate()) && today.before(value.getEndDate())) {
                results.add(value);
            }
        });

        return results;
    }

    /**
     * Return all job posting that end date < current date or start date >current date.
     * @return job postings that are ended.
     */

    public ArrayList<JobPosting> getCompletedPostings() {
        ArrayList<JobPosting> results = new ArrayList<>();
        Date today = Calendar.getInstance().getTime();
        jobs.forEach(value -> {
            if (today.after(value.getEndDate())) {
                results.add(value);
            }
        });
        return results;

    }

    /**
     * Add another coordinator for the company.
     * @param u new coordinator
     */
    public void addCoordinator(Coordinator u) {
        coordinators.put(u.getUsername(), u);
        u.setCompany(this);
    }

    /**
     * Remove given coordinator from company.
     * @param u remove given coordinator
     */
    public void removeCoordinator(Coordinator u) {
        if (u.getCompany() != null && u.getCompany().equals(this)) {
//            u.unsetCompany();
            coordinators.remove(u.getUsername());
        }
    }

    /**
     * Add the interviewr to current company.
     * @param u interviewr
     */
    public void addInterviewer(Interviewer u) {
        interviewers.put(u.getUsername(), u);
        u.setCompany(this);
    }

    /**
     * Remove given interviewr from company.
     * @param u interview that to be remove
     */
    public void removeInterviewer(Interviewer u) {
        if (u.getCompany() != null && u.getCompany().equals(this)) {
            u.unsetCompany();
            interviewers.remove(u.getUsername());
        }
    }

    /**
     * Return all applicant in jobposting.
     * @return a list of applicant that are applying the comapny.
     */
    public ArrayList<Applicant> getApplicants() {
        // union all applicants for each posting
        HashSet<Applicant> applicantHashSet = new HashSet<>();
        jobs.forEach(value -> value.getApplications().forEach(application -> applicantHashSet.add(application.getApplicant())));

        return new ArrayList<>(applicantHashSet);
    }

    /**
     *
     * @param a applicant
     * @return a list of application that are applied by the applicant
     */
    public ArrayList<Application> getApplicationsByApplicant(Applicant a) {
        ArrayList<Application> applications = new ArrayList<>();
        jobs.forEach(value -> value.getApplications().forEach(application -> {
            if (application.getApplicant().equals(a)) {
                applications.add(application);
            }
        }));

        return applications;
    }

    /**
     *
     * @return return the name of the company.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return return a hasmap that contains all the coordiantor in the company.
     */
    public HashMap<String, Coordinator> getCoordinators() {
        return coordinators;
    }

    /**
     *
     * @param username username of the coordinator
     * @return a coordinator
     */
    public Coordinator getCoordinator(String username) {
        return coordinators.get(username);
    }

    /**
     *
     * @param username
     * @return
     */
    public Interviewer getInterviewer(String username) {
        return interviewers.get(username);
    }

    /**
     *
     * @return get list of interviewer for the company
     */
    public ArrayList<Interviewer> getInterviewers() {
        return new ArrayList<>(this.interviewers.values());
    }

    public String toString() {
        return this.name;
    }
}
