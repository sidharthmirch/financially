package ui;

import model.Account;
import model.Budget;

import javax.swing.*;
import java.awt.*;

public class AccountPanel extends JPanel {

    public final String name;

    private Account account;
    private JTable table;

    public AccountPanel(Account acc, String name) {
        this.name = name;
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//        setLayout(new BorderLayout());
        this.account = acc;
        Object[][] rowData = generateTableData(account);
        AccountTableModel model = new AccountTableModel(rowData);
        drawTable(model);
    }

    // MODIFIES: this
    // EFFECTS: updates account values and repaints panel to show new values
    public void updateAccount(Account acc) {
        this.account = acc;
        Object[][] rowData = generateTableData(account);
        AccountTableModel model = new AccountTableModel(rowData);
        table.setModel(model);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: draw JTable to this panel
    private void drawTable(AccountTableModel model) {
        table = new JTable(model);
        JScrollPane container = new JScrollPane(table);
        // this.add(table.getTableHeader(), BorderLayout.NORTH);
        this.add(container, BorderLayout.CENTER);
    }

    // EFFECTS: generates table row data for JTable
    // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
    private Object[][] generateTableData(Account acc) {
        Budget budget = acc.getBudget();
        Object[][] data = new Object[1][3];
        data[0][0] = "$" + acc.getBalance();
        data[0][1] = "$" + budget.getSize();
        data[0][2] = "$" + budget.getRemaining();
        return data;
    }
}
