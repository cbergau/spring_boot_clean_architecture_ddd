package de.christianbergau.bankaccount.usecase;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
public class TransferMoneyRequest {
    private String fromIban;

    @NotEmpty
    private String toIban;
}
