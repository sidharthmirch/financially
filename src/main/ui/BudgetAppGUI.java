package ui;


import model.Account;
import model.Budget;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// GUI implementation of the Budget app
public class BudgetAppGUI {

    private Account account;

    private JFrame frame;

    private JMenuBar menuBar;
    private JPanel mainPanel;
    private AccountPanel accountPanel;
    private JPanel budgetPanel;
    private TransactionsPanel transactionsPanel;
    private JPanel savePanel;

    private CardLayout layout = new CardLayout(0,0);

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/user.json";


    public BudgetAppGUI() {
        frame = new JFrame("Financially");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();
        setupMenuBar();
        centreFrame();
        frame.setVisible(true);
    }



    private void setup() {
        splashScreen();
        // Demo account balance = 2000. Feel free to load account from ./data
        this.account = new Account(2000);
        frame.setBounds(500,500,500,500);
        frame.getContentPane().setLayout(layout);

        mainPanel = new JPanel();
        frame.getContentPane().add(mainPanel, "parent");
        mainPanel.setLayout(layout);


        accountPanel = new AccountPanel(account, "account");
//        budgetPanel = new BudgetPanel(account, "budget");
        transactionsPanel = new TransactionsPanel(account, "transactions", accountPanel);
//        savePanel = new SavePanel(account, "save");

        mainPanel.add(accountPanel, "account");
//        mainPanel.add(budgetPanel, "budget");
        mainPanel.add(transactionsPanel, "transactions");
//        mainPanel.add(savePanel, "save");

        frame.pack();
    }

    private void splashScreen() {
        JWindow window = new JWindow();
        ImageIcon splashImage = new ImageIcon("./data/splashscreen.png");
        window.add(new JLabel(splashImage));
        window.pack();
        centreWindow(window);
        window.setVisible(true);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        window.setVisible(false);
        window.dispose();
    }

    @SuppressWarnings("methodlength")
    private void setupMenuBar() {
        JMenu accountMenu = new JMenu("Account");
        accountMenu.setActionCommand("account");
        accountMenu.addMenuListener(handleMenuClick("account"));


//        JMenu budgetMenu = new JMenu("Budget");
//        budgetMenu.setActionCommand("budget");
//        budgetMenu.addMenuListener(handleMenuClick("budget"));

        JMenu transactionsMenu = new JMenu("Transactions");
        transactionsMenu.setActionCommand("transactions");
        transactionsMenu.addMenuListener(handleMenuClick("transactions"));

        JMenu optionsMenu = new JMenu("Options");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAccount();
            }
        });
        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAccount();
                transactionsPanel.updateTable(account);
            }
        });
        optionsMenu.add(saveMenuItem);
        optionsMenu.add(loadMenuItem);

        menuBar = new JMenuBar();

        menuBar.add(accountMenu);
        // menuBar.add(budgetMenu);
        menuBar.add(transactionsMenu);
        menuBar.add(optionsMenu);

        frame.setJMenuBar(menuBar);
    }

    private MenuListener handleMenuClick(String name) {
        CardLayout cardLayout = (CardLayout)(mainPanel.getLayout());
        MenuListener handleClick = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                cardLayout.show(mainPanel, name);
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

    // EFFECTS: saves the Account to file
    private void saveAccount() {
        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Saved your account with balance $" + account.getBalance() + " to " + JSON_STORE,
                    "Account saved",
                    JOptionPane.OK_OPTION);
            System.out.println("Saved your account with balance $" + account.getBalance() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Account from file
    private void loadAccount() {
        try {
            jsonReader = new JsonReader(JSON_STORE);
            account = jsonReader.read();
            JOptionPane.showMessageDialog(null,
                    "Loaded your account with balance $" + account.getBalance() + " from " + JSON_STORE,
                    "Account loaded",
                    JOptionPane.OK_OPTION);
            System.out.println("Loaded your account with balance $" + account.getBalance() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // From SIGame
    // MODIFIES: this
    // EFFECTS:  location of frame is set so that the frame is centred on the desktop
    private void centreFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screen.width - frame.getWidth()) / 2,
                (screen.height - frame.getHeight()) / 2);
    }

    // Inspired by SIGame's centreFrame
    // MODIFIES: this
    // EFFECTS:  location of frame is set so that the frame is centred on the desktop
    private void centreWindow(JWindow window) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screen.width - window.getWidth()) / 2,
                (screen.height - window.getHeight()) / 2);
    }

    public static void main(String[] args) {
        new BudgetAppGUI();
    }
}
