package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Represents an Account with a balance in dollars, a list of transactions, and a budget
public class Account implements Writable {
    private double balance;
    private Budget budget;
    private List<Transaction> transactionList;

    public Account(double openingBalance) {
        transactionList = new ArrayList<>();
        budget = new Budget(openingBalance / 2);
        balance = openingBalance;
    }

    public Account() {
        transactionList = new ArrayList<>();
        budget = new Budget(0);
        balance = 0;
    }

    public Account(Budget budget, double balance) {
        transactionList = new ArrayList<>();
        this.budget = budget;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public Budget getBudget() {
        return budget;
    }

    // EFFECTS: returns filtered transaction list based on given display option
    // https://www.baeldung.com/java-stream-filter-lambda
    public List<Transaction> getFilteredTransactions(String displayOption) {
        List<Transaction> filtered;
        switch (displayOption) {
            case "Withdrawals":
                filtered = transactionList.stream().filter(t -> !t.isDeposit()).collect(Collectors.toList());
                break;
            case "Deposits":
                filtered = transactionList.stream().filter(t -> t.isDeposit()).collect(Collectors.toList());
                break;
            default:
                filtered = transactionList;
                break;
        }
        return filtered;
    }

    // MODIFIES: this
    // EFFECTS: sets the account transaction list, used when loading save data
    public void loadTransaction(Transaction transaction) {
        this.transactionList.add(transaction);
        EventLog.getInstance().logEvent(new Event("Transaction loaded from save: $" + transaction.getAmount()
                + " TYPE: " + (transaction.isDeposit() ? "DEPOSIT" : "WITHDRAWAL")));
    }

    // MODIFIES: this, budget
    // EFFECTS: records a transaction to the account and records it to the budget too
    public void recordTransaction(Transaction transaction) {
        if (!transaction.isDeposit()) {
            budget.recordTransaction(transaction);
        }
        transactionList.add(transaction);
        balance += transaction.getAmount();

        EventLog.getInstance().logEvent(new Event("Transaction recorded: $" + transaction.getAmount()
                + " TYPE: " + (transaction.isDeposit() ? "DEPOSIT" : "WITHDRAWAL")));
    }

    // EFFECTS: returns summation of all transactions that are not deposits
    public double getSpending() {
        double spending = 0;
        for (Transaction t: transactionList) {
            if (!t.isDeposit()) {
                spending -= (t.getAmount());
            }
        }
        return spending;
    }

    // EFFECTS: creates a JSON object with save data
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", balance);
        json.put("budget", budget.toJson());
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: creates a JSON array of transactionList
    public JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Transaction t : transactionList) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

}
