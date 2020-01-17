package de.christianbergau.bankaccount.usecase;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface TransferMoneyPresenter {
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> errors);
}
