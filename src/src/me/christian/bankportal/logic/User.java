package me.christian.bankportal.logic;

import java.util.Objects;

public class User {

    private String userName;
    private String uuid;
    private String email;

    private String passwordHash;

    public User(String userName, String email, String passwordHash) {
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
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

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }
}
