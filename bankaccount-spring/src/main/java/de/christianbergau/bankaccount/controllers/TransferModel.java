package de.christianbergau.bankaccount.controllers;

import javax.validation.constraints.NotEmpty;

public class TransferModel {
    public double amount;

    @NotEmpty
    public String fromIban;

    @NotEmpty
    public String toIban;
}
