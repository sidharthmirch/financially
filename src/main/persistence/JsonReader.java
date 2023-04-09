package persistence;

import model.Account;
import model.Budget;
import model.Transaction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads Account from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        double balance = jsonObject.getDouble("balance");
        Budget budget = parseBudget(jsonObject.getJSONObject("budget"));
        Account acc = new Account(budget, balance);
        addTransactions(acc, jsonObject);
        return acc;
    }


    // MODIFIES: acc, budget
    // EFFECTS: parses Budget from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        String name;
        double size = jsonObject.getDouble("size");
        double remaining = jsonObject.getDouble("remaining");
        Budget budget = new Budget(size);
        try {
            name = jsonObject.getString("name");
            budget.setName(name);
        } catch (Exception e) {
            // NO NAME SET
        }
        budget.setRemaining(remaining);
        return budget;
    }

    // MODIFIES: acc
    // EFFECTS: parses thingies from JSON object and adds them to Account
    private void addTransactions(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(acc, nextTransaction);
        }
    }

    // MODIFIES: acc
    // EFFECTS: parses thingy from JSON object and adds it to Account
    private void addTransaction(Account acc, JSONObject jsonObject) {
        String category;
        double amount = jsonObject.getDouble("amount");
        String dateString = jsonObject.getString("date");
        Instant date = Instant.parse(dateString);

        Transaction transaction = new Transaction(amount, date);
        try {
            category = jsonObject.getString("category");
            transaction.setCategory(category);
        } catch (Exception e) {
            //
        }
        acc.loadTransaction(transaction);
    }
}
