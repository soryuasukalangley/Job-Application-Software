package jobmanager.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Interviewer extends User implements Serializable {
    private Company company;

    public Interviewer(String username, String phoneNumber, String legalName, String email, String password) {
        super(username, phoneNumber, legalName, email, password);
        this.company = null;
    }

    /**
     * Gets the company of this interviewer
     * @return The company of this interviewer
     */
    public Company getCompany() {
        return company;
    }

    /**
     * The company of this interviewer
     * @param company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     *
     * @return returns a list of all interviews assigned to an interviewer before the interview dates.
     */
    public ArrayList<Interview> getPendingInterviews() {
        ArrayList<Interview> ret = new ArrayList<>();
        for (JobPosting posting : this.getCompany().getManager().getAllPostings()) {
            if (posting.getLastInterviewRound() != null)
                ret.addAll(posting.getLastInterviewRound().getInterviews());
        }

        return ret;
    }

    @Override
    public String getFileString() {
        return "interviewer";
    }
}