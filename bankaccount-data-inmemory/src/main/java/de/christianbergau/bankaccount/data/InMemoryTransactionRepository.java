package de.christianbergau.bankaccount.data;

import de.christianbergau.bankaccount.domain.Transaction;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepository implements SaveTransactionRepository {

    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> entities() {
        return transactions;
    }
}
