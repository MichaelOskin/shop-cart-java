package org.penzgtu.Application.dao;

import org.penzgtu.Application.util.ResourceReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CreateTableSQLite {

    private static void createTable(String sql, String name) {
        try (Connection connection = RequestsSQL.connect();
             Statement statement = connection.createStatement()) {
             statement.execute(sql);
             System.out.println("Table " + name + " has been created.");
        } catch (SQLException e) {System.out.println("Error: " + e.getMessage());}
    }
    public static void createPhones() {
        createTable
                (
                "CREATE TABLE IF NOT EXISTS Phones (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Price REAL," +
                    "Name TEXT," +
                    "Model TEXT," +
                    "NameCPU TEXT," +
                    "Cores INTEGER," +
                    "Freq REAL," +
                    "hDisplay INTEGER," +
                    "sDisplay DOUBLE," +
                    "wDisplay INTEGER," +
                    "RAM INTEGER," +
                    "ROM INTEGER," +
                    "type TEXT," +
                    "ver TEXT," +
                    "date DATE" +
                    ");",
              "PHONES"
                );
    }

    public static void createUsers() {
            createTable
                    (
                    "CREATE TABLE IF NOT EXISTS Users (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "name TEXT," +
                         "tier INTEGER CHECK (tier >= 0 AND tier <= 10)" +
                         ");",
                   "USERS"
                    );
    }
    public static void createBasket() {
        createTable
                (
            "CREATE TABLE IF NOT EXISTS Basket (" +
                "\"session_id\" INTEGER NOT NULL," +
                "\"product_id\" INTEGER," +
                "quantity INTEGER NOT NULL," +
                "FOREIGN KEY (session_id) REFERENCES Sessions(id_session)," +
                "FOREIGN KEY (product_id) REFERENCES Phones(id)" +
                ");",
              "BASKET"
                );
    }

    public static void createSession() {
        createTable
                (
            "CREATE TABLE IF NOT EXISTS Sessions (" +
                "\"id_session\" INTEGER PRIMARY KEY AUTOINCREMENT," +
                "\"user_id\" INTEGER," +
                "\"is_active\" INTEGER NOT NULL," + // - 0 - false; 1 - true
                "date DATE," +
                "FOREIGN KEY (user_id) REFERENCES Users(id)" +
                ");",
          "SESSIONS"
                );
    }

    public static void createStoragePasswordLogin() {
        createTable
                (
                "CREATE TABLE IF NOT EXISTS Passwords (" +
                    "\"user_id\" INTEGER," +
                    "login TEXT PRIMARY KEY," +
                    "password TEXT NOT NULL," +
                    "FOREIGN KEY (\"user_id\") REFERENCES Users(id)" +
                    ");",
              "PASSWORDS"
                );
    }

    public static void createUserWallet() {
        createTable
                (
                "CREATE TABLE IF NOT EXISTS UserWallet (" +
                    "\"user_id\" INTEGER PRIMARY KEY," +
                    "balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00," +
                    "FOREIGN KEY (user_id) REFERENCES Users(id)" +
                    ");",
              "USERWALLET"
                );
    }

    public static void createAnyTable(int n) {
        switch (n) {
            case 1:
                createUsers();
                break;
            case 2:
                createStoragePasswordLogin();
                break;
            case 3:
                createUserWallet();
                break;
            case 4:
                createBasket();
                break;
            case 5:
                createPhones();
                break;
            case 6:
                createSession();
            default:
                System.out.println("Error input");
        }
    }

    public static void outActiveTables() {
        try (Connection connection = RequestsSQL.connect()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            try (var resultSet = metaData.getTables(null, null, "%", types)) {
                while (resultSet.next()) {
                    System.out.print(resultSet.getString("TABLE_NAME") + "   ");
                }
                System.out.println();
            }
        } catch (SQLException e) {e.printStackTrace();}
    }

    public void InitialDatabase() throws IOException {
        Properties props = new ResourceReader("config.properties").loadFile();
        InputStream inputStream = new ResourceReader(props.getProperty("script.sql.path")).getStream();
        System.out.println("Script Database success with: " + props.getProperty("script.sql.path") + "\n");
        System.out.printf("Default admin login&password: %s;%s\n\n", props.getProperty("authentication.username"),
                props.getProperty("authentication.password"));
        try (Connection conn = RequestsSQL.connect(); Statement stmt = conn.createStatement()) {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String script = new String(buffer);
            stmt.executeUpdate(script);
        } catch (Exception e) {e.printStackTrace();}
    }
}
