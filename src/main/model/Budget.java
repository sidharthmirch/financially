package model;

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

    public void recordTransaction(Transaction transaction) {
        if (!transaction.isDeposit()) {
            remaining += transaction.getAmount();
        }
    }
}
