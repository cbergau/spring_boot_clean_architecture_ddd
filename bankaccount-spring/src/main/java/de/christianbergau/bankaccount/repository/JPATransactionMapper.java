package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;

public class JPATransactionMapper implements JPAMapper<Transaction, JPATransaction> {
    @Override
    public JPATransaction toJPAEntity(Transaction transaction) {
        return JPATransaction.builder()
                .amount(transaction.getAmount())
                .fromIban(transaction.getFromIban())
                .toIban(transaction.getToIban())
                .build();
    }

    @Override
    public Transaction toDomainEntity(JPATransaction jpaTransaction) {
        return new Transaction(jpaTransaction.getAmount(), jpaTransaction.getFromIban(), jpaTransaction.getToIban());
    }
}
