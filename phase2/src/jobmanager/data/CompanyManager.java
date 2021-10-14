package jobmanager.data;

import jobmanager.data.*;

import java.io.Serializable;
import java.util.*;

public class CompanyManager implements Serializable {
    private Company company;

    private List<JobPosting> jobs;
    private List<Coordinator> coordinators;
    private List<Interviewer> interviewers;

    public CompanyManager(Company c) {
        this.company = c;

        this.jobs = new ArrayList<>();
        this.coordinators = new ArrayList<>();
        this.interviewers = new ArrayList<>();
    }

    /**
     * Add the a new job posting for current company.
     *
     * @param p new job posting
     */
    public void addJobPosting(JobPosting p) {
        jobs.add(p);
    }

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

    public List<JobPosting> getAllPostings() {
        return this.jobs;
    }

    /**
     * Add another coordinator for the company.
     *
     * @param u new coordinator
     */
    public void addCoordinator(Coordinator u) {
        coordinators.add(u);
    }

    /**
     * Add the interviewr to current company.
     *
     * @param u interviewr
     */
    public void addInterviewer(Interviewer u) {
        interviewers.add(u);
        u.setCompany(this.company);
    }

    /**
     * @return a list of applicant that are applying the comapny.
     */
    public ArrayList<Applicant> getApplicants() {
        // union all applicants for each posting
        HashSet<Applicant> applicantHashSet = new HashSet<>();
        for (JobPosting posting : this.getAllPostings()) {
            applicantHashSet.addAll(posting.getApplicants());
        }

        return new ArrayList<>(applicantHashSet);
    }

    /**
     * @return return a hashmap that contains all the coordinator in the company.
     */
    public List<Coordinator> getCoordinators() {
        return coordinators;
    }

    /**
     * @param username username of the coordinator
     * @return a coordinator with given username
     */
    public Coordinator getCoordinatorByUsername(String username) {
        for (Coordinator c : coordinators) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    /**
     * @return list of interviewer
     */
    public List<Interviewer> getInterviewers() {
        return interviewers;
    }

    /**
     * @param tags Set of strings about the keywords for such posting.
     * @return returns a list of JobPosting with tags in the set.
     */
    public List<JobPosting> getFilteredPostings(Set<String> tags) {
        ArrayList<JobPosting> ret = new ArrayList<>();
        for (JobPosting p : this.company.getManager().getAllPostings()) {
            boolean shouldAdd = false;
            for (String s : tags) {
                if (p.getTags().contains(s))
                    shouldAdd = true;
            }

            if (shouldAdd)
                ret.add(p);
        }
        return ret;
    }
}
