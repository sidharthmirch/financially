package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a budget, that has a size, remaining in dollars, and an optional name
public class Budget implements Writable {
    private String name;
    private double size;
    private double remaining;

    public Budget(double size) {
        this.size = size;
        remaining = size;
    }

    public Budget(double size, String name) {
        this.size = size;
        this.name = name;
        remaining = size;
    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public double getRemaining() {
        return remaining;
    }

    // MODIFIES: this
    // EFFECTS: sets budget remaining - only used for loading JSON data
    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: sets new budget size, and updates remaining
    public void setSize(double newSize, double currentSpend) {
        this.size = newSize;
        this.remaining = newSize - currentSpend;
    }

    // MODIFIES: this
    // EFFECTS: subtracts the transaction amount from the remainder of budget
    public void recordTransaction(Transaction transaction) {
        if (!transaction.isDeposit()) {
            remaining += transaction.getAmount();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("size", size);
        json.put("remaining", remaining);
        return json;
    }
}
