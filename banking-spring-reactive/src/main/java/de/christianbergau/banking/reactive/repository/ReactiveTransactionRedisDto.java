package de.christianbergau.banking.reactive.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactiveTransactionRedisDto {
    private String transactionNumber;
    private double amount;
    private String fromIban;
    private String toIban;
}
