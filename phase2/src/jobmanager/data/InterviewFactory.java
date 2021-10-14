package jobmanager.data;

import jobmanager.data.Application;
import jobmanager.data.InPersonInterview;
import jobmanager.data.Interview;
import jobmanager.data.PhoneInterview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterviewFactory {

    /**
     * Constructs a new interview objects based on type specified
     * @param interviewType
     * @param application
     * @param interviewDate
     * @return
     */
    public Interview constructInterview(String interviewType, Application application, Date interviewDate) {
        if (interviewType.equals("in-person"))
            return new InPersonInterview(application, interviewDate);
        if (interviewType.equals("phone"))
            return new PhoneInterview(application, interviewDate);

        return null;
    }

    /**
     * Returns the interview type string based on provided interview object
     * @param it
     * @return
     */
    public String getInterviewTypeString(Interview it) {
        if (it instanceof InPersonInterview) return "in-person";
        if (it instanceof PhoneInterview) return "phone";
        return "invalid";
    }

    /**
     * Returns a list of available types
     * @return
     */
    public List<String> getAvailableInterviewTypes() {
        List<String> types = new ArrayList<>();
        types.add("phone");
        types.add("in-person");
        return types;
    }
}
