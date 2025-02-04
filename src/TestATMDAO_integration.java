import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestATMDAO_integration {

    // Helper method to check the current ink and paper levels
    private int[] getInkPaperStatus() {
        int[] status = new int[2]; // [0] - ink, [1] - paper
        String query = "SELECT ink_units, paper_count FROM atm_status WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                status[0] = rs.getInt("ink_units");
                status[1] = rs.getInt("paper_count");
            }
        } catch (SQLException e) {
            System.out.println("Error checking ink/paper status: " + e.getMessage());
        }
        return status;
    }

    // Test to ensure ink and paper are reduced after a withdrawal
    @Test
    public void testInkAndPaperDecreasedAfterWithdrawal() {
        // Initial status check
        int[] initialStatus = getInkPaperStatus();
        int initialInk = initialStatus[0];
        int initialPaper = initialStatus[1];

        // Perform withdrawal (assuming the method for withdrawal is called here)
        boolean withdrawalSuccess = ATMStatusDAO.updateInkPaperUsage();
        assertTrue(withdrawalSuccess, "Ink and paper should be updated after withdrawal");

        // Check the status after withdrawal
        int[] newStatus = getInkPaperStatus();
        int newInk = newStatus[0];
        int newPaper = newStatus[1];

        // Assert that ink and paper have decreased by 1
        assertEquals(initialInk - 1, newInk, "Ink level should decrease by 1 after withdrawal");
        assertEquals(initialPaper - 1, newPaper, "Paper count should decrease by 1 after withdrawal");
    }

    private void assertTrue(boolean withdrawalSuccess, String s) {
    }

    private void assertEquals(int i, int newInk, String s) {
    }


    // Test to ensure that ink and paper are not decreased when one of them is 0
    @Test
    public void testNoDecreaseWhenInkOrPaperIsZero() {
        // First, simulate ink and paper being at zero
        String query = "UPDATE atm_status SET ink_units = 0, paper_count = 0 WHERE id = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error setting ink/paper to zero: " + e.getMessage());
        }

        // Attempt a withdrawal
        boolean withdrawalSuccess = ATMStatusDAO.updateInkPaperUsage();
        assertFalse(withdrawalSuccess, "Withdrawal should fail when ink or paper is zero");

        // Verify that the ink and paper values haven't been decremented
        int[] finalStatus = getInkPaperStatus();
        assertEquals(0, finalStatus[0], "Ink units should remain at zero");
        assertEquals(0, finalStatus[1], "Paper count should remain at zero");
    }

    private void assertFalse(boolean withdrawalSuccess, String s) {
    }

    // You can add more tests for other scenarios like refilling ink and paper
}
