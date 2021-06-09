package edu.postech.csed332.homework1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    Bank wb;

    @BeforeEach
    void setup() {
        wb = new Bank();
    }

    @Test
    void testFindAccount() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        Account reqAccountByNum = wb.findAccount(100000);
        List<Account> reqAccountByName = wb.findAccountByName("Thomas");
        assertEquals(reqAccountByNum.getOwner(), "Thomas");
        assertEquals(reqAccountByName.get(0).getAccountNumber(), 100000);
    }

    @Test
    void testHighInterestAccount() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        Account s = wb.findAccount(100000);
        s.deposit(10000.);
        s.updateBalance(20);
        assertEquals(s.getBalance(), 122019.00399479672);

        Account account = wb.createAccount("John", ACCTYPE.HIGH, 100.);
        assertNull(account);
    }

    @Test
    void testLowInterestAccount() {
        wb.createAccount("Thomas", ACCTYPE.LOW, 100.);
        Account s = wb.findAccount(100000);
        s.updateBalance(10);
        assertEquals(s.getBalance(), 105.11401320407893);
    }

    @Test
    void testMultiAccount() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        wb.createAccount("John", ACCTYPE.LOW, 60000.);
        Account t = wb.findAccount(100000);
        assertEquals(t.getBalance(), 90000.);
        Account j = wb.findAccount(100001);
        assertEquals(j.getBalance(), 60000.);
    }

    @Test
    void testTransfer() throws IllegalOperationException {
        Account account1 = wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        Account account2 = wb.createAccount("John", ACCTYPE.HIGH, 90000.);
        Account account3 = wb.createAccount("John", ACCTYPE.LOW, 100.);
        wb.transfer(account1, account2, 10000.);
        List<Account> john = wb.findAccountByName("John");
        assertEquals(john.get(1).getBalance(), 100.);
        assertEquals(john.get(0).getBalance(), 100000.);
        assertEquals(john.get(0), account2);
        assertEquals(account1.getBalance(), 80000.);

        assertThrows(IllegalOperationException.class,
                () -> account3.withdraw(1000.),
                "You account has less money than a money you request.");
    }
}

