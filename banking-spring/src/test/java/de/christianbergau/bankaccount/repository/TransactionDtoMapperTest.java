package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionDtoMapperTest {

    private TransactionDtoMapper mapper;

    double amount = 100.00;
    String transactionNumber = UUID.randomUUID().toString();
    String fromIban = "DE89 3704 0044 0532 0130 00";
    String toIban = "DE89 3704 0044 0532 0130 11";

    @BeforeEach
    void setUp() {
        mapper = new TransactionDtoMapper();
    }

    @Test
    void testToJPAEntity() {
        // given
        Transaction transaction = new Transaction(transactionNumber, amount, fromIban, toIban);

        // when
        TransactionDto transactionDto = mapper.toDto(transaction);

        // then
        assertEquals(transactionNumber, transactionDto.getTransactionNumber());
        assertEquals(amount, transactionDto.getAmount());
        assertEquals(fromIban, transactionDto.getFromIban());
        assertEquals(toIban, transactionDto.getToIban());
    }

    @Test
    void testToDomainEntity() {
        // given
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionNumber(transactionNumber)
                .amount(amount)
                .fromIban(fromIban)
                .toIban(toIban)
                .build();

        // when
        Transaction transaction = mapper.toDomainEntity(transactionDto);

        // then
        assertEquals(transactionNumber, transaction.getTransactionNumber());
        assertEquals(amount, transaction.getAmount());
        assertEquals(fromIban, transaction.getFromIban());
        assertEquals(toIban, transaction.getToIban());
    }
}
