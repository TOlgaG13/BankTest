package homework;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankService {
    @PersistenceContext
    private EntityManager entityManager;

    public void deposit(Long accountId, BigDecimal amount) {
        Account account = entityManager.find(Account.class, accountId);
        account.setBalance(account.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setFromAccount(null);
        transaction.setToAccount(account);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setDate(LocalDateTime.now());

        entityManager.persist(transaction);
    }

    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = entityManager.find(Account.class, fromAccountId);
        Account toAccount = entityManager.find(Account.class, toAccountId);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");
        transaction.setDate(LocalDateTime.now());

        entityManager.persist(transaction);
    }

    public void convertCurrency(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = entityManager.find(Account.class, fromAccountId);
        Account toAccount = entityManager.find(Account.class, toAccountId);

        ExchangeRate fromRate = entityManager.createQuery("SELECT r FROM ExchangeRate r WHERE r.currency = :currency", ExchangeRate.class)
                .setParameter("currency", fromAccount.getCurrency())
                .getSingleResult();

        ExchangeRate toRate = entityManager.createQuery("SELECT r FROM ExchangeRate r WHERE r.currency = :currency", ExchangeRate.class)
                .setParameter("currency", toAccount.getCurrency())
                .getSingleResult();

        BigDecimal amountInUAH = amount.multiply(fromRate.getRateToUAH());
        BigDecimal convertedAmount = amountInUAH.divide(toRate.getRateToUAH(), BigDecimal.ROUND_HALF_UP);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(convertedAmount));

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setType("CONVERSION");
        transaction.setDate(LocalDateTime.now());

        entityManager.persist(transaction);
    }

    public BigDecimal getUserTotalBalanceInUAH(Long userId) {
        User user = entityManager.find(User.class, userId);
        BigDecimal totalBalance = BigDecimal.ZERO;

        for (Account account : user.getAccounts()) {
            ExchangeRate rate = entityManager.createQuery("SELECT r FROM ExchangeRate r WHERE r.currency = :currency", ExchangeRate.class)
                    .setParameter("currency", account.getCurrency())
                    .getSingleResult();

            totalBalance = totalBalance.add(account.getBalance().multiply(rate.getRateToUAH()));
        }

        return totalBalance;
    }
}