package de.christianbergau.bankaccount.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Transaction {
    private final double amount;
    private final String fromIban;
    private final String toIban;
}
