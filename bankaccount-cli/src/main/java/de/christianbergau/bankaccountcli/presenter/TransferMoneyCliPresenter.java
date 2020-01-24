package de.christianbergau.bankaccountcli.presenter;

import de.christianbergau.bankaccount.usecase.TransferMoneyPresenter;
import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class TransferMoneyCliPresenter implements TransferMoneyPresenter {
    @Override
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> errors) {
        errors.forEach(System.err::println);
    }

    @Override
    public void presentSuccess(String transactionId, String fromIban, String toIban, double amount) {
        System.out.println(String.format("Transaction: %s, from: %s, to: %s", transactionId, fromIban, toIban));
    }
}
