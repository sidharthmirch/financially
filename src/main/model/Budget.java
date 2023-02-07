package model;

// Represents a budget, that has a size, remaining in dollars, and an optional name
public class Budget {
    private String name;
    private int size;
    private int remaining;

    public Budget(int size) {
        this.size = size;
        remaining = size;
    }

    public Budget(int size, String name) {
        this.size = size;
        this.name = name;
        remaining = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // MODIFIES: this
    // EFFECTS: subtracts the transaction amount from the budget if it is a deposit
    public void recordTransaction(Transaction transaction) {
        if (!transaction.isDeposit()) {
            remaining += transaction.getAmount();
        }
    }
}
