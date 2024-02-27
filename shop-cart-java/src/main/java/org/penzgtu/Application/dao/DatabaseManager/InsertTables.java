package org.penzgtu.Application.dao.DatabaseManager;

import org.penzgtu.Application.dao.RequestsSQL;
import org.penzgtu.Application.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTables {
    public static void insertUsers(User user) {
        try (Connection connection = RequestsSQL.connect()) {
            String sql = "INSERT INTO Users (id, name, tier) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(2, user.getName());
                preparedStatement.setInt(3, user.getTier());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {e.printStackTrace();}
    }
    public static void insertSession(Session session) {
        try (Connection connection = RequestsSQL.connect()) {
            String sql = "INSERT INTO Sessions (user_id, is_active, date) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, session.getUser_id());
                preparedStatement.setInt(2, session.getIs_active());
                preparedStatement.setString(3, session.getDate().toString());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void insertPasswords(LoginPassword loginPassword) {
        try (Connection connection = RequestsSQL.connect()) {
            String sql = "INSERT INTO Passwords (user_id, login, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, loginPassword.getUser_id());
                preparedStatement.setString(2, loginPassword.getLogin());
                preparedStatement.setString(3, loginPassword.getPassword());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void insertBasket(Cart basket) {
        try (Connection connection = RequestsSQL.connect()) {
            String sql = "INSERT INTO Basket (session_id, product_id, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, basket.getSession_id());
                preparedStatement.setInt(2, basket.getProduct_id());
                preparedStatement.setInt(3, basket.getQuantity());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void insertBalance(UserWallet userWallet) {
        try (Connection connection = RequestsSQL.connect()) {
            String sql = "INSERT INTO UserWallet (user_id, balance) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userWallet.getUser_id());
                preparedStatement.setDouble(2, userWallet.getBalance());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {e.printStackTrace();}
    }
}
