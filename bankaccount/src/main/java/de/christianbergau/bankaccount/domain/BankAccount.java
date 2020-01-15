package de.christianbergau.bankaccount.domain;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BankAccount {
    private final IBAN iban;
    private List<TransferEvent> events = new ArrayList<>();

    public double getBalance() {
        return events.stream().mapToDouble(TransferEvent::getAmount).sum();
    }

    public void transfer(double amount) {
        events.add(new TransferEvent(amount));
    }

    public void withdraw(double amount) {
        events.add(new TransferEvent(amount * -1));
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof BankAccount)) {
            return false;
        }

        BankAccount otherBankAccount = (BankAccount) other;
        return iban.getValue().equals(otherBankAccount.iban.getValue());
    }
}
