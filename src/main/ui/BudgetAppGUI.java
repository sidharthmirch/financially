package ui;


import model.Account;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;

// GUI implementation of the Budget app
public class BudgetAppGUI extends JFrame {

    private Account account;

    private JMenuBar menuBar;
    private JPanel accountPanel;
    private JPanel budgetPanel;
    private JPanel transactionsPanel;
    private JPanel savePanel;
    private JMenuBar navBar;

    public BudgetAppGUI() {
        super("Financially");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();
        centreFrame();
        setVisible(true);
    }

    private void setup() {
        account = new Account();

        accountPanel = new AccountPanel(account, "account");
        budgetPanel = new BudgetPanel(account, "budget");
        transactionsPanel = new TransactionsPanel(account, "transactions");
        savePanel = new SavePanel(account, "save");

        add(accountPanel);

        pack();
    }

    // TODO: check if this is still needed??? Remnant from SIGame
    // MODIFIES: this
    // EFFECTS:  location of frame is set so that the frame is centred on the desktop
    private void centreFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2,
                (screen.height - getHeight()) / 2);
    }

    public static void main(String[] args) {
        new BudgetAppGUI();
    }
}
