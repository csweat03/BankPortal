package me.christian.bankportal.server.utility;

import java.util.Arrays;
import java.util.Objects;

public class User {

    private String userName;
    private String uuid;
    private String passwordHash;

    private String verifiedID = "";

    private String firstName, lastName;
    private String birthday;
    private String phoneNumber;
    private String email;

    public User(String userName, char[] password, String firstName, String lastName, String birthday, String phone, String email) {
        setUserName(userName);
        updatePassword(password);

        setFirstName(firstName);
        setLastName(lastName);
        setBirthday(birthday);
        setPhoneNumber(phone);
        setEmail(email);
    }

    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean comparePasswordHash(String passwordHash) {
        return Objects.equals(passwordHash, this.passwordHash);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String hash) {
        this.passwordHash = hash;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updatePassword(char[] password) {
        this.passwordHash = Cryptography.encrypt(password);
        Arrays.fill(password, (char) 0);
    }

    public void updateVerifiedID(String newID) {
        if (this.verifiedID == null)
            this.verifiedID = newID;
    }

    public void lockAccount() {
        this.verifiedID = "";
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
