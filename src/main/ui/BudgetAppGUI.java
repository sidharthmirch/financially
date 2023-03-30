package ui;


import model.Account;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GUI implementation of the Budget app
public class BudgetAppGUI {

    private Account account;

    private JFrame frame;

    private JMenuBar menuBar;
    private JPanel mainPanel;
    private JPanel accountPanel;
    private JPanel budgetPanel;
    private JPanel transactionsPanel;
    private JPanel savePanel;

    private CardLayout layout = new CardLayout(0,0);


    public BudgetAppGUI() {
        frame = new JFrame("Financially");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();
        setupMenuBar();
        centreFrame();
        frame.setVisible(true);
    }

    private void setup() {
        account = new Account();
        frame.setBounds(500,500,500,500);
        frame.getContentPane().setLayout(layout);

        mainPanel = new JPanel();
        frame.getContentPane().add(mainPanel, "parent");
        mainPanel.setLayout(layout);


        accountPanel = new AccountPanel(account, "account");
        budgetPanel = new BudgetPanel(account, "budget");
        transactionsPanel = new TransactionsPanel(account, "transactions");
        savePanel = new SavePanel(account, "save");

        mainPanel.add(accountPanel, "account");
        mainPanel.add(budgetPanel, "budget");
        mainPanel.add(transactionsPanel, "transactions");
        mainPanel.add(savePanel, "save");

//        frame.pack();
    }

    @SuppressWarnings("methodlength")
    private void setupMenuBar() {
        JMenu accountMenu = new JMenu("Account");
        accountMenu.setActionCommand("account");
        accountMenu.addMenuListener(handleMenuClick("account"));


        JMenu budgetMenu = new JMenu("Budget");
        budgetMenu.setActionCommand("budget");
        budgetMenu.addMenuListener(handleMenuClick("budget"));

        JMenu transactionsMenu = new JMenu("Transactions");
        transactionsMenu.setActionCommand("transactions");
        transactionsMenu.addMenuListener(handleMenuClick("transactions"));

        JMenu saveMenu = new JMenu("Save");
        saveMenu.setActionCommand("save");
        saveMenu.addMenuListener(handleMenuClick("save"));

        menuBar = new JMenuBar();

        menuBar.add(accountMenu);
        menuBar.add(budgetMenu);
        menuBar.add(transactionsMenu);
        menuBar.add(saveMenu);

        frame.setJMenuBar(menuBar);
    }

    private void switchPanel(String name) {
        layout.show(mainPanel, name);
    }

    private MenuListener handleMenuClick(String name) {
        CardLayout cardLayout = (CardLayout)(mainPanel.getLayout());
        MenuListener handleClick = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                cardLayout.show(mainPanel, name);
//                switchPanel(name);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                //
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                //
            }
        };

        return handleClick;
    }

    // TODO: check if this is still needed??? Remnant from SIGame
    // MODIFIES: this
    // EFFECTS:  location of frame is set so that the frame is centred on the desktop
    private void centreFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screen.width - frame.getWidth()) / 2,
                (screen.height - frame.getHeight()) / 2);
    }

    public static void main(String[] args) {
        new BudgetAppGUI();
    }
}
