
import javax.swing.*;
import java.io.*;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Main {

    public static final boolean LOAD_FROM_SERIALIZED_FILE = false;
    public static final String SERIALIZE_FILE_DIR = "./appstate.ser";

    public static ArrayList<Company> companies;
    public static ArrayList<User> users;

    public static ArrayList<Company> getCompanies() {
        return companies;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void viewFile(File f) {
        new FileViewerWindow(f);
    }

    public static ArrayList<JobPosting> getAllPosting() {
        ArrayList<JobPosting> ret = new ArrayList<>();
        for (Company c : Main.getCompanies()) ret.addAll(c.getOngoingPostings());

        return ret;
    }

    public static void init() {
        Main.companies = new ArrayList<>();
        Main.users = new ArrayList<>();
    }

    public static void loadDefault() {
        Company comp = new Company("Test Company");
        Main.companies.add(comp);

        JobPosting posting = new JobPosting();
        posting.setName("Sample Posting");
        posting.setStartDate(getDateOffset(-10));
        posting.setEndDate(getDateOffset(10));
        posting.setReferenceDeadline(getDateOffset(20));
        posting.setDescription("This is a sample job.");
        posting.setCompany(comp);

        Applicant applicant = new Applicant("applicant", "1234567890", "Legal Name", "email", "password", new Date());

        Application app = new Application(applicant, posting, new File("/tmp/a"), new File("/tmp/b"));

        posting.addApplication(app);
        comp.addJobPosting(posting);

        Coordinator c = new Coordinator("coordinator", "1234567890", "Legal Name", "email", "password", comp);
        Interviewer it = new Interviewer("interviewer", "0987654321", "Legal Interviewer", "interviewer@a", "password");
        Referrer referrer = new Referrer("referrer", "128432424", "Legal Referrer", "referrer@a", "password");
        it.setCompany(comp);
        comp.addInterviewer(it);

        try {
            app.selectForInterview(new Interview(it, getDateOffset(10), app));
        } catch (Application.InvalidStateException e) {
            e.printStackTrace();
        }

        users.add(applicant);
        users.add(c);
        users.add(it);
        users.add(referrer);
    }

    public static void loadState() {
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(Main.SERIALIZE_FILE_DIR));
            Main.companies = (ArrayList<Company>) reader.readObject();
            Main.users = (ArrayList<User>) reader.readObject();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveState() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(Main.SERIALIZE_FILE_DIR));
            writer.writeObject(Main.companies);
            writer.writeObject(Main.users);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Date getDateOffset(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, offset);

        return cal.getTime();
    }

    public static void main(String[] args) {
        if (LOAD_FROM_SERIALIZED_FILE) {
            loadState();
        } else {
            init();
            loadDefault();
        }
        new LoginWindow();
    }
}
