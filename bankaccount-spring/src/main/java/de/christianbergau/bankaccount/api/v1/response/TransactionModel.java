package de.christianbergau.bankaccount.api.v1.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransactionModel {
    private String transactionNumber;
    private String fromIban;
    private String toIban;
    private double amount;
}
