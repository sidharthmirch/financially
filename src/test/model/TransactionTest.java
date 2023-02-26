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


    @BeforeEach
    void runBefore() {
        t1 = new Transaction(400.0, date);
        t2 = new Transaction(-200.0, date);
        t3 = new Transaction(500.0);
    }

    @Test
    void testConstructor() {
        assertEquals(400.0, t1.getAmount());
        assertEquals(-200.0, t2.getAmount());
        assertEquals(date, t1.getDate());
        assertEquals(500.0, t3.getAmount());
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
        t3.setDate(newDate);
        assertEquals(newDate, t3.getDate());
    }

    @Test
    void testSetAmount() {
        t1.setAmount(200.0);
        assertEquals(200.0, t1.getAmount());
        t2.setAmount(500.0);
        assertEquals(500.0, t2.getAmount());
        t1.setAmount(-300.0);
        assertEquals(-300.0, t1.getAmount());
    }

    @Test
    void testSetCategory() {
        t1.setCategory("A");
        assertEquals("A", t1.getCategory());
        t2.setCategory("B");
        assertEquals("B", t2.getCategory());
    }
}
