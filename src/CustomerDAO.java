import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    public static Customer authenticate(String cardNumber, int enteredPin) {
        String query = "SELECT id, name, balance FROM customers WHERE card_number = ? AND pin = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, cardNumber);
                stmt.setInt(2, enteredPin);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("id"),
                            rs.getString("name"), "cardNumber",
                            rs.getDouble("balance")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    public static void updateBalance(int customerId, double newBalance) {
        String query = "UPDATE customers SET balance = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setDouble(1, newBalance);
                stmt.setInt(2, customerId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }
}
