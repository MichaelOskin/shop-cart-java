package org.penzgtu.Application.dao.DatabaseManager;

import org.penzgtu.Application.dao.RequestsSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataValidator {
    private final static String loginRegex = "^[a-zA-Z0-9_-]{3,20}@[a-z]+\\.[a-z]+";
    private final static String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    public static boolean isValidPhoneId(int phoneId) {
        try (Connection connection = RequestsSQL.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id FROM Phones WHERE id = ?")) {
                preparedStatement.setInt(1, phoneId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
        return false;
    }

    public static boolean isValidBasketId(int session_id, int product_id) {
        try (Connection connection = RequestsSQL.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Basket WHERE session_id = ? and product_id = ?")) {
                preparedStatement.setInt(1, session_id);
                preparedStatement.setInt(2, product_id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            } catch (SQLException e) {e.printStackTrace();}
        } catch (SQLException e) {e.printStackTrace();}
        return false;
    }

    public static ArrayList<String> validLoginPassword() {
        Scanner scan = new Scanner(System.in);
        String login; String password;
        do {
            System.out.println("Enter your login (from 3 to 20 characters, letters, numbers, hyphens, " +
                    "underscores):");
            login = scan.nextLine();
            if (login.equals("admin")) {break;}
        } while (!(login.matches(loginRegex)));
        do {
            System.out.println("Enter the password (at least 8 characters, at least one uppercase letter, " +
                    "one lowercase letter, one digit):");
            password = scan.nextLine();
        } while (!password.matches(passwordRegex));
        return new ArrayList<>(Arrays.asList(login, password));
    }

    public static boolean DatabaseIsEmpty() {
        try (Connection connection = RequestsSQL.connect()) {
            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery
                    (
                "SELECT name FROM sqlite_master WHERE type='table'"
                    )
            ) {return !resultSet.next();}
        } catch (SQLException e) {e.printStackTrace();}
        return false;
    }
}
