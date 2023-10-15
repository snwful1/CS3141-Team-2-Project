import java.util.ArrayList;
import java.util.List;

// A class to represent a financial transaction
public class Transaction {
    private double amount;
    private String description;
    private TransactionType type;

    // Constructor to create a new transaction
    public Transaction(double amount, String description, TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    // Getter methods to access the transaction details
    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction [amount=" + amount + ", description=" + description + ", type=" + type + "]";
    }
}

// A class to manage a list of transactions
public class TransactionManager {
    private List<Transaction> transactions;

    // Constructor to initialize the list of transactions
    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    // Method to add a new transaction to the list
    public void addTransaction(double amount, String description, TransactionType type) {
        Transaction transaction = new Transaction(amount, description, type);
        transactions.add(transaction);
    }

    // Method to get the list of all transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }
}

// An enum to represent the type of a transaction (income or expense)
public enum TransactionType {
    INCOME, EXPENSE
}

public class ExpenseTracker {
    public static void main(String[] args) {
        // Create a TransactionManager to manage transactions
        TransactionManager transactionManager = new TransactionManager();

        // Add some example transactions
        transactionManager.addTransaction(1000.00, "Salary", TransactionType.INCOME);
        transactionManager.addTransaction(50.00, "Groceries", TransactionType.EXPENSE);

        // Get the list of all transactions
        List<Transaction> allTransactions = transactionManager.getTransactions();
        
        // Display all transactions
        for (Transaction transaction : allTransactions) {
            System.out.println(transaction);
        }
    }
}
