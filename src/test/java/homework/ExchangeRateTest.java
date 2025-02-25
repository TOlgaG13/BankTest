package homework;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangeRateTest {
    @Test
    public void testExchangeRateCreation() {
        ExchangeRate rate = new ExchangeRate();


        rate.setCurrency(Currency.EUR);
        rate.setRateToUAH(new BigDecimal("40.00"));


        assertEquals(Currency.EUR, rate.getCurrency());

        assertEquals(new BigDecimal("40.00"), rate.getRateToUAH());
    }
}

