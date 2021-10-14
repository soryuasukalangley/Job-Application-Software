import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Reference implements Serializable {
    // Martin
    public Applicant applicant;
    public JobPosting posting;
    public Referrer referrer;
//    public ArrayList<File> letters;
    public File letter;

    public Reference(Referrer referrer) {
        this.referrer = referrer;
//        this.letters = new ArrayList<>();
    }

    public JobPosting getPosting() {
        return posting;
    }

    public void setPosting(JobPosting posting) {
        this.posting = posting;
    }
    public void setLetter(File f) {
        this.letter = f;
    }

    public File getLetter() {
        return this.letter;
    }

    public void setApplicant(Applicant app) {
        this.applicant = app;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public String toString() {
        return String.format("%s for %s", this.referrer.getLegalName(), this.posting.getName());
    }
}
