package me.christian.bankportal.logic;

import java.util.Objects;

public class User {

    private String userName;
    private String email;

    private String passwordHash;

    public User(String userName, String email, String passwordHash) {
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public boolean comparePasswordHash(String passwordHash) {
        return Objects.equals(passwordHash, this.passwordHash);
    }
}
