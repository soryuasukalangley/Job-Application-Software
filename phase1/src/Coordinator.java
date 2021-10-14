import java.io.Serializable;

// Martin
public class Coordinator extends User implements Serializable {
    private Company company;

    public Coordinator(String username, String phoneNumber, String legalName, String email, String password, Company company) {
        super(username, phoneNumber, legalName, email, password);
        this.company = company;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
