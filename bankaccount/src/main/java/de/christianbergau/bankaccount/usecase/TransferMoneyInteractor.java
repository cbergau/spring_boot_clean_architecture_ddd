package de.christianbergau.bankaccount.usecase;

import de.christianbergau.bankaccount.domain.Transaction;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class TransferMoneyInteractor implements TransferMoneyUseCase {
    private TransferMoneyPresenter presenter;
    private SaveTransactionRepository saveTransactionRepository;
    private Validator validator;

    public TransferMoneyInteractor(TransferMoneyPresenter presenter, SaveTransactionRepository saveTransactionRepository) {
        this.presenter = presenter;
        this.saveTransactionRepository = saveTransactionRepository;
    }

    public void execute(TransferMoneyRequest request) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<TransferMoneyRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            presenter.presentError(violations);
            return;
        }

        saveTransactionRepository.saveTransaction(
                new Transaction(request.getAmount(), request.getFromIban(), request.getToIban())
        );
    }
}
