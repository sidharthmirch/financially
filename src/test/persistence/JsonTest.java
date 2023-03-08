package persistence;

import model.Transaction;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTransaction(double amount, Instant date, String category, Transaction t) {
        assertEquals(amount, t.getAmount());
        assertEquals(date, t.getDate());
        assertEquals(category, t.getCategory());
    }
}
