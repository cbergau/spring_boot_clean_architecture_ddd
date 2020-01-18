package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JPATransactionMapperTest {

    private JPATransactionMapper mapper;

    double amount = 100.00;
    String fromIban = "DE89 3704 0044 0532 0130 00";
    String toIban = "DE89 3704 0044 0532 0130 11";

    @BeforeEach
    void setUp() {
        mapper = new JPATransactionMapper();
    }

    @Test
    void testToJPAEntity() {
        // given
        Transaction transaction = new Transaction(amount, fromIban, toIban);

        // when
        JPATransaction jpaTransaction = mapper.toJPAEntity(transaction);

        // then
        assertEquals(amount, jpaTransaction.getAmount());
        assertEquals(fromIban, jpaTransaction.getFromIban());
        assertEquals(toIban, jpaTransaction.getToIban());
    }

    @Test
    void testToDomainEntity() {
        // given
        JPATransaction jpaTransaction = JPATransaction.builder().amount(amount)
                .fromIban(fromIban)
                .toIban(toIban)
                .build();

        // when
        Transaction transaction = mapper.toDomainEntity(jpaTransaction);

        // then
        assertEquals(amount, transaction.getAmount());
        assertEquals(fromIban, transaction.getFromIban());
        assertEquals(toIban, transaction.getToIban());
    }
}
