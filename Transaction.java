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

