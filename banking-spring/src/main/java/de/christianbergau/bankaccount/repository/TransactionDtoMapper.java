package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;

public class TransactionDtoMapper implements DtoMapper<Transaction, TransactionDto> {
    @Override
    public TransactionDto toDto(Transaction transaction) {
        return TransactionDto.builder()
                .transactionNumber(transaction.getTransactionNumber())
                .amount(transaction.getAmount())
                .fromIban(transaction.getFromIban())
                .toIban(transaction.getToIban())
                .build();
    }

    @Override
    public Transaction toDomainEntity(TransactionDto transactionDto) {
        return new Transaction(
                transactionDto.getTransactionNumber(),
                transactionDto.getAmount(),
                transactionDto.getFromIban(),
                transactionDto.getToIban()
        );
    }
}
