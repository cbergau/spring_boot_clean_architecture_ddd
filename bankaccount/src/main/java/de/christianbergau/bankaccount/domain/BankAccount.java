package de.christianbergau.bankaccount.domain;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

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
}
