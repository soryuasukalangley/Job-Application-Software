import java.io.Serializable;

public class Referrer extends User implements Serializable {
    // Martin
    public Referrer(String username, String phoneNumber, String legalName, String email, String password) {
        super(username, phoneNumber, legalName, email, password);
    }
}
