package de.christianbergau.banking.reactive.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferModel {
    public double amount;

    @NotEmpty
    public String fromIban;

    @NotEmpty
    public String toIban;
}
