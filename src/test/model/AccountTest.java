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
    void testRecordDeposit() {
        a1.recordDeposit(500.0);
        assertEquals(500.0, a1.getBalance());
        a1.recordDeposit(250.0);
        assertEquals(750.0, a1.getBalance());
        a2.recordDeposit(3500.0);
        a2.recordDeposit(300.0);
        assertEquals(8800.0, a2.getBalance());
    }

    @Test
    void testRecordTransaction() {
        a2.recordTransaction(200.0);
        assertEquals(4800.0, a2.getBalance());
        a2.recordTransaction(300.0);
        a2.recordTransaction(200.0);
        assertEquals(4300.0, a2.getBalance());
    }

    @Test
    void testGetSpending() {
        a2.recordTransaction(200.0);
        a2.recordTransaction(300.0);
        assertEquals(500.0, a2.getSpending());
        a2.recordDeposit(500.0);
        a2.recordTransaction(1000.0);
        assertEquals(1500.0, a2.getSpending());
    }
}
