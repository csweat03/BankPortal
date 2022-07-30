package me.christian.bankportal.server.utility;

import me.christian.bankportal.global.GlobalReferences;
import me.christian.bankportal.server.BankPortal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SQL {

    public List<User> addUsersFromDatabase() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(GlobalReferences.DATABASE_URL, References.DATABASE_USERNAME, References.DATABASE_PASSWORD)) {
            String query = "select * from users;";
            BankPortal.log(query);
            ResultSet set = connection.createStatement().executeQuery(query);

            while (set.next()) {
                User user = new User();
                user.setUUID(set.getString("uuid"));
                user.setUserName(set.getString("userName"));
                user.setPassword(set.getString("passwordHash"));
                user.setFirstName(set.getString("firstName"));
                user.setLastName(set.getString("lastName"));
                user.setBirthday(set.getString("birthday"));
                user.setPhoneNumber(set.getString("phone"));
                user.setEmail(set.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void addNewUserToDatabase(User user) {
        if (addUsersFromDatabase().stream().anyMatch(u -> Objects.equals(u.getEmail(), user.getEmail()))) {
            BankPortal.log("Could not create account, email already exists.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(GlobalReferences.DATABASE_URL, References.DATABASE_USERNAME, References.DATABASE_PASSWORD)) {
            String query = String.format("insert into users (uuid,userName,passwordHash,firstName,lastName,birthday,phone,email) values (UUID(), '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                    user.getUserName(), user.getPasswordHash(), user.getFirstName(), user.getLastName(), user.getBirthday(), user.getPhoneNumber(), user.getEmail());
            BankPortal.log(query);
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            BankPortal.log(e.getMessage() + ".");
        }
        addUsersFromDatabase();
    }

    public List<String> queryDatabase(String query) {
        List<String> resultSetContent = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(GlobalReferences.DATABASE_URL, References.DATABASE_USERNAME, References.DATABASE_PASSWORD)) {
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

    public void updateDatabase(String query) {
        try (Connection connection = DriverManager.getConnection(GlobalReferences.DATABASE_URL, References.DATABASE_USERNAME, References.DATABASE_PASSWORD)) {
            BankPortal.log(query);
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            BankPortal.log(e.getMessage() + ".");
        }
    }
}
