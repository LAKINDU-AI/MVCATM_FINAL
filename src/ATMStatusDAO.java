import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATMStatusDAO {

    // Check current ink and paper levels
    public static void checkInkPaperStatus() {
        String query = "SELECT ink_units, paper_count FROM atm_status WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    int ink = rs.getInt("ink_units");
                    int paper = rs.getInt("paper_count");
                    System.out.println("Ink Level: " + ink + " units");
                    System.out.println("Paper Count: " + paper + " sheets");
                } else {
                    System.out.println("ATM status data not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking ink/paper status: " + e.getMessage());
        }
    }

    // Refill ink
    public static void refillInk() {
        String query = "UPDATE atm_status SET ink_units = 5 WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.executeUpdate();
                System.out.println("Ink refilled successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error refilling ink: " + e.getMessage());
        }
    }

    // Refill paper
    public static void refillPaper() {
        String query = "UPDATE atm_status SET paper_count = 5 WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.executeUpdate();
                System.out.println("Paper refilled successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error refilling paper: " + e.getMessage());
        }
    }

    // Decrease ink & paper after each withdrawal
    public static boolean updateInkPaperUsage() {
        String query = "UPDATE atm_status SET ink_units = ink_units - 1, paper_count = paper_count - 1 WHERE id = 1 AND ink_units > 0 AND paper_count > 0";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // Return true if ink & paper were updated
            }
        } catch (SQLException e) {
            System.out.println("Error updating ink/paper usage: " + e.getMessage());
            return false;
        }
    }

    // Method to refill ATM balance to 6000 (for technician)
    public static void refillAtmBalance() {
        String query = "UPDATE atm_status SET atm_balance = 6000 WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.executeUpdate();
                System.out.println("ATM balance has been refilled to $6000.");
            }
        } catch (SQLException e) {
            System.out.println("Error refilling ATM balance: " + e.getMessage());
        }
    }

    // Get current ATM balance (useful for the technician)
    public static double getAtmBalance() {
        String query = "SELECT atm_balance FROM atm_status WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getDouble("atm_balance");
                } else {
                    System.out.println("ATM balance data not found.");
                    return 0.0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving ATM balance: " + e.getMessage());
            return 0.0;
        }
    }
    //getatmbalance
    public static void updateAtmBalance(double newBalance) {
        String sql = "UPDATE atm_status SET atm_balance = ? WHERE id = 1";  // Assuming there's only 1 record in atm_status table

        try (Connection connection = DatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, newBalance);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

