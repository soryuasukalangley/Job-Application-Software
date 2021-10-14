package jobmanager.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterviewRound implements Serializable {
    private JobPosting jobPosting;
    private List<Interview> interviews;

    public InterviewRound(JobPosting p, List<Interview> it) {
        this.jobPosting = p;
        this.interviews = it;
    }

    /**
     * Check if this interview round contains a specific application.
     * @param application the application to be checked.
     * @return True if it exists in the next round, false otherwise.
     */
    private boolean containsApplication(Application application) {
        for (Interview i : this.interviews) {
            if (i.getApplication().equals(application))
                return true;
        }
        return false;
    }

    public int getInterviewCount() {
        return this.interviews.size();
    }

    /**
     *
     * @param applications  The list of applications for which new interviews are created
     * @param interviewType Either "phone" or "in-person". Letter cases ignored.
     * @return A new interview round based on the given list of applications and type
     * @throws Exception
     */
    public InterviewRound getNextRound(List<Application> applications, String interviewType, Date interviewDate) throws Exception {
        List<Interview> newInterviews = new ArrayList<>();
        for (Application app : applications) {
            // Make sure application list is valid
            if (!this.containsApplication(app))
                throw new Exception("Invalid list of applications.");

            // Add interview object to new list of interviews
            InterviewFactory interviewFactory = new InterviewFactory();
            Interview interview = interviewFactory.constructInterview(interviewType, app, interviewDate);
            if (interview == null)
                throw new Exception("Invalid interview type.");

            newInterviews.add(interview);
        }

        // Reject those not selected.
        for (Interview i : this.interviews) {
            if (!applications.contains(i.getApplication())) {
                i.getApplication().reject();
            }
        }

        return new InterviewRound(this.jobPosting, newInterviews);
    }

    /**
     * @return The list of interviews in the current round
     */
    public List<Interview> getInterviews() {
        return this.interviews;
    }

}

