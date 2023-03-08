package persistence;

import model.Account;
import model.Budget;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Account acc = new Account(1000);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Account acc = new Account(new Budget(0), 0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals(0, acc.getBalance());
            assertEquals(0, acc.getTransactionList().size());
            assertEquals(0, acc.getBudget().getSize());
            assertEquals(0, acc.getBudget().getRemaining());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Account acc = new Account(new Budget(2000), 4500);
            acc.getBudget().setRemaining(1905);
            acc.loadTransaction(new Transaction(-20, Instant.parse("2023-03-07T23:52:19.326547Z")));
            acc.loadTransaction(new Transaction(-40, Instant.parse("2023-03-07T23:52:20.977080Z")));
            acc.loadTransaction(new Transaction(-20, Instant.parse("2023-03-07T23:52:23.196514Z")));
            acc.loadTransaction(new Transaction(20, Instant.parse("2023-03-07T23:52:24.791338Z")));
            acc.loadTransaction(new Transaction(-35, Instant.parse("2023-03-07T23:52:27.362722Z")));
            acc.getTransactionList().get(4).setCategory("Test category");
            JsonWriter writer = new JsonWriter("./data/testWriterDemoAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDemoAccount.json");
            acc = reader.read();
            assertEquals(4500, acc.getBalance());
            List<Transaction> transactionList = acc.getTransactionList();
            assertEquals(5, transactionList.size());
            checkTransaction(-20, Instant.parse("2023-03-07T23:52:19.326547Z"), null,
                    transactionList.get(0));
            checkTransaction(20, Instant.parse("2023-03-07T23:52:24.791338Z"), null,
                    transactionList.get(3));
            checkTransaction(-35, Instant.parse("2023-03-07T23:52:27.362722Z"), "Test category",
                    transactionList.get(4));
            assertEquals(2000, acc.getBudget().getSize());
            assertEquals(1905, acc.getBudget().getRemaining());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}