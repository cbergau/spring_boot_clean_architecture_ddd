package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionAdapter implements SaveTransactionRepository {

    private final TransactionDtoMapper mapper;
    private final TransactionRepository repository;

    @Override
    public void saveTransaction(Transaction transaction) {
        repository.save(mapper.toDto(transaction));
    }
}
