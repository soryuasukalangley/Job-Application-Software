import java.io.Serializable;
import java.util.ArrayList;

// M
public class Interviewer extends User implements Serializable {
    private Company company;

    Interviewer(String username, String phoneNumber, String legalName, String email, String password) {
        super(username, phoneNumber, legalName, email, password);
        this.company = null;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void unsetCompany() {
        this.company = null;
    }

    public ArrayList<Interview> getPendingInterviews() {
        ArrayList<Interview> ret = new ArrayList<>();
        for (JobPosting posting : this.getCompany().getOngoingPostings()) {
            for (Application app : posting.getApplications()) {
                for (Interview it : app.getInterviews()) {
                    if (it.daysUntil() > 0 && this.equals(it.getInterviewer()))
                        ret.add(it);
                }
            }
        }

        return ret;
    }
}