import java.sql.Timestamp;

public class transaction {
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;
    private double balanceAfter;

    // Constructor, getters, and setters
    public transaction(String transactionType, double amount, Timestamp transactionDate, double balanceAfter) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.balanceAfter = balanceAfter;
    }

    // Getter for transactionType
    public String getTransactionType() {
        return transactionType;
    }

    // Setter for transactionType
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Getter for transactionDate
    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    // Setter for transactionDate
    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    // Getter for balanceAfter
    public double getBalanceAfter() {
        return balanceAfter;
    }

    // Setter for balanceAfter
    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
}
