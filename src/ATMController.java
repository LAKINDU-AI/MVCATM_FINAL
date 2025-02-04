public class ATMController {
    private Customer customer;

    public Customer login(String cardNumber, int enteredPin) {
        customer = CustomerDAO.authenticate(cardNumber, enteredPin);
        return customer;
    }

    public double getBalance() {
        return customer.getBalance();
    }

    //public Customer getCustomer() {
        //return customer;


    public void deposit(double amount) {
        if (amount > 0) {
            double newBalance = customer.getBalance() + amount;
            customer.setBalance(newBalance);
            CustomerDAO.updateBalance(customer.getId(), newBalance);
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > customer.getBalance()) {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
            return;
        }

        // Check if there is enough ink and paper for transaction
        if (!ATMStatusDAO.updateInkPaperUsage()) {
            System.out.println("Withdrawal failed! ATM is out of ink or paper. Please contact a technician.");
            return;
        }

        // Perform withdrawal
        double newBalance = customer.getBalance() - amount;
        customer.setBalance(newBalance);
        CustomerDAO.updateBalance(customer.getId(), newBalance);

        System.out.println("Successfully withdrew $" + amount);
    }
    // This method now uses CustomerDAO to authenticate the customer


    // Other methods (deposit, withdraw, etc.) will go here
}

    // Other methods (deposit, withdraw, etc.) will go here


