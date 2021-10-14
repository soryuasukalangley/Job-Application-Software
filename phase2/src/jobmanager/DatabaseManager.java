package jobmanager;

import jobmanager.data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;

    private List<Company> companies;
    private List<User> users;

    private File serializedFile;

    private DatabaseManager() {
    }

    /**
     * Loads the demo data the group will use for the presentation.
     * Not used during production.
     */
    void loadDemoData() {
        this.companies = new ArrayList<>();
        this.users = new ArrayList<>();

        Company c = new Company("Test Company");
        Company c2 = new Company("Test Company (Branch 2)");
        this.companies.add(c);
        this.companies.add(c2);

        Coordinator coordinator = new Coordinator(
                "coordinator",
                "1234567",
                "Legal Coordinator",
                "coordinator@domain.com",
                "password"
        );
        coordinator.addManagedCompany(c);
        coordinator.addManagedCompany(c2);
        this.users.add(coordinator);

        Applicant applicant = new Applicant(
                "applicant",
                "7654321",
                "Legal Applicant",
                "applicant@domain.com",
                "password",
                new Date()
        );
        this.users.add(applicant);

        Interviewer interviewer = new Interviewer(
                "interviewer",
                "7654321",
                "Legal Interviewer",
                "interviewer@domain.com",
                "password"
        );
        c.getManager().addInterviewer(interviewer);
        this.users.add(interviewer);

        try {
            JobPosting posting1 = new JobPosting(c);
            posting1.setName("Test Position");
            posting1.setDescription("This is some text.\nand some more on the next line");
            posting1.setNumReferencesRequired(1);
            posting1.setStartDate(Main.getDateOffset(-5));
            posting1.setEndDate(Main.getDateOffset(5));
            posting1.setReferenceDeadline(Main.getDateOffset(10));
            posting1.getTags().add("tag1");
            posting1.getTags().add("tag2");
            c.getManager().addJobPosting(posting1);

            JobPosting posting2 = new JobPosting(c);
            posting2.setName("In-Progress Position");
            posting2.setDescription("This is some text.\nand some more on the next line");
            posting2.setNumReferencesRequired(1);
            posting2.setStartDate(Main.getDateOffset(-5));
            posting2.setEndDate(Main.getDateOffset(-1));
            posting2.setReferenceDeadline(Main.getDateOffset(10));
            posting2.getTags().add("tag1");
            posting2.getTags().add("tag2");
            posting2.getTags().add("tag3");
            posting2.getTags().add("tag4");
            c.getManager().addJobPosting(posting2);

            Application app = new Application(applicant, posting1,
                    new Material(Material.MaterialType.CURRICULUM_VITAE, new File("/tmp/a")),
                    new Material(Material.MaterialType.COVER_LETTER, new File("/tmp/b")));
            applicant.apply(app);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads from a predefined serialization file
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.serializedFile));
        this.companies = (List<Company>) in.readObject();
        this.users = (List<User>) in.readObject();
        in.close();
    }

    /**
     * Saves to a serialization file
     *
     * @throws IOException
     */
    public void saveToFile() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.serializedFile));
        out.writeObject(this.companies);
        out.writeObject(this.users);
        out.flush();
        out.close();
    }

    /**
     * Gets list of companies
     *
     * @return List of companies
     */
    public List<Company> getCompanies() {
        return this.companies;
    }

    /**
     * Gets list of users
     *
     * @return List of users
     */
    public List<User> getUsers() {
        return this.users;
    }

    /**
     * Sets the file to which the database is read from or serialized to
     *
     * @param serializedFile The file object
     */
    public void setSerializedFile(File serializedFile) {
        this.serializedFile = serializedFile;
    }

    /**
     * Returns the instance
     *
     * @return
     */
    public static DatabaseManager getInstance() {
        if (instance == null)
            instance = new DatabaseManager();

        return instance;
    }
}
