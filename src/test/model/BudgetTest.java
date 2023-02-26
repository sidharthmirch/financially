package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {
    private Budget b1;
    private Budget b2;

    @BeforeEach
    void runBefore() {
        b1 = new Budget(1500.0);
        b2 = new Budget(2000.0, "Food");
    }

    @Test
    void testConstructor() {
        assertEquals(1500.0, b1.getSize());
        assertEquals(2000.0, b2.getSize());
        assertEquals("Food", b2.getName());
    }

    @Test
    void testSetName() {
        b1.setName("Amazon");
        assertEquals("Amazon", b1.getName());
        assertEquals("Food", b2.getName());
        b2.setName("New name!");
        assertEquals("New name!", b2.getName());
    }

    @Test
    void testSetSize() {
        b1.setSize(2000.0, 100.0);
        assertEquals(2000.0, b1.getSize());
        b1.setSize(1200.0, 100.0);
        assertEquals(1200.0, b1.getSize());
        b2.setSize(300.0, 100.0);
        assertEquals(300.0, b2.getSize());
        b2.setSize(5000.0, 100.0);
        assertEquals(5000.0, b2.getSize());
    }

    @Test
    void testRecordTransaction() {
        b1.recordTransaction(new Transaction(-200.0));
        assertEquals(1300.0, b1.getRemaining());
        b2.recordTransaction(new Transaction(-200.0));
        assertEquals(1800.0, b2.getRemaining());
        b2.recordTransaction(new Transaction(400.0));
        assertEquals(1800.0, b2.getRemaining());
    }

}
