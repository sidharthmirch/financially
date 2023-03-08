package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account a1;
    private Account a2;

    @BeforeEach
    void runBefore() {
        a1 = new Account();
        a2 = new Account(5000.0);
    }

    @Test
    void testConstructor() {
        assertEquals(0.0, a1.getBalance());
        assertEquals(5000.0, a2.getBalance());
        assertEquals(0.0, a1.getBudget().getSize());
        assertEquals((5000.0 / 2), a2.getBudget().getSize());
        assertEquals(0.0, a1.getTransactionList().size());
        assertEquals(0.0, a2.getTransactionList().size());
    }

    @Test
    void testRecordTransaction() {
        // deposits
        a1.recordTransaction(new Transaction(500.0));
        assertEquals(500.0, a1.getBalance());
        a1.recordTransaction(new Transaction(250.0));
        assertEquals(750.0, a1.getBalance());
        a2.recordTransaction(new Transaction(3500.0));
        a2.recordTransaction(new Transaction(300.0));
        assertEquals(8800.0, a2.getBalance());

        // withdrawals
        a2.recordTransaction(new Transaction(-200.0));
        assertEquals(8600.0, a2.getBalance());
        a2.recordTransaction(new Transaction(-300.0));
        a2.recordTransaction(new Transaction(-200.0));
        assertEquals(8100.0, a2.getBalance());
    }

    @Test
    void testGetSpending() {
        a2.recordTransaction(new Transaction(-200.0));
        a2.recordTransaction(new Transaction(-300.0));
        assertEquals(500.0, a2.getSpending());
        a2.recordTransaction(new Transaction(500.0));
        a2.recordTransaction(new Transaction(-1000.0));
        assertEquals(1500.0, a2.getSpending());
    }
}
