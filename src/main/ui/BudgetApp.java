package ui;

import exceptions.CrashException;
import model.Account;
import model.Budget;
import model.Transaction;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Math.abs;

// Console based implementation of the Budget app
public class BudgetApp {

    private Account account;

    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String JSON_STORE = "./data/user.json";


    public BudgetApp() throws CrashException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        run();
    }

    // MODIFIES: this
    // EFFECTS: handles user input and keeps app running
    private void run() {
        String cmd;

        setup();

        while (true) {
            System.out.print("To continue, please type a command and press enter: ");
            cmd = input.next().toLowerCase();
            if (cmd.equals("q")) {
                String choice;
                System.out.print("Would you like to save your session? [y/n]: ");
                choice = input.next().toLowerCase();
                if (choice.equals("y")) {
                    saveAccount();
                }
                break;
            } else {
                handleInput(cmd);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes account and prints welcome message
    private void setup() {
        String choice;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.printf("Welcome to Financially.%n");
        System.out.print("Load session file? [y/n]: ");
        choice = input.next().toLowerCase();
        clear();
        if (choice.equals("y")) {
            loadAccount();
        } else {
            createAccount();
        }

        accountMenu();
    }


    // MODIFIES: this
    // EFFECTS: creates a new user account and budget for demo purposes
    private void createAccount() {
        double openingBalance;
        double budgetSize;
        System.out.println("Let's create a new account.");
        System.out.print("Please enter your opening balance: ");
        openingBalance = input.nextDouble();
        clear();
        System.out.println("Now let's create a budget.");
        System.out.print("Please set your budget size: ");
        budgetSize = input.nextDouble();

        account = new Account(new Budget(budgetSize), openingBalance);

        clear();
        commandList();
    }

    // MODIFIES: this
    // EFFECTS: handle user input
    @SuppressWarnings("methodlength")
    private void handleInput(String cmd) {
        clear();
        switch (cmd) {
            case "a":
                accountMenu();
                break;
            case "b":
                budgetMenu();
                break;
            case "d":
                recordDeposit();
                break;
            case "r":
                recordTransaction();
                break;
            case "t":
                transactionList();
                break;
            case "s":
                saveAccount();
                break;
            case "l":
                loadAccount();
                break;
            default:
                System.out.printf("Something went wrong!%n");
                commandList();
        }
    }

    // EFFECTS: display available commands to user
    private void commandList() {
        System.out.printf("+           COMMANDS            +%n");
        System.out.printf("+-------------------------------+%n");
        System.out.printf("| a - view your account         |%n");
        System.out.printf("| b - view your budget          |%n");
        System.out.printf("| d - record a deposit          |%n");
        System.out.printf("| r - record a new transaction  |%n");
        System.out.printf("| t - view all your transactions|%n");
        System.out.printf("| h - view this menu again      |%n");
        System.out.printf("| s - save current session      |%n");
        System.out.printf("| l - load saved session        |%n");
        System.out.printf("| q - quit the application      |%n");
        System.out.printf("+-------------------------------+%n");
    }

    // EFFECTS: display user account information
    private void accountMenu() {
        System.out.printf("+       YOUR ACCOUNT     +%n");
        System.out.printf("+------------------------+%n");
        System.out.printf("| Balance   | $%-4.2f   |%n", account.getBalance());
        System.out.printf("| Budget    | $%-4.2f   |%n", account.getBudget().getSize());
        System.out.printf("| Remaining | $%-4.2f   |%n", account.getBudget().getSize() - account.getSpending());
        System.out.printf("+------------------------+%n");
    }

    // EFFECTS: display user budget information
    private void budgetMenu() {
        if (account.getBudget().getName() != null) {
            System.out.printf("+      %-16s +%n", account.getBudget().getName().toUpperCase());
        } else {
            System.out.printf("+           YOUR BUDGET         +%n");
        }
        System.out.printf("+-------------------------------+%n");
        System.out.printf("| Total     | $%-10.2f    |%n", account.getBudget().getSize());
        System.out.printf("| Spent     | $%-10.2f    |%n", account.getSpending());
        System.out.printf("| Remaining | $%-10.2f    |%n", account.getBudget().getSize() - account.getSpending());
        System.out.printf("+-------------------------------+%n");

        editBudget();
    }

    // EFFECTS: prompts user to select between editing budget size or name
    private void editBudget() {
        // force entry into loop for further input, with reference to TellerApp
        String cmd = "";

        while (!(cmd.equals("n") || cmd.equals("s") || cmd.equals("h"))) {
            System.out.printf("+           COMMANDS            +%n");
            System.out.printf("+-------------------------------+%n");
            System.out.printf("| n - edit budget name          |%n");
            System.out.printf("| s - edit budget size          |%n");
            System.out.printf("| h - exit back to command menu |%n");
            System.out.print("To continue, please type a command and press enter: ");
            cmd = input.next().toLowerCase();
        }

        if (cmd.equals("n")) {
            editBudgetName();
        } else if (cmd.equals("s")) {
            editBudgetSize();
        } else {
            commandList();
        }
    }

    private void editBudgetName() {
        String name;
        System.out.print("Enter your new budget name: ");
        name = input.next();
        account.getBudget().setName(name);
        System.out.printf("Successfully renamed budget to \"%s\".%n", account.getBudget().getName());
    }

    private void editBudgetSize() {
        double size;
        System.out.print("Enter your new budget size: ");
        size = input.nextInt();
        account.getBudget().setSize(size, account.getSpending());
        System.out.printf("Successfully changed budget to $%.2f.%n", account.getBudget().getSize());
    }

    // EFFECTS: display all user transactions
    private void transactionList() {
        // Date formatting code inspiration from
        // https://stackoverflow.com/a/27483371
        // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE
        DateTimeFormatter formatter;
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(Locale.CANADA).withZone(ZoneId.systemDefault());
        System.out.printf("+       USER TRANSACTIONS      +%n");
        System.out.printf("+-----------------+------------+%n");
        System.out.printf("| Type | Amount   | Date       |%n");
        System.out.printf("+------+-----------------------+%n");

        // Table generation code inspiration from
        // https://stackoverflow.com/questions/15215326/how-can-i-create-table-using-ascii-in-a-console
        for (Transaction t : account.getTransactionList()) {
            double amount = t.getAmount();
            Instant date = t.getDate();
            String formattedDate = formatter.format(date);

            System.out.printf("|  %-4s| %-8s | %-5s |%n",
                    (t.isDeposit() ? "D" : "W"), "$" + abs(amount),
                    formattedDate);
        }
    }

    // MODIFIES: this
    // EFFECTS: record a transaction to the account
    private void recordTransaction() {
        double amount;
        System.out.printf("+   NEW TRANSACTION   +%n");
        System.out.printf("%n| Amount = $");
        amount = input.nextDouble();
        if (amount > account.getBalance()) {
            System.out.printf("Your account balance is too low to complete this transaction!%n");
            accountMenu();
            return;
        }
        Transaction transaction = new Transaction(-amount, Instant.now());
        account.recordTransaction(transaction);
        System.out.printf("Your transaction of $%.2f has been recorded, enter a command to continue: ", amount);

        clear();
        transactionList();
    }

    // MODIFIES: this
    // EFFECTS: record a deposit to the account
    private void recordDeposit() {
        double amount;
        System.out.printf("+     NEW DEPOSIT     +%n");
        System.out.printf("%n| Amount = $");
        amount = input.nextDouble();
        if (amount < 0.0) {
            clear();
            System.out.printf("Your deposit must be a value greater than 0!%n");
            recordDeposit();
            return;
        }
        Transaction transaction = new Transaction(amount, Instant.now());
        account.recordTransaction(transaction);
        System.out.printf("Your deposit of $%.2f has been recorded, enter a command to continue: ", amount);

        clear();
        transactionList();
    }

    // EFFECTS: saves the Account to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            System.out.println("Saved your account with balance $" + account.getBalance() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Account from file
    private void loadAccount() {
        try {
            account = jsonReader.read();
            System.out.println("Loaded your account with balance $" + account.getBalance() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: simulates clearing the screen by printing 10 newlines
    private void clear() {
        int i = 10;
        while (i != 0) {
            System.out.printf("%n");
            i--;
        }
    }

}
