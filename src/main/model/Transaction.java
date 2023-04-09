package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.Instant;

// Represents a Transaction, that contains the date of transaction, amount, and an optional category
public class Transaction implements Writable {
    private double amount;
    private Instant date;
    private String category;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = Instant.now();
    }

    public Transaction(double amount, Instant date) {
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public Instant getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isDeposit() {
        return (amount > 0.0);
    }

    // EFFECTS: creates a JSON object with save data
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("date", date.toString());
        json.put("category", category);
        return json;
    }
}
