package org.penzgtu.Application.dao;

import org.penzgtu.Application.util.ResourceReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class RequestsSQL {

    private static String pathSqlite = null;
    private RequestsSQL() {
    }

    private static void init() throws IOException {
        if (pathSqlite == null) {
            Properties props = new ResourceReader("config.properties").loadFile();
            pathSqlite = "jdbc:sqlite:"+props.getProperty("database.sqlite.path");
        }
    }

    public static Connection connect() {
        Connection connection = null;
        try {
            init();
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(pathSqlite);
        } catch (IOException |ClassNotFoundException | SQLException e) {e.printStackTrace();}
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void executeUpdate(String sql) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            // (INSERT, UPDATE, DELETE)
            statement.executeUpdate(sql);
        } catch (SQLException e) {e.printStackTrace();}
    }
}

