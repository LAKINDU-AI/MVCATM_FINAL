import java.util.Scanner;

public class ATMView {
    private final Scanner scanner = new Scanner(System.in);

    public String getCardNumber() {
        System.out.print("Enter Card Number: ");
        return scanner.nextLine();
    }

    public int getPin() {
        System.out.print("Enter PIN: ");
        return scanner.nextInt();
    }

    public int getMainMenuChoice() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Customer Login");
        System.out.println("2. Technician Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }

    public int getCustomerMenuChoice() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }

    public int getTechnicianMenuChoice() {
        System.out.println("\nTechnician Menu:");
        System.out.println("1. Refill Ink");
        System.out.println("2. Refill Paper");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }

    public String getTechnicianPassword() {
        System.out.print("Enter Technician Password: ");
        return scanner.next();
    }

    public double getAmount(String action) {
        System.out.print("Enter " + action + " amount: ");
        return scanner.nextDouble();
    }

    public void showBalance(double balance) {
        System.out.println("Your balance is: $" + balance);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public int getMenuChoice() {
        return 0;
    }
}
