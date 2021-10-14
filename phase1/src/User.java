import java.io.Serializable;
import java.security.MessageDigest;

public abstract class User implements Serializable {
    //Devin
    private String username;
    private String phoneNumber;
    private String legalName;
    private String email;
    private String password;

    public User(String username, String phoneNumber, String legalName, String email, String password) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.legalName = legalName;
        this.password = this.hash(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param s String
     * @return Stringa after hash.
     */
    private String hash(String s) {
        return Long.toString(s.hashCode());
    }

    /**
     *
     * @param passwd password that the user entered
     * @return true iff the password is valid.
     */
    public boolean validatePassword(String passwd) {
        // Return true if hash(passwd) == this.password
        return this.hash(passwd).equals(this.password);
    }

    public String toString() {
        return String.format("%s (%s)", this.legalName, this.username);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return this.username.equals(u.username);
    }
}
