package de.christianbergau.bankaccount.repository;

import de.christianbergau.bankaccount.domain.Transaction;
import reactor.core.publisher.Mono;

public interface SaveTransactionReactiveRepository {
    Mono<Transaction> saveTransaction(Transaction transaction);
}
