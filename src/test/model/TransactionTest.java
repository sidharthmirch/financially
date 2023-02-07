package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    private Transaction t1;
    private Transaction t2;
    private Transaction t3;
    private Date date = new Date();

    // TODO: setAmount() setCategory()

    @BeforeEach
    void runBefore() {
        t1 = new Transaction(400, date);
        t2 = new Transaction(-200, date);
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

    @Test
    void testSetDate() {
        assertEquals(date, t1.getDate());
        Date newDate = new Date();
        t1.setDate(newDate);
        assertEquals(newDate, t1.getDate());
        assertNotEquals(date, t1.getDate());
    }
}
