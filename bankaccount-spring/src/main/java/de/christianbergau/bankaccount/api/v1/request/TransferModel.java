package de.christianbergau.bankaccount.api.v1.request;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;

@Builder
public class TransferModel {
    public double amount;

    @NotEmpty
    public String fromIban;

    @NotEmpty
    public String toIban;
}
