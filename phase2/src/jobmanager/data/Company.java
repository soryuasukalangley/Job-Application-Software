package jobmanager.data;

import java.io.Serializable;

public class Company implements Serializable {
    private String name;
    private CompanyManager companyManager;

    public Company(String name) {
        this.name = name;
        this.companyManager = new CompanyManager(this);
    }

    public CompanyManager getManager() {
        return this.companyManager;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Company)) return false;
        return this.name.equals(((Company) o).getName());
    }

    /**
     * @return return the name of the company.
     */
    public String getName() {
        return name;
    }

    public String toString() {
        return this.name;
    }
}
