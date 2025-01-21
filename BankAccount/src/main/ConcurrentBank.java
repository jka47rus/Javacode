package main;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConcurrentBank {
    private final Map<String, BankAccount> accounts = new HashMap<>();

    public synchronized BankAccount createAccount(String accountNumber, BigDecimal initialAmount) {
        if (!accounts.containsKey(accountNumber)) {

            BankAccount account = new BankAccount(accountNumber, initialAmount);
            accounts.put(accountNumber, account);
            return account;
        }
        throw new IllegalArgumentException("Аккаунт с таким номером уже существует");
    }

    public synchronized void transfer(BankAccount fromAccount, BankAccount toAccount, BigDecimal amount) {
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Один из счетов не существует.");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

    }

    public BigDecimal getTotalBalance() {
        BigDecimal total = BigDecimal.ZERO;
        synchronized (this) {
            for (BankAccount account : accounts.values()) {
                total = total.add(account.getBalance());
            }
        }
        return total;
    }
}


