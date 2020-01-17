package de.christianbergau.bankaccount.usecase;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class TransferMoneyInteractor implements TransferMoneyUseCase {
    private TransferMoneyPresenter presenter;
    private Validator validator;

    public TransferMoneyInteractor(TransferMoneyPresenter presenter) {
        this.presenter = presenter;
    }

    public void execute(TransferMoneyRequest request) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<TransferMoneyRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            presenter.presentError(violations);
        }
    }
}
