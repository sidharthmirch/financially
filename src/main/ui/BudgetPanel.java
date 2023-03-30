package ui;

import model.Account;
import model.Budget;

import javax.swing.*;
import java.awt.*;

public class BudgetPanel extends JPanel {

    public final String name;

    private Account account;
    private JTable table;

    public BudgetPanel(Account acc, String name) {
        this.name = name;
        setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        setLayout(new BorderLayout());
        this.account = acc;
        drawTable();
    }

    private void drawTable() {
        String[] columns = {"Total", "Spent", "Remaining"};
        table = new JTable(generateTableData(), columns);
        this.add(table, BorderLayout.CENTER);
        this.add(table.getTableHeader(), BorderLayout.NORTH);
    }

    // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
    private Object[][] generateTableData() {
        Budget budget = account.getBudget();
        Object[][] data = new Object[1][3];
        data[0][0] = "$" + budget.getSize();
        data[0][1] = "$" + (budget.getSize() - budget.getRemaining());
        data[0][2] = "$" + budget.getRemaining();
        return data;
    }
}
