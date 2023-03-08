package persistence;

import model.Account;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account acc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account acc = reader.read();
            assertEquals(0, acc.getBalance());
            assertEquals(0, acc.getTransactionList().size());
            assertEquals(0, acc.getBudget().getSize());
            assertEquals(0, acc.getBudget().getRemaining());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderDemoAccount() {
        JsonReader reader = new JsonReader("./data/testReaderDemoAccount.json");
        try {
            Account acc = reader.read();
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
            assertEquals("Demo budget", acc.getBudget().getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}