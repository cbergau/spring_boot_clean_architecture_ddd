package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;

public interface SaveTransactionRepository {
    public void saveTransaction(Transaction transaction);
}
