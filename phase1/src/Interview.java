import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

// M
public class Interview implements Serializable {
    private Interviewer interviewer;
    private Date interviewDate;
    private String note;
    private Application application;

    public Interview(Interviewer interviewer, Date interviewDate, Application application) {
        this.interviewer = interviewer;
        this.interviewDate = interviewDate;
        this.application = application;
    }

    public long daysUntil() {
        long diffInMill = this.interviewDate.getTime() - (new Date()).getTime();
        return TimeUnit.DAYS.convert(diffInMill, TimeUnit.MILLISECONDS);
    }

    public String getNote() {
        if (this.note == null) return "";
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public Application getApplication() {
        return application;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public Applicant getInterviewee(){
        return this.application.getApplicant();
    }

    public String toString() {
        return String.format("with %s on %s", this.interviewer.getLegalName(),
                new SimpleDateFormat("yyyy-MM-dd").format(this.interviewDate));
    }
}
