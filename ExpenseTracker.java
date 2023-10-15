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
