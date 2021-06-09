package edu.postech.csed332.homework1;

/**
 * An account with a low interest rate. The rate is 0.5% per day.
 * E.g., if the balance is initially 100, after 10 days (on the 11th day)
 * the balance will be 100*(1.005)^10.
 */
class LowInterestAccount implements Account {
    String owner;
    double balance;
    int accountNumber;

    LowInterestAccount(String _owner, double _balance, int _accountNumber) {
        owner = _owner;
        balance = _balance;
        accountNumber = _accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public void updateBalance(int elapsedDate) {
        for (int i = 0; i < elapsedDate; i++)
            balance *= 1.005;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws IllegalOperationException {
        if (amount > balance)
            throw new IllegalOperationException("You account has less money than a money you request.");
        balance -= amount;
    }
}
