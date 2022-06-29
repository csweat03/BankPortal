package me.christian.bankportal.utility;

import java.sql.*;

public class SQL {

    private String url = "jdbc:mysql://localhost:3306/bpdb";
    private String username = "javaclient";
    private String password = "j@v@Cl1ent";

    private Connection connection;

    public void initializeConnection() {
        if (connection != null) return;

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            this.connection = connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void getTables() {
        ResultSet set = processQuery("show tables");

        if (set == null) return;

        try {
            while(set.next()) {
                System.out.println(set);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!set.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public ResultSet processQuery(String query) {
        initializeConnection();

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
