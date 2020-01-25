package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JPATransactionAdapter implements SaveTransactionRepository {

    private final JPATransactionMapper mapper;
    private final JPATransactionRepository repository;

    @Override
    public void saveTransaction(Transaction transaction) {
        repository.save(mapper.toJPAEntity(transaction));
    }
}
