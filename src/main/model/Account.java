package model;

import java.util.ArrayList;
import java.util.List;

// Represents an Account with a balance in dollars, a list of transactions, and a budget
public class Account {
    private int balance;
    private List<Transaction> transactionList;
    private Budget budget;

    public Account(int openingBalance) {
        transactionList = new ArrayList<>();
        budget = new Budget(openingBalance / 2);
        balance = openingBalance;
    }

    public Account() {
        transactionList = new ArrayList<>();
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

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: records a deposit to the account, adds to balance
    public void recordDeposit(int amount) {
        Transaction transaction = new Transaction(amount);
        transactionList.add(transaction);
        balance += transaction.getAmount();
    }

    // REQUIRES: (amount > 0) && (amount < balance)
    // MODIFIES: this, budget
    // EFFECTS: records a transaction to the account and records it to the budget too
    public void recordTransaction(int amount) {
        Transaction transaction = new Transaction(-amount);
        transactionList.add(transaction);
        budget.recordTransaction(transaction);
        balance += transaction.getAmount();
    }

    // EFFECTS: returns summation of all transactions that are not deposits
    public int getSpending() {
        int spending = 0;
        for (Transaction t: transactionList) {
            if (!t.isDeposit()) {
                spending -= (t.getAmount());
            }
        }
        return spending;
    }
}
