package de.christianbergau.bankaccount.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
public class TransferMoneyRequest {
    private String fromIban;

    @NotEmpty
    private String toIban;

    private double amount;
}
