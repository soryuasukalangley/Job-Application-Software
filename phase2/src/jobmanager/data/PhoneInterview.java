package jobmanager.data;

import java.io.Serializable;
import java.util.Date;

public class PhoneInterview extends Interview implements Serializable {

    public PhoneInterview(Application app, Date interviewDate) {
        super(app, interviewDate);
    }
}

