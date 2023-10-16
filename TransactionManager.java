import java.util.ArrayList;
import java.util.List;

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
