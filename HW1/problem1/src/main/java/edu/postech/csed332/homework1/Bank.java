package edu.postech.csed332.homework1;

import java.util.ArrayList;
import java.util.List;

/**
 * Bank manages a collection of accounts. An account number is assigned
 * incrementally from 100000. E.g., the first account has 100000, the second
 * has 100001, etc. There are also functions for finding specific accounts.
 */
public class Bank {

    List<Account> accountList;
    private int accountNumber = 100000;

    /**
     * Create a bank. Initially, there is no account.
     */
    Bank() {
        accountList = new ArrayList<>();
    }

    /**
     * Find an account by a given account number.
     *
     * @param accNum an account number
     * @return the account with number accNum; null if no such account exists
     */
    Account findAccount(int accNum) {
        for (Account account : accountList)
            if (account.getAccountNumber() == accNum)
                return account;
        return null;
    }

    /**
     * Find all accounts owned by a given name
     *
     * @param name owner's name
     * @return a list of accounts sorted in ascending order by account number
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    List<Account> findAccountByName(String name) {
        List<Account> test = new ArrayList<>();
        for (Account account : accountList)
            if (account.getOwner().equals(name))
                test.add(account);

        return test;
    }

    /**
     * Create a new account and register it to the bank.
     *
     * @param name    owner name
     * @param accType HIGH or LOW
     * @param initial initial balance
     * @return the newly created account; null if not possible
     */
    Account createAccount(String name, ACCTYPE accType, double initial) {
        Account account = null;
        if (accType == ACCTYPE.HIGH) {
            if (initial < 1000)
                return null;
            account = new HighInterestAccount(name, initial, accountNumber++);
        } else if (accType == ACCTYPE.LOW)
            account = new LowInterestAccount(name, initial, accountNumber++);
        accountList.add(account);
        return account;
    }

    /**
     * Transfer a given amount of money from src account to dst account.
     *
     * @param src    source account
     * @param dst    destination acount
     * @param amount of money
     * @throws IllegalOperationException if not possible
     */
    void transfer(Account src, Account dst, double amount) throws IllegalOperationException {
        src.withdraw(amount);
        dst.deposit(amount);
    }
}
