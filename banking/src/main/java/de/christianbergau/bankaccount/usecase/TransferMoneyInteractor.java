package de.christianbergau.bankaccount.usecase;

import de.christianbergau.bankaccount.domain.Transaction;
import de.christianbergau.bankaccount.repository.SaveTransactionReactiveRepository;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

public class TransferMoneyInteractor implements TransferMoneyUseCase {
    private TransferMoneyPresenter presenter;
    private SaveTransactionRepository saveTransactionRepository;
    private SaveTransactionReactiveRepository saveTransactionReactiveRepository;
    private Validator validator;

    public TransferMoneyInteractor(TransferMoneyPresenter presenter, SaveTransactionRepository saveTransactionRepository) {
        this.presenter = presenter;
        this.saveTransactionRepository = saveTransactionRepository;
    }

    public TransferMoneyInteractor(TransferMoneyPresenter presenter, SaveTransactionReactiveRepository repository) {
        this.presenter = presenter;
        this.saveTransactionReactiveRepository = repository;
    }

    public void execute(TransferMoneyRequest request) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<TransferMoneyRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            presenter.presentError(violations);
            return;
        }

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                request.getAmount(),
                request.getFromIban(),
                request.getToIban()
        );

        saveTransactionRepository.saveTransaction(transaction);

        presenter.presentSuccess(
                transaction.getTransactionNumber(),
                transaction.getFromIban(),
                transaction.getToIban(),
                transaction.getAmount()
        );
    }

    public void executeAsync(TransferMoneyRequest request) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<TransferMoneyRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            presenter.presentError(violations);
            return;
        }

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                request.getAmount(),
                request.getFromIban(),
                request.getToIban()
        );

        // Was wenn das Mono fehlschlägt? Können wir hier überhaupt differenzieren?
        Mono<Transaction> transactionMono = saveTransactionReactiveRepository.saveTransaction(transaction);
        transactionMono.doOnSuccess(transactionAfterSave ->
                presenter.presentSuccess(
                        transactionAfterSave.getTransactionNumber(),
                        transactionAfterSave.getFromIban(),
                        transactionAfterSave.getToIban(),
                        transactionAfterSave.getAmount()));
    }
}
