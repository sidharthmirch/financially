package ui;

import model.Account;
import model.Transaction;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private AccountPanel ap;

    private JTable table;

    private JButton depositBtn = new JButton("Record deposit");
    private JButton withdrawalBtn  = new JButton("Record withdrawal");

    String[] displayOptions = {"All", "Withdrawals", "Deposits"};
    private JComboBox<String> tableDisplaySelection = new JComboBox<>(displayOptions);

    @SuppressWarnings("methodlength")
    public TransactionsPanel(Account acc, String name, AccountPanel ap) {
        this.name = name;
        this.account = acc;
        this.ap = ap;
        Object[][] rowData = generateTableData(account.getTransactionList());
        TransactionTableModel model = new TransactionTableModel(rowData);
        drawTable(model);
        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordTransaction(true);
            }
        });
        withdrawalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordTransaction(false);
            }
        });
        tableDisplaySelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                String displayOption = (String) comboBox.getSelectedItem();
                updateTable(account, displayOption);
            }
        });

        this.add(depositBtn);
        this.add(withdrawalBtn);
        this.add(new JLabel("Display options"));
        this.add(tableDisplaySelection);
    }

    // MODIFIES: this
    // EFFECTS: draw JTable to this panel, inside a JScrollPane so we can scroll for n long list
    private void drawTable(TransactionTableModel model) {
        table = new JTable(model);
        JScrollPane container = new JScrollPane(table);
        this.add(container, BorderLayout.CENTER);
    }

    // MODIFIES: this, this.account
    // EFFECTS: updates account values and repaints table to with new values
    public void updateTable(Account acc, String displayOption) {
        this.account = acc;
        Object[][] rowData = generateTableData(account.getFilteredTransactions(displayOption));
        TransactionTableModel model = new TransactionTableModel(rowData);
        table.setModel(model);
        ap.updateAccount(acc);
        revalidate();
        repaint();
    }


    // MODIFIES: this, this.account
    // EFFECTS: updates account values and repaints table to with new values
    // Default no option: render all
    public void updateTable(Account acc) {
        this.account = acc;
        Object[][] rowData = generateTableData(account.getTransactionList());
        TransactionTableModel model = new TransactionTableModel(rowData);
        table.setModel(model);
        ap.updateAccount(acc);
        revalidate();
        repaint();
    }

//    // EFFECTS: returns filtered transaction list based on given display option
//    // https://www.baeldung.com/java-stream-filter-lambda
//    private List<Transaction> filterTransactions(List<Transaction> originalList, String displayOption) {
//        List<Transaction> filtered;
//        switch (displayOption) {
//            case "Withdrawals":
//                filtered = originalList.stream().filter(t -> !t.isDeposit()).collect(Collectors.toList());
//                break;
//            case "Deposits":
//                filtered = originalList.stream().filter(t -> t.isDeposit()).collect(Collectors.toList());
//                break;
//            default:
//                filtered = originalList;
//                break;
//        }
//        return filtered;
//    }


    // MODIFIES: this, this.account
    // EFFECTS: adds transaction to account then triggers repaint of table
    public void addTransaction(Transaction t) {
        this.account.recordTransaction(t);
        updateTable(this.account);
    }

    // EFFECTS: generates table row data for JTable
    // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
    private Object[][] generateTableData(List<Transaction> transactionList) {
        // Date formatting code inspiration from
        // https://stackoverflow.com/a/27483371
        // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE
        DateTimeFormatter formatter;
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(Locale.CANADA).withZone(ZoneId.systemDefault());


        Object[][] data = new Object[transactionList.size()][3];

        for (int i = 0; i < transactionList.size(); i++) {
            Transaction t = transactionList.get(i);
            data[i][0] = (t.isDeposit() ? "D" : "W");
            data[i][1] = (t.isDeposit() ? t.getAmount() : Math.abs(t.getAmount()));
            data[i][2] = formatter.format(t.getDate());
        }

        return data;
    }

    // MODIFIES: this.account
    // EFFECTS: record a transaction to the account
    private void recordTransaction(boolean isDeposit) {

        String input;
        double amount;

        if (isDeposit) {
            input = JOptionPane.showInputDialog(this,
                    "Enter deposit amount",
                    "$ 0.00");
        } else {
            input = JOptionPane.showInputDialog(this,
                    "Enter withdrawal amount",
                    "$ 0.00");
        }

        // check if input is valid
        try {
            amount = Double.parseDouble(input);
            if (!isDeposit) {
                amount = -amount;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input! Try a number such as 42.50 or 30");
            return;
        }

        Transaction t = new Transaction(amount);
        addTransaction(t);

    }

}
