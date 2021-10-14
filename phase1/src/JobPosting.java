
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
// Devin
public class JobPosting implements Serializable {
    private Company company;
    private String name;
    private String description;
    private Enum status;
    private int numSpots;
    private int numReferencesRequired;
    private Date endDate;
    private Date startDate;
    private Date referenceDeadline;
    private ArrayList<Application> applications;

    public JobPosting() {
        this.applications = new ArrayList<>();
    }

    public JobPosting(Company company) {
        this.applications = new ArrayList<>();
        this.company = company;

        this.name = "New Posting";
        this.description = "New Description";
        this.startDate = new Date(0L);
        this.endDate = new Date(Long.MAX_VALUE);
        this.referenceDeadline = new Date(Long.MAX_VALUE);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public int getNumSpots() {
        return numSpots;
    }

    public void setNumSpots(int numSpots) {
        this.numSpots = numSpots;
    }

    public int getNumReferencesRequired() {
        return numReferencesRequired;
    }

    public void setNumReferencesRequired(int numReferencesRequired) {
        this.numReferencesRequired = numReferencesRequired;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReferenceDeadline() {
        return referenceDeadline;
    }

    public void setReferenceDeadline(Date referenceDeadline) {
        this.referenceDeadline = referenceDeadline;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public ArrayList<Applicant> getApplicants() {
        ArrayList<Applicant> ret = new ArrayList<>();
        for (Application a : this.getApplications())
            ret.add(a.getApplicant());

        return ret;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application app) {
        this.applications.add(app);
    }

    public String toString() {
        return String.format("%s (%s)", this.getName(), this.getCompany().getName());
    }
}
