package de.christianbergau.bankaccount.usecase;

import de.christianbergau.bankaccount.domain.Transaction;
import de.christianbergau.bankaccount.repository.InMemoryTransactionRepository;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TransferMoneyInteractorTest {

    @Test
    void testGivenDestinationIbanIsInvalid_ShouldPresentError() {
        // given
        TransferMoneyPresenterErrorSpy presenter = new TransferMoneyPresenterErrorSpy();
        SaveTransactionRepository saveTransactionRepository = mock(SaveTransactionRepository.class);
        TransferMoneyInteractor interactor = new TransferMoneyInteractor(presenter, saveTransactionRepository);

        // when
        interactor.execute(new TransferMoneyRequest("DE89 3704 0044 0532 0130 00", "", 100.00));

        // then
        assertEquals(1, presenter.getConstraintViolations().size());
        assertEquals("{javax.validation.constraints.NotEmpty.message}", presenter.getConstraintViolations().iterator().next().getMessageTemplate());
        verify(saveTransactionRepository, times(0)).saveTransaction(any());
    }

    @Test
    void testGivenRequestIsValid_ShouldSaveTransaction_AndPresentTransactionDetails() {
        // given
        String fromIban = "DE89 3704 0044 0532 0130 00";
        String toIban = "DE89 3704 0044 0532 0130 22";
        double amount = 100.00;

        TransferMoneyPresenterSpy presenter = new TransferMoneyPresenterSpy();
        InMemoryTransactionRepository saveTransactionRepository = new InMemoryTransactionRepository();
        TransferMoneyInteractor interactor = new TransferMoneyInteractor(presenter, saveTransactionRepository);

        // when
        interactor.execute(new TransferMoneyRequest(fromIban, toIban, amount));

        // then
        assertEquals(0, presenter.getConstraintViolations().size());
        assertNotNull(presenter.getTransactionId());
        
        Transaction savedTransaction = saveTransactionRepository.findAll().iterator().next();
        assertEquals(presenter.getTransactionId(), savedTransaction.getTransactionNumber());
        assertEquals(presenter.getFromIban(), savedTransaction.getFromIban());
        assertEquals(presenter.getToIban(), savedTransaction.getToIban());
        assertEquals(presenter.getAmount(), savedTransaction.getAmount());
    }
}

@Getter
class TransferMoneyPresenterErrorSpy implements TransferMoneyPresenter {
    private Set<ConstraintViolation<TransferMoneyRequest>> constraintViolations = new HashSet<>();

    @Override
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }

    @Override
    public void presentSuccess(String transactionId, String fromIban, String toIban, double amount) {

    }
}

@Getter
class TransferMoneyPresenterSpy implements TransferMoneyPresenter {
    private Set<ConstraintViolation<TransferMoneyRequest>> constraintViolations = new HashSet<>();
    private String transactionId;
    private String fromIban;
    private String toIban;
    private double amount;

    @Override
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> constraintViolations) {

    }

    @Override
    public void presentSuccess(String transactionId, String fromIban, String toIban, double amount) {
        this.transactionId = transactionId;
        this.fromIban = fromIban;
        this.toIban = toIban;
        this.amount = amount;
    }
}
