package de.christianbergau.bankaccount.usecase;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface TransferMoneyPresenter {
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> errors);

    public void presentSuccess(String transactionId, String fromIban, String toIban, double amount);
}
