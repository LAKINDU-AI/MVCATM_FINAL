import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Customer implements Serializable {

    private final int id;
    private final String name;
    private final String cardNumber;
    private double balance;

    // Simulating a static database for customer authentication
    private static final Map<String, Customer> customerDatabase = new HashMap<>();

    public Customer(int id, String name, String cardNumber, double balance) {
        if (id < 0) throw new IllegalArgumentException("ID cannot be negative.");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty.");
        if (cardNumber == null || cardNumber.isEmpty())
            throw new IllegalArgumentException("Card number cannot be null or empty.");
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative.");

        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.balance = balance;

        // Add to simulated database
        customerDatabase.put(cardNumber, this);
    }

    public static Customer authenticate(String cardNumber) {
        return customerDatabase.get(cardNumber); // Fetch customer from database
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount; // Increase balance
    }

    public synchronized void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance) throw new IllegalStateException("Insufficient balance.");
        balance -= amount; // Decrease balance
    }

    @Override
    public String toString() {
        return "Customer{id=" + id + ", name='" + name + "', cardNumber='" + cardNumber + "', balance=" + balance + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(cardNumber, customer.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber);
    }

    public void setBalance(double newBalance) {
    }
}