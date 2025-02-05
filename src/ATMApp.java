import java.util.Scanner;

public class ATMApp {

    public static void main(String[] args) {
        ATMView view = new ATMView();
        ATMController controller = new ATMController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM!");
        System.out.println("1. Customer Login");
        System.out.println("2. Technician Login");
        System.out.print("Choose an option: ");

        int userType = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (userType == 1) {
            // Customer login process
            String cardNumber = view.getCardNumber();
            int pin = view.getPin();

            boolean loginSuccess = controller.login(cardNumber, pin);
            if (!loginSuccess) {
                view.showMessage("Incorrect Card Number or PIN. Exiting...");
                return;
            }

            while (true) {
                int choice = view.getCustomerMenuChoice();
                switch (choice) {
                    case 1 -> view.showBalance(controller.getBalance());
                    case 2 -> controller.deposit(view.getAmount("deposit"));
                    case 3 -> controller.withdraw(view.getAmount("withdraw"));
                    case 4 -> controller.viewTransactions();
                    case 5 -> {
                        view.showMessage("Thank you for using the ATM.");
                        return;
                    }
                    default -> view.showMessage("Invalid option. Please try again.");
                }
            }

        } else if (userType == 2) {
            // Technician login process
            System.out.print("Enter Technician Password: ");
            String password = scanner.nextLine();

            if (!TechnicianDAO.authenticate(password)) {
                System.out.println("Incorrect Password. Exiting...");
                return;
            }

            technicianMenu(); // Call the technician menu

        } else {
            System.out.println("Invalid option. Exiting...");
        }
    }

    public static void technicianMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTechnician Menu:");
            System.out.println("1. Check Ink & Paper Status");
            System.out.println("2. Refill Ink");
            System.out.println("3. Refill Paper");
            System.out.println("4. Check ATM Balance");
            System.out.println("5. Refill ATM Balance");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> ATMStatusDAO.checkInkPaperStatus();
                case 2 -> refillInk();
                case 3 -> refillPaper();
                case 4 -> checkAtmBalance();
                case 5 -> refillAtmBalance();
                case 6 -> {
                    System.out.println("Exiting Technician Mode...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void refillInk() {
        ATMStatusDAO.refillInk();
        System.out.println("Ink refilled successfully.");
    }

    public static void refillPaper() {
        ATMStatusDAO.refillPaper();
        System.out.println("Paper refilled successfully.");
    }

    // Method to check the current ATM balance
    public static void checkAtmBalance() {
        double atmBalance = ATMStatusDAO.getAtmBalance();
        System.out.println("Current ATM Balance: $" + atmBalance);
    }

    // Method to refill the ATM balance to 6000
    public static void refillAtmBalance() {
        ATMStatusDAO.refillAtmBalance();
        System.out.println("ATM balance has been refilled to $6000.");
    }
}
