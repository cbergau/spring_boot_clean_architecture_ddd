package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JPATransactionMapperTest {

    private JPATransactionMapper mapper;

    double amount = 100.00;
    String transactionNumber = UUID.randomUUID().toString();
    String fromIban = "DE89 3704 0044 0532 0130 00";
    String toIban = "DE89 3704 0044 0532 0130 11";

    @BeforeEach
    void setUp() {
        mapper = new JPATransactionMapper();
    }

    @Test
    void testToJPAEntity() {
        // given
        Transaction transaction = new Transaction(transactionNumber, amount, fromIban, toIban);

        // when
        JPATransaction jpaTransaction = mapper.toJPAEntity(transaction);

        // then
        assertEquals(transactionNumber, jpaTransaction.getTransactionNumber());
        assertEquals(amount, jpaTransaction.getAmount());
        assertEquals(fromIban, jpaTransaction.getFromIban());
        assertEquals(toIban, jpaTransaction.getToIban());
    }

    @Test
    void testToDomainEntity() {
        // given
        JPATransaction jpaTransaction = JPATransaction.builder()
                .transactionNumber(transactionNumber)
                .amount(amount)
                .fromIban(fromIban)
                .toIban(toIban)
                .build();

        // when
        Transaction transaction = mapper.toDomainEntity(jpaTransaction);

        // then
        assertEquals(transactionNumber, transaction.getTransactionNumber());
        assertEquals(amount, transaction.getAmount());
        assertEquals(fromIban, transaction.getFromIban());
        assertEquals(toIban, transaction.getToIban());
    }
}
