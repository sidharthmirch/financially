package ui;

import model.Account;
import model.Budget;
import model.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Math.abs;

// Console based implementation of the Budget app
public class BudgetApp {

    private Account account;
    private Budget budget;
    private Scanner input;


    public BudgetApp() {
        run();
    }

    private void run() {
        String cmd;
        setup();
        while (true) {
            System.out.print("To continue, please type a command and press enter: ");
            cmd = input.next().toLowerCase();
            if (cmd.equals("q")) {
                break;
            } else {
                handleInput(cmd);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes account and prints welcome message
    private void setup() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.printf("Welcome to Financially.%n");
        demo();
        commandList();
    }


    // MODIFIES: this
    // EFFECTS: creates a new user account and budget for demo purposes
    private void demo() {
        double openingBalance;
        double budgetSize;
        System.out.println("Let's create a new account.");
        System.out.printf("Please enter your opening balance: ");
        openingBalance = input.nextDouble();
        account = new Account(openingBalance);
        System.out.printf("Great! Your account has been created with a balance of $%.2f.%n", account.getBalance());

        System.out.println("Now let's create a budget.");
        System.out.printf("Please set your budget size: ");
        budgetSize = input.nextDouble();
        budget = new Budget(budgetSize);
        clear();
    }

    // MODIFIES: this
    // EFFECTS: handle user input
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
        System.out.printf("| q - quit the application      |%n");
        System.out.printf("+-------------------------------+%n");
    }

    // EFFECTS: display user account information
    private void accountMenu() {
        System.out.printf("+       YOUR ACCOUNT     +%n");
        System.out.printf("+------------------------+%n");
        System.out.printf("| Balance   | $%-4.2f   |%n", account.getBalance());
        System.out.printf("| Budget    | $%-4.2f   |%n", budget.getSize());
        System.out.printf("| Remaining | $%-4.2f   |%n", budget.getRemaining());
        System.out.printf("+------------------------+%n");
    }

    // EFFECTS: display user budget information
    private void budgetMenu() {
        if (budget.getName() != null) {
            System.out.printf("+      %-16s +%n", budget.getName().toUpperCase());
        } else {
            System.out.printf("+       YOUR BUDGET      +%n");
        }
        System.out.printf("+------------------------+%n");
        System.out.printf("| Total     | $%-2.2f    |%n", budget.getSize());
        System.out.printf("| Spent     | $%-2.2f    |%n", budget.getSize() - account.getSpending());
        System.out.printf("| Remaining | $%-2.2f    |%n", budget.getRemaining());
        System.out.printf("+------------------------+%n");

        editBudget();
    }

    private void editBudget() {
        // force entry into loop for further input, with reference to TellerApp
        String cmd = "";

        while (!(cmd.equals("n") || cmd.equals("s") || cmd.equals("h"))) {
            System.out.printf("To edit your budget name enter \"n\", to edit size enter \"s\".%n");
            System.out.printf("To exit this menu enter \"h\".%n");
            cmd = input.next().toLowerCase();
        }

        if (cmd.equals("n")) {
            String name;
            System.out.printf("Enter your new budget name: ");
            name = input.next();
            budget.setName(name);
            System.out.printf("Successfully renamed budget to \"%s\".%n", budget.getName());
        } else if (cmd.equals("s")) {
            double size;
            System.out.printf("Enter your new budget size: ");
            size = input.nextInt();
            budget.setSize(size, account.getSpending());
            System.out.printf("Successfully changed budget to $%f.%n", budget.getSize());
        } else {
            commandList();
        }
    }

    // EFFECTS: display all user transactions
    private void transactionList() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        System.out.printf("+       USER TRANSACTIONS     +%n");
        System.out.printf("+-----------------+-----------+%n");
        System.out.printf("| Type | Amount   | Date      |%n");
        System.out.printf("+------+----------------------+%n");

        // Table generation code inspiration from
        // https://stackoverflow.com/questions/15215326/how-can-i-create-table-using-ascii-in-a-console
        for (Transaction t : account.getTransactionList()) {
            double amount = t.getAmount();
            Date date = t.getDate();
            String formattedDate = dateFormat.format(date);

            System.out.printf("|  %-4s| %-8s | %-6s |%n",
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
            System.out.printf("Your account balance is too low for this transaction!%n");
            accountMenu();
        }
        account.recordTransaction(amount);
        System.out.printf("Your transaction of $%.2f has been recorded, enter a command to continue: ", -amount);

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
            System.out.printf("Your deposit must be a value greater than 0!%n");
            clear();
            recordDeposit();
        }
        account.recordDeposit(amount);
        System.out.printf("Your deposit of $%.2f has been recorded, enter a command to continue: ", amount);

        clear();
        transactionList();
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
