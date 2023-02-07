package model;

import java.util.Date;

// Represents a Transaction, that contains the date of transaction, amount, and an optional category
public class Transaction {
    private int amount;
    private Date date;
    private String category;

    public Transaction(int amount) {
        this.amount = amount;
        this.date = new Date();
    }

    public Transaction(int amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isDeposit() {
        return (amount > 0);
    }
}
