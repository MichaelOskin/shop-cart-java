package org.penzgtu.Application.dao;

import com.github.javafaker.Faker;
import org.penzgtu.Application.dao.DatabaseManager.DatabaseHandler;
import org.penzgtu.Application.dao.DatabaseManager.InsertTables;
import org.penzgtu.Application.domain.*;
import org.penzgtu.Application.util.JsonUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class GenerateData {

    private static Faker faker = new Faker();

    public static void generateAllTables(int n) {
        generateUsers(n); // primary key!
        generatePasswords(n);
        generateBalanceForUserWallet(n);
        generateSessions(n);
        generatePhones();
    }
    public static void generateUsers(int n) {
        for (int i = 0; i < n; i++) {
            InsertTables.insertUsers(new User(-1, faker.name().fullName(), faker.number().numberBetween(1, 10)));
        }
    }

    public static void generateSessions(int n) {
        n+=2;
        for (int i = 2; i < n; i++) {
            LocalDate randomDate = LocalDate.of(faker.number().numberBetween(2023, 2024),
                    faker.number().numberBetween(1, 12), faker.number().numberBetween(1, 28));
            InsertTables.insertSession(new Session(i, 1, randomDate));
        }
    }
    public static void generateBalanceForUserWallet(int n) {
        int lastId = DatabaseHandler.getLastInsertedId("UserWallet", "user_id")+1;
        n += lastId;
        for (int i = lastId; i < n; i++) {
            InsertTables.insertBalance(new UserWallet(i, faker.number().
            randomDouble(2, 0, 1000)));
        }
    }
    public static void generatePasswords(int n) {
        int lastId = DatabaseHandler.getLastInsertedId("Passwords", "user_id")+1;
        n += lastId;
        for (int i = lastId; i < n; i++) {
            InsertTables.insertPasswords(new LoginPassword(i, faker.internet().emailAddress()
                    .replaceFirst("\\.", ""),
                    faker.internet().password(8, 20, true)));
        }
    }

    public static void generatePhones() {
        RequestsSQL.executeUpdate("DELETE FROM Phones;");
        String insertPhonesSQL = "INSERT INTO Phones (Price, Name, Model, NameCPU, Cores, Freq, hDisplay, sDisplay, " +
                "wDisplay, RAM, ROM, type, ver, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = RequestsSQL.connect();
        try (connection) {
            try (PreparedStatement statement = connection.prepareStatement(insertPhonesSQL)) {

                JsonUtil<Phone> json = new JsonUtil<>();
                try  {json.jsonToList(Phone.class);} catch (IOException e) {e.printStackTrace();}

                for (Phone phone: json.getModels()) {
                    statement.setDouble(1, faker.number().
                            randomDouble(10, 100, 1300));
                    statement.setString(2, phone.getName());
                    statement.setString(3, phone.getModel());
                    statement.setString(4, phone.getNameCPU());
                    statement.setInt(5, phone.getCores());
                    statement.setDouble(6, phone.getFreq());
                    statement.setInt(7, phone.getHDisplay());
                    statement.setDouble(8, phone.getSDisplay());
                    statement.setInt(9, phone.getWDisplay());
                    statement.setInt(10, phone.getRam());
                    statement.setInt(11, phone.getRom());
                    statement.setString(12, phone.getType());
                    statement.setString(13, phone.getVer());
                    statement.setString(14, phone.getDate());
                    statement.executeUpdate();
                }

            }
        } catch (SQLException e) {e.printStackTrace();}
    }
}