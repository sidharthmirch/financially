package ui;

import model.Account;
import model.Budget;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetPanel extends JPanel {

    // TODO: Display budget name at top of Panel

    public final String name;

    private Account account;
    private JTable table;
    private JButton editSizeBtn;
    private JButton editNameBtn;

    public BudgetPanel(Account acc, String name) {
        this.name = name;
        setBorder(BorderFactory.createEmptyBorder(10,200,200,200));
        this.account = acc;
        drawTable();
        setupButtons();
    }

    private void drawTable() {
        String[] columns = {"Total", "Spent", "Remaining"};
        table = new JTable(generateTableData(), columns);
        this.add(table.getTableHeader(), BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
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

    @SuppressWarnings("methodlength")
    private void setupButtons() {
        String[] columns = {"Total", "Spent", "Remaining"};
        Budget budget = account.getBudget();
        editSizeBtn = new JButton("Edit budget size");
        editSizeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double newSize = Float.parseFloat(JOptionPane.showInputDialog("Enter your new budget size"));

                if (newSize > 0.0) {
                    budget.setSize(newSize, account.getSpending());
                    table = new JTable(generateTableData(), columns);
                    repaint();
                    revalidate();
                }
                System.out.println(account.getBudget().getSize());
            }
        });


        editNameBtn = new JButton("Edit budget name");
        editNameBtn.addActionListener(new ActionListener() {
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = (String) JOptionPane.showInputDialog("Enter your new budget name");

                if ((newName != null) && (newName.length() > 0)) {
                    account.getBudget().setName(newName);
                }
            }
        });

        add(editSizeBtn);
        add(editNameBtn);
    }
}
