package de.christianbergau.bankaccount.data;

import de.christianbergau.bankaccount.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTransactionRepositoryTest {

    private InMemoryTransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTransactionRepository();
    }

    @Test
    void isInitiallyEmpty() {
        assertEquals(0, repository.entities().size());
    }

    @Test
    void testSaveTransaction() {
        Transaction transaction = new Transaction("123", 9.99, "12345", "67890");
        repository.saveTransaction(transaction);
        assertEquals(1, repository.entities().size());
        assertEquals(transaction, repository.entities().get(0));
    }

    @Test
    void testSaveTransactions() {
        Transaction transaction1 = new Transaction("123", 9.99, "12345", "67890");
        Transaction transaction2 = new Transaction("1234", 9.99, "12345", "67890");
        repository.saveTransaction(transaction1);
        repository.saveTransaction(transaction2);
        assertEquals(2, repository.entities().size());
        assertEquals(transaction1, repository.entities().get(0));
        assertEquals(transaction2, repository.entities().get(1));
    }
}
