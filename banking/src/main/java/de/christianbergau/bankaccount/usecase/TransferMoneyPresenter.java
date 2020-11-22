package de.christianbergau.bankaccount.usecase;

import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface TransferMoneyPresenter {
    void presentError(Set<ConstraintViolation<TransferMoneyRequest>> errors);

    void presentSuccess(String transactionNumber, String fromIban, String toIban, double amount);
}
