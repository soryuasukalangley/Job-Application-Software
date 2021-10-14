package jobmanager.data;

import java.io.Serializable;
import java.util.Date;

public class InPersonInterview extends Interview implements Serializable {
    public InPersonInterview(Application app, Date interviewDate) {
        super(app, interviewDate);
    }
}
