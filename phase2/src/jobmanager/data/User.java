package jobmanager.data;

import java.io.Serializable;

public abstract class User implements Serializable {
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

    /**
     * Gets the username of this user
     * @return The username of this user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the phone number of this user
     * @return The phone number of this user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the legal name of this user
     * @return The legal name of this user
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * Sets the legal name of this user
     * @param legalName the legal name of this user
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     * Gets the email of this user
     * @return The email of this user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the password of this user and hash it
     * @param password The password of this user
     */
    public void setPassword(String password) {
        this.password = this.hash(password);
    }

    /**
     * Returns a string hash
     *
     * @param s String
     * @return String after it's hashed
     */
    private String hash(String s) {
        return Long.toString(s.hashCode());
    }

    /**
     * Checks password against records
     *
     * @param passwd password that the user entered
     * @return true iff the password is valid.
     */
    public boolean validatePassword(String passwd) {
        return this.hash(passwd).equals(this.password);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.legalName, this.username);
    }

    public String getFileString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return this.username.equals(u.username);
    }
}
