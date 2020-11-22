package de.christianbergau.bankaccount.repository;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Builder
@Getter
@RedisHash
public class TransactionDto {
    @Indexed
    @Id
    private String transactionNumber;
    private double amount;
    private String fromIban;
    private String toIban;
}
