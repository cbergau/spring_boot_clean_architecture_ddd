package de.christianbergau.banking.reactive.repository;

import de.christianbergau.bankaccount.domain.Transaction;
import de.christianbergau.bankaccount.repository.SaveTransactionReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReactiveRedisRepositoryImpl implements SaveTransactionReactiveRepository {
    private final ReactiveRedisOperations<String, ReactiveTransactionRedisDto> operations;

    @Override
    public Mono<Transaction> saveTransaction(Transaction transaction) {
        return operations
                .opsForValue()
                .set(transaction.getTransactionNumber(), mapToDto(transaction))
                .then(Mono.just(transaction));
    }

    private ReactiveTransactionRedisDto mapToDto(Transaction transaction) {
        return new ReactiveTransactionRedisDto(
                transaction.getTransactionNumber(),
                transaction.getAmount(),
                transaction.getFromIban(), 
                transaction.getToIban()
        );
    }
}
