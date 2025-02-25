package homework;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal rateToUAH;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getRateToUAH() {
        return rateToUAH;
    }

    public void setRateToUAH(BigDecimal rateToUAH) {
        this.rateToUAH = rateToUAH;
    }


}

