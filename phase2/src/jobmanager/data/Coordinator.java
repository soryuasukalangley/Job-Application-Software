package jobmanager.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Coordinator extends User implements Serializable {

    private List<Company> managedCompanies;

    public Coordinator(String username, String phoneNumber, String legalName, String email, String password) {
        super(username, phoneNumber, legalName, email, password);
        this.managedCompanies = new ArrayList<>();
    }

    public List<Company> getManagedCompanies() {
        return managedCompanies;
    }

    public void addManagedCompany(Company c) {
        c.getManager().addCoordinator(this);
        this.managedCompanies.add(c);
    }

    @Override
    public String getFileString() {
        return "coordinator";
    }
}
