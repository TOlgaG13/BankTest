package homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private BankService bankService;

    private Account account;
    private User user;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1000));
        account.setCurrency(Currency.UAH);

        user = new User();
        user.setId(1L);
        user.setName("Test User");
    }

    @Test
    void deposit_ShouldIncreaseBalanceAndCreateTransaction() {
        when(entityManager.find(Account.class, 1L)).thenReturn(account);

        bankService.deposit(1L, BigDecimal.valueOf(500));

        assertEquals(BigDecimal.valueOf(1500), account.getBalance());
        verify(entityManager, times(1)).persist(any(Transaction.class));
    }

    @Test
    void transfer_ShouldDeductFromSenderAndAddToReceiver() {
        Account toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(BigDecimal.valueOf(500));
        toAccount.setCurrency(Currency.UAH);

        when(entityManager.find(Account.class, 1L)).thenReturn(account);
        when(entityManager.find(Account.class, 2L)).thenReturn(toAccount);

        bankService.transfer(1L, 2L, BigDecimal.valueOf(200));

        assertEquals(BigDecimal.valueOf(800), account.getBalance());
        assertEquals(BigDecimal.valueOf(700), toAccount.getBalance());
        verify(entityManager, times(1)).persist(any(Transaction.class));
    }
}




