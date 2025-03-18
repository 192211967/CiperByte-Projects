import java.io.*;
import java.util.*;

class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            recipient.deposit(amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
    }

    @Override
    public String toString() {
        return accountNumber + "," + accountHolder + "," + balance;
    }
}

public class BankY {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        loadAccounts();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to BankY");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount(scanner);
                    break;
                case 2:
                    depositFunds(scanner);
                    break;
                case 3:
                    withdrawFunds(scanner);
                    break;
                case 4:
                    transferFunds(scanner);
                    break;
                case 5:
                    checkBalance(scanner);
                    break;
                case 6:
                    running = false;
                    saveAccounts();
                    System.out.println("Thank you for using BankY. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter account holder name: ");
        String accountHolder = scanner.nextLine();
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, accountHolder, 0);
        accounts.put(accountNumber, account);
        System.out.println("Account created successfully! Account Number: " + accountNumber);
    }

    private static void depositFunds(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.print("Enter deposit amount: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawFunds(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transferFunds(Scanner scanner) {
        System.out.print("Enter sender account number: ");
        String senderAccountNumber = scanner.nextLine();
        Account senderAccount = accounts.get(senderAccountNumber);
        if (senderAccount != null) {
            System.out.print("Enter recipient account number: ");
            String recipientAccountNumber = scanner.nextLine();
            Account recipientAccount = accounts.get(recipientAccountNumber);
            if (recipientAccount != null) {
                System.out.print("Enter transfer amount: ");
                double amount = scanner.nextDouble();
                senderAccount.transfer(recipientAccount, amount);
            } else {
                System.out.println("Recipient account not found.");
            }
        } else {
            System.out.println("Sender account not found.");
        }
    }

    private static void checkBalance(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolder());
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static String generateAccountNumber() {
        return "ACC" + (accounts.size() + 1);
    }

    private static void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String accountNumber = parts[0];
                String accountHolder = parts[1];
                double balance = Double.parseDouble(parts[2]);
                accounts.put(accountNumber, new Account(accountNumber, accountHolder, balance));
            }
        } catch (IOException e) {
            System.out.println("No existing accounts found. Starting fresh.");
        }
    }

    private static void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts.values()) {
                writer.write(account.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
}