package org.penzgtu.Application.dao.DatabaseManager;

import org.penzgtu.Application.dao.RequestsSQL;
import org.penzgtu.Application.domain.Cart;
import org.penzgtu.Application.domain.LoginPassword;

import java.sql.*;

public class DatabaseHandler {

    public static boolean checkLogin(String login) {
        String sql = "SELECT * FROM Passwords WHERE login = ?";
        try (Connection connection = RequestsSQL.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Registration with such an email exists");
                    return true;
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
        return false;
    }
    public static int activeSession(int user_id) {
        int session = 0;
        Connection connection = RequestsSQL.connect();
        String sql = "SELECT id_session FROM Sessions WHERE is_active = 1 AND user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                session = resultSet.getInt("id_session");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {RequestsSQL.closeConnection(connection);}
        return session;
    }

    public static double checkBalance(int user_id) {
        double balance = 0.;
        try (Connection connection = RequestsSQL.connect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT balance FROM UserWallet WHERE user_id = ?")) {
                preparedStatement.setInt(1, user_id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        balance = resultSet.getDouble("balance");
                        return balance;
                    }
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
        return balance;
    }
    public static void updateBalance(int user_id, double newBalance) {
        try (Connection connection = RequestsSQL.connect()) {
            String sql = "UPDATE UserWallet SET balance = ? WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, newBalance);
                preparedStatement.setInt(2, user_id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void addProductToCart(Cart cart) {
        try (Connection connection = RequestsSQL.connect()) {
            String checkSql = "SELECT quantity FROM Basket WHERE session_id = ? AND product_id = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                checkStatement.setInt(1, cart.getSession_id());
                checkStatement.setInt(2, cart.getProduct_id());

                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int quantity = resultSet.getInt("quantity");
                        String updateSql = "UPDATE Basket SET quantity = ? WHERE session_id = ? AND product_id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setInt(1, quantity + 1);
                            updateStatement.setInt(2, cart.getSession_id());
                            updateStatement.setInt(3, cart.getProduct_id());
                            updateStatement.executeUpdate();
                        }
                    } else {InsertTables.insertBasket(cart);}
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void removeProductFromCart(int session_id, int product_id) {
        try (Connection connection = RequestsSQL.connect()) {
            String sql = "DELETE FROM Basket WHERE session_id = ? AND product_id = ? AND quantity = 1";
            try (PreparedStatement deleteStatement = connection.prepareStatement(sql)) {
                deleteStatement.setInt(1, session_id);
                deleteStatement.setInt(2, product_id);
                deleteStatement.executeUpdate();
            }
            sql = "UPDATE Basket SET quantity = quantity - 1 WHERE session_id = ? AND product_id = ? AND quantity > 1";
            try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
                updateStatement.setInt(1, session_id);
                updateStatement.setInt(2, product_id);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static double calculateTotalPriceForSession(int sessionId) {
        double totalPrice = 0.0;
        try (Connection connection =RequestsSQL.connect();) {
            String sql ="SELECT SUM(p.price * b.quantity) AS total_price " +
                        "FROM Basket b " +
                        "INNER JOIN Phones p ON b.product_id = p.id " +
                        "WHERE b.session_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, sessionId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        totalPrice = resultSet.getDouble("total_price");
                    }
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
        return totalPrice;
    }

    public static int getIdByLoginPassword(LoginPassword loginPassword) {
        int user_id = 0;
        try (Connection connection = RequestsSQL.connect()) {
            String checkSql = "SELECT user_id FROM Passwords WHERE login = ? AND password = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                checkStatement.setString(1, loginPassword.getLogin());
                checkStatement.setString(2, loginPassword.getPassword());
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {user_id = resultSet.getInt("user_id");}
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
        return user_id;
    }

    public static int getLastInsertedId(String table, String column) {
        int lastId = -1;
        try (Connection connection = RequestsSQL.connect();
             Statement statement = connection.createStatement()) {
            String query = "SELECT MAX("+ column + ") FROM " + table;
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {lastId = resultSet.getInt(1);}
            }
        } catch (SQLException e) {e.printStackTrace();}
        return lastId;
    }
}
