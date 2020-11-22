package de.christianbergau.bankaccount.repository;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Builder
@Getter
@Entity
public class TransactionDto {
    @Id
    @GeneratedValue()
    private String id;

    private String transactionNumber;
    private double amount;
    private String fromIban;
    private String toIban;
}
