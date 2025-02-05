import java.sql.Timestamp;
import java.util.List;

public class ATMController {
    private static double atmBalance = 6000;  // Default ATM balance of 6000
    private static final String DEPOSIT_TRANSACTION_TYPE = "Deposit";
    private static final String WITHDRAWAL_TRANSACTION_TYPE = "Withdrawal";

    private Customer customer;

    public boolean login(String cardNumber, int enteredPin) {
        Customer result = CustomerDAO.authenticate(cardNumber, enteredPin);
        if (result != null) {
            this.customer = result;
            return true;
        }
        System.out.println("Login failed: Invalid card number or PIN.");
        return false;
    }

    public double getBalance() {
        return customer != null ? customer.getBalance() : 0.0;
    }

    public void deposit(double amount) {
        if (!validateDeposit(amount)) return;
        customer.deposit(amount);
        updateCustomerBalance();
        createAndSaveTransaction(DEPOSIT_TRANSACTION_TYPE, amount, customer.getBalance());
        System.out.println("Successfully deposited $" + amount);
    }

    public void withdraw(double amount) {
        if (!validateWithdrawal(amount)) return;

        // Check if ATM balance is sufficient for the withdrawal
        if (atmBalance < amount) {
            System.out.println("ATM balance is insufficient. Please wait for the technician to refill.");
            return;
        }

        if (!ATMStatusDAO.updateInkPaperUsage()) {
            System.out.println("ATM is out of ink or paper. Please contact a technician.");
            return;
        }

        // Proceed with withdrawal
        atmBalance -= amount;  // Deduct from ATM balance
        customer.withdraw(amount);  // Deduct from customer balance
        updateCustomerBalance();
        createAndSaveTransaction(WITHDRAWAL_TRANSACTION_TYPE, amount, customer.getBalance());

        ATMStatusDAO.updateAtmBalance(atmBalance);

        System.out.println("Successfully withdrew $" + amount);
    }

    public void viewTransactions() {
        List<transaction> transactions = transactionDAO.getTransactionsByCustomerId(customer.getId());
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("Previous Transactions:");
        for (transaction transaction : transactions) {
            System.out.println("Transaction Type: " + transaction.getTransactionType());
            System.out.println("Amount: " + transaction.getAmount());
            System.out.println("Date: " + transaction.getTransactionDate());
            System.out.println("Balance After: " + transaction.getBalanceAfter());
            System.out.println("----");
        }
    }

    private boolean validateDeposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return false;
        }
        return true;
    }

    private boolean validateWithdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }
        if (amount > customer.getBalance()) {
            System.out.println("Insufficient balance for this withdrawal.");
            return false;
        }
        return true;
    }

    private void updateCustomerBalance() {
        CustomerDAO.updateBalance(customer.getId(), customer.getBalance());
    }

    private void createAndSaveTransaction(String transactionType, double amount, double newBalance) {
        transaction transaction = new transaction(transactionType, amount, new Timestamp(System.currentTimeMillis()), newBalance);
        transactionDAO.saveTransaction(transaction, customer.getId());
    }

    // Method for technician to refill ATM balance
    public static void refillAtmBalance() {
        ATMStatusDAO.refillAtmBalance();
    }

    // Get the current ATM balance (used in ATMApp)
    public static double getAtmBalance() {
        return atmBalance;
    }
}
