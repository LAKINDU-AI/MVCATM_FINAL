import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class transactionDAO {

    // Save a new transaction
    public static void saveTransaction(transaction transaction, int customerId) {
        String sql = "INSERT INTO transactions (customer_id, transaction_type, amount, transaction_date, balance_after) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                statement.setString(2, transaction.getTransactionType());
                statement.setDouble(3, transaction.getAmount());
                statement.setTimestamp(4, transaction.getTransactionDate());
                statement.setDouble(5, transaction.getBalanceAfter());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all transactions for a specific customer
    public static List<transaction> getTransactionsByCustomerId(int customerId) {
        List<transaction> transactions = new ArrayList<>();
        String sql = "SELECT transaction_type, amount, transaction_date, balance_after FROM transactions WHERE customer_id = ? ORDER BY transaction_date DESC";

        try (Connection connection = DatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String transactionType = resultSet.getString("transaction_type");
                    double amount = resultSet.getDouble("amount");
                    Timestamp transactionDate = resultSet.getTimestamp("transaction_date");
                    double balanceAfter = resultSet.getDouble("balance_after");

                    // Correct class name should be Transaction (not transaction)
                    transaction transaction = new transaction(transactionType, amount, transactionDate, balanceAfter);
                    transactions.add(transaction);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
