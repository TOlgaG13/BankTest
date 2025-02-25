package homework;

import org.junit.Test;


import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    @Test
    public void testAccountCreation() {
        Account account = new Account();
        account.setCurrency(Currency.USD); // Правильный способ задания валюты
        account.setBalance(new BigDecimal("100.00"));

        assertEquals(Currency.USD, account.getCurrency()); // Исправлено: сравниваем с Currency
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }
}

