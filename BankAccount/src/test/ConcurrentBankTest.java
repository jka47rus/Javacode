package test;

import main.BankAccount;
import main.ConcurrentBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrentBankTest {


    @Test
    void transferTest() {
        ConcurrentBank bank = new ConcurrentBank();
        BankAccount account1 = bank.createAccount("1", BigDecimal.valueOf(1000));
        BankAccount account2 = bank.createAccount("2", BigDecimal.valueOf(500));
        bank.transfer(account1, account2, BigDecimal.valueOf(200));

        BigDecimal account1Actual = account1.getBalance();
        BigDecimal account2Actual = account2.getBalance();
        BigDecimal account1Expected = BigDecimal.valueOf(800);
        BigDecimal account2Expected = BigDecimal.valueOf(700);

        assertEquals(account1Expected, account1Actual);
        assertEquals(account2Expected, account2Actual);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bank.transfer(account2, account1, BigDecimal.valueOf(2000));
        });

        assertEquals("Не достаточно средств на счете!", exception.getMessage());

        BigDecimal inTotalActual = bank.getTotalBalance();

        assertEquals(BigDecimal.valueOf(1500), inTotalActual);

    }

}
