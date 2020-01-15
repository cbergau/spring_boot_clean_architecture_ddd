package de.christianbergau.bankaccount.domain;

import lombok.Getter;

import java.time.LocalDateTime;

public class TransferEvent {
    private LocalDateTime occurredOn;

    @Getter
    private final double amount;

    public TransferEvent(double amount) {
        occurredOn = LocalDateTime.now();
        this.amount = amount;
    }
}
