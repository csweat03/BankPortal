package me.christian.bankportal.utility;

import me.christian.bankportal.logic.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SQL {

    private String url = "jdbc:mysql://localhost:3306/bpdb";
    private String username = "javaclient";
    private String password = "j@v@Cl1ent";

    public void showTables() {
        System.out.println(queryDatabase("show tables"));
    }

    public void createTable(String tableName, String columns) {
        System.out.println("Trying to create table '" + tableName + "'. With columns '" + columns + "'.");
        updateDatabase("create table " + tableName + " ( " + columns + " );");
    }

    public List<User> populateUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            ResultSet set = connection.createStatement().executeQuery("select * from users;");

            while (set.next()) {
                User user = new User();
                user.setUUID(set.getString("uuid"));
                user.setUserName(set.getString("userName"));
                user.setEmail(set.getString("email"));
                user.setPasswordHash(set.getString("passwordHash"));
                users.add(user);
            }

            for (User user: users)
                System.out.println((user.getUuid() + " " + user.getUserName() + " " + user.getEmail() + " " + user.getPasswordHash()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void addNewUserToDatabase(User user) {
        if (populateUsers().stream().anyMatch(u -> Objects.equals(u.getEmail(), user.getEmail()))) {
            System.err.println("Warning: Could not create account, email already exists.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.createStatement().executeUpdate("insert into users (uuid, userName, email, passwordHash) values (UUID(), '" + user.getUserName() + "',  '" + user.getEmail() + "',  '" + user.getPasswordHash() + "');");
        } catch (SQLException e) {
            System.err.println("Warning: " + e.getMessage() + ".");
        }
        populateUsers();
    }

    public List<String> queryDatabase(String query) {
        List<String> resultSetContent = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            ResultSet set = connection.createStatement().executeQuery(query);

            while (set.next()) {
                resultSetContent.add(set + "");
            }

            return resultSetContent;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateDatabase(String query) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            return connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Warning: " + e.getMessage() + ".");
        }
        return 0;
    }
}
