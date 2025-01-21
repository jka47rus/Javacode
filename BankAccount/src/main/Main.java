package main;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ConcurrentBank bank = new ConcurrentBank();

        BankAccount account1 = bank.createAccount("1", BigDecimal.valueOf(1000));
        BankAccount account2 = bank.createAccount("2", BigDecimal.valueOf(500));
        BankAccount account3 = bank.createAccount("3", BigDecimal.valueOf(2000));

        Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2, BigDecimal.valueOf(200)));
        Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1, BigDecimal.valueOf(100)));
        Thread transferThread3 = new Thread(() -> bank.transfer(account3, account2, BigDecimal.valueOf(1000)));

        transferThread1.start();
        transferThread2.start();
        transferThread3.start();

        try {
            transferThread1.join();
            transferThread2.join();
            transferThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Total balance: " + bank.getTotalBalance());
    }
}