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
        a2 = new Account(5000);
    }

    @Test
    void testConstructor() {
        assertEquals(0, a1.getBalance());
        assertEquals(5000, a2.getBalance());
        assertEquals(0, a1.getBudget().getSize());
        assertEquals((5000 / 2), a2.getBudget().getSize());
        assertEquals(0, a1.getTransactionList().size());
        assertEquals(0, a2.getTransactionList().size());
    }

    @Test
    void testRecordDeposit() {
        a1.recordDeposit(500);
        assertEquals(500, a1.getBalance());
        a1.recordDeposit(250);
        assertEquals(750, a1.getBalance());
        a2.recordDeposit(3500);
        a2.recordDeposit(300);
        assertEquals(8800, a2.getBalance());
    }

    @Test
    void testRecordTransaction() {
        a2.recordTransaction(200);
        assertEquals(4800, a2.getBalance());
        a2.recordTransaction(300);
        a2.recordTransaction(200);
        assertEquals(4300, a2.getBalance());
    }

    @Test
    void testGetSpending() {
        a2.recordTransaction(200);
        a2.recordTransaction(300);
        assertEquals(500, a2.getSpending());
        a2.recordDeposit(500);
        a2.recordTransaction(1000);
        assertEquals(1500, a2.getSpending());
    }
}
