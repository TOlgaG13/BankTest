package homework;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void testTransactionCreation() {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("50.00"));
        transaction.setType("TRANSFER");
        transaction.setDate(LocalDateTime.now());
        assertEquals("TRANSFER", transaction.getType());
        assertEquals(new BigDecimal("50.00"), transaction.getAmount());
    }
}

