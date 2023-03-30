package ui;

import model.Account;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SavePanel extends JPanel {

    public final String name;

    private Account account;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/user.json";

    public SavePanel(Account acc, String name) {
        this.name = name;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAccount();
            }
        });
        setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        setLayout(new BorderLayout());
        this.account = acc;
        this.add(saveButton);
    }

    // EFFECTS: saves the Account to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            Object[] options = {"Quit",
                    "Return to Financially"};
            int exitPanel = JOptionPane.showOptionDialog(null,
                    "Saved your account with balance $" + account.getBalance() + " to " + JSON_STORE,
                    "Success!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,null, options, null);
            System.out.println("Saved your account with balance $" + account.getBalance() + " to " + JSON_STORE);
            if (exitPanel == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else if (exitPanel == JOptionPane.NO_OPTION) {
                // Continue running
            }
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

}
