package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;

import java.util.HashSet;
import java.util.Set;

public class InMemoryTransactionRepository implements SaveTransactionRepository {
    private Set<Transaction> transactions = new HashSet<>();

    @Override
    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Set<Transaction> findAll() {
        return transactions;
    }
}
