package com.example.perfumeservicemaven.Repository;

import java.sql.*;

public class Repository {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/perfume_management";
    private final String USER = "admin";
    private final String PASS = "21092001.Gabi";
    private Connection connection;

    public Repository() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASS);
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public ResultSet executeCommand(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from " + tableName);
        return rs;
    }

    public void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
    }

    public boolean executeUpdate(String sql) {
        boolean result = true;
        Statement stmt = null;
        try {
            openConnection();
            stmt = connection.createStatement();
            int affectedRows = stmt.executeUpdate(sql);
            if (affectedRows == 0)
                result = false;
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ResultSet getTable(String sql) {
        ResultSet result = null;
        try {
            openConnection();
            Statement stmt = connection.createStatement();
            result = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
