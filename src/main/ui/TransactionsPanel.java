package ui;

import model.Account;
import model.Transaction;


import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class TransactionsPanel extends JPanel {

    public final String name;

    private Account account;
    private List<Transaction> transactionList;
    private JTable table;

    public TransactionsPanel(Account acc, String name) {
        this.name = name;
        setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        setLayout(new BorderLayout());
        this.account = acc;
        transactionList = account.getTransactionList();
        drawTable();
    }

    private void drawTable() {
        String[] columns = {"Type", "Amount", "Date"};
        table = new JTable(generateTableData(), columns);
        JScrollPane container = new JScrollPane(table);
        this.add(container, BorderLayout.CENTER);
    }

    // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
    private Object[][] generateTableData() {
        // Date formatting code inspiration from
        // https://stackoverflow.com/a/27483371
        // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE
        DateTimeFormatter formatter;
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(Locale.CANADA).withZone(ZoneId.systemDefault());

        // Table data generation code guided by java docs
        // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
        Object[][] data = new Object[transactionList.size()][3];
        for (int i = 0; i < transactionList.size(); i++) {
            Instant date = transactionList.get(i).getDate();
            String formattedDate = formatter.format(date);
            // Setting 3 variables from Transaction for use in JTable
            if (transactionList.get(i).isDeposit()) {
                data[i][0] = "D";
            } else {
                data[i][0] = "W";
            }
            data[i][1] = "$" + Math.abs(transactionList.get(i).getAmount());
            data[i][2] = formattedDate;
        }
        return data;
    }

    // MODIFIES: this
    // EFFECTS: record a transaction to the account
    private void recordTransaction() {
        double amount;
        JTextField inputAmount = new JTextField(9);
        //= Float.parseFloat(inputAmount.getText());

//        double amount;
//        System.out.printf("+   NEW TRANSACTION   +%n");
//        System.out.printf("%n| Amount = $");
//        amount = input.nextDouble();
//        if (amount > account.getBalance()) {
//            System.out.printf("Your account balance is too low to complete this transaction!%n");
//            accountMenu();
//            return;
//        }
//        Transaction transaction = new Transaction(-amount, Instant.now());
//        account.recordTransaction(transaction);
//        System.out.printf("Your transaction of $%.2f has been recorded, enter a command to continue: ", amount);
//
//        clear();
//        transactionList();
    }

}
