package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    private Transaction t1;
    private Transaction t2;
    private Transaction t3;

    @BeforeEach
    void runBefore() {
        t1 = new Transaction(400);
        t2 = new Transaction(-200);
        // TODO: Date test
    }

    @Test
    void constructorTest() {
        assertEquals(400.0, t1.getAmount());
        assertEquals(-200.0, t2.getAmount());
    }

    @Test
    void testDeposit() {
        assertTrue(t1.isDeposit());
        assertFalse(t2.isDeposit());
    }
}
