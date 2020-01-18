package de.christianbergau.bankaccount.usecase;

import de.christianbergau.bankaccount.domain.Transaction;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransferMoneyInteractorTest {

    @Test
    void testGivenDestinationIbanIsInvalid_ShouldPresentError() {
        // given
        TransferMoneyPresenterSpy presenter = new TransferMoneyPresenterSpy();
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
        SaveTransactionRepository saveTransactionRepository = mock(SaveTransactionRepository.class);
        TransferMoneyInteractor interactor = new TransferMoneyInteractor(presenter, saveTransactionRepository);

        // when
        interactor.execute(new TransferMoneyRequest(fromIban, toIban, amount));

        // then
        assertEquals(0, presenter.getConstraintViolations().size());
        Transaction transaction = new Transaction(amount, fromIban, toIban);
        verify(saveTransactionRepository, times(1)).saveTransaction(transaction);
    }
}

@Getter
class TransferMoneyPresenterSpy implements TransferMoneyPresenter {
    private Set<ConstraintViolation<TransferMoneyRequest>> constraintViolations = new HashSet<>();

    @Override
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }
}
