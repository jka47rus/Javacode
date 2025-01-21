package main;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private final String accountNumber;
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(String accountNumber, BigDecimal initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(BigDecimal amount) {
        lock.lock();
        try {
            int result = amount.compareTo(BigDecimal.ZERO);
            if (result > 0) {
                balance = balance.add(amount);

            }
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(BigDecimal amount) {
        lock.lock();
        try {
            int result = amount.compareTo(BigDecimal.ZERO);
            int balanceCheck = balance.compareTo(amount);

            if (result > 0 && balanceCheck >= 0) {
                balance = balance.subtract(amount);

            } else {
                throw new IllegalArgumentException("Не достаточно средств на счете!");
            }
        } finally {
            lock.unlock();
        }
    }

    public BigDecimal getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

