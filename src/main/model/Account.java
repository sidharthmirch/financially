package model;

import java.util.ArrayList;
import java.util.List;

// Represents an Account with a balance in dollars, a list of transactions, and a budget
public class Account {
    private int balance;
    private List<Transaction> transactionList;
    private Budget budget;

    public Account(int openingBalance) {
        transactionList = new ArrayList<Transaction>();
        budget = new Budget(openingBalance / 2);
        balance = openingBalance;
    }

    public Account() {
        transactionList = new ArrayList<Transaction>();
        budget = new Budget(0);
        balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public Budget getBudget() {
        return budget;
    }

    // MODIFIES: this, budget
    // EFFECTS: records a transaction, and if its a withdrawal records it to the budget too
    public void recordTransaction(int amount) {
        Transaction transaction = new Transaction(amount);
        transactionList.add(transaction);
        balance += transaction.getAmount();
        if (!transaction.isDeposit()) {
            budget.recordTransaction(transaction);
        }
    }
}
